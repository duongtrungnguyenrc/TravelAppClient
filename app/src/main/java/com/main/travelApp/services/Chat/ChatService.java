package com.main.travelApp.services.Chat;

import android.app.Activity;
import android.util.Log;

import com.main.travelApp.models.Message;
import com.main.travelApp.request.NewMessageRequest;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private static final String SERVER_URL = "http://192.168.31.214:8085";
    private Socket socket;
    private final List<Message> messages = new ArrayList<>();
    private final Activity activity;

    public interface MessageListener {
        void onConnected(List<Message> messages);
        void onMessageReceived(Message message);
        void onChange(boolean isChanging);
        void onStopChange();
    }

    private final MessageListener messageListener;

    public ChatService(String room, Activity activity, MessageListener listener) {
        this.activity = activity;
        this.messageListener = listener;
        try {
            socket = IO.socket(SERVER_URL).connect();

            socket.on(Socket.EVENT_CONNECT, args -> {
                try {
                    JSONObject joinRoomData = new JSONObject();
                    joinRoomData.put("room", room);
                    socket.emit("join-room", joinRoomData);
                    enableEvent();
                } catch (JSONException e) {
                    Log.d("SocketIO", "JSON Exception: " + e.getMessage());
                }
            }).on(Socket.EVENT_CONNECT_ERROR, args -> {
                Exception e = (Exception) args[0];
                Log.d("SocketIO", "Connection error: " + e.toString());
            }).on(Socket.EVENT_DISCONNECT, args -> {
                Log.d("SocketIO", "Disconnected from server");
            });
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void enableEvent() {
        socket.on("connected", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONArray messagesArray = (JSONArray) args[0];
                        try {
                            for (int i = 0; i < messagesArray.length(); i++) {
                                JSONObject messageJson = messagesArray.getJSONObject(i);

                                Message message = new Message(
                                        messageJson.getLong("id"),
                                        messageJson.getString("message"),
                                        messageJson.getLong("uid"),
                                        messageJson.getString("time"),
                                        messageJson.getLong("room"),
                                        messageJson.getString("name"),
                                        messageJson.getString("role"),
                                        messageJson.getString("avatar")
                                );
                                messages.add(message);
                            }
                            messageListener.onConnected(messages);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).on("receive", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject messageJson = (JSONObject) args[0];
                        try {
                            Message message = new Message(
                                    messageJson.getLong("id"),
                                    messageJson.getString("message"),
                                    messageJson.getLong("uid"),
                                    messageJson.getString("time"),
                                    messageJson.getLong("room"),
                                    messageJson.getString("name"),
                                    messageJson.getString("role"),
                                    messageJson.getString("avatar")
                            );
                            messages.add(message);
                            messageListener.onMessageReceived(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).on("change", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                messageListener.onChange(true);
            }
        }).on("stop-change", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                messageListener.onStopChange();
            }
        });
    }

    public void sendChange() {
        socket.emit("change", new JSONObject());
    }

    public void sendStopChange() {
        socket.emit("stop-change", new JSONObject());
    }

    public void sendMessage(JSONObject request) {
        socket.emit("send", request);
    }

    public void disconnect() {
        if (socket != null) {
            socket.disconnect();
            socket.off();
        }
    }
}
