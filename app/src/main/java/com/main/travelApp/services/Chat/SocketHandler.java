package com.main.travelApp.services.Chat;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.main.travelApp.models.Message;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;

public class SocketHandler {

    private Socket socket;

    private MutableLiveData<Message> _onNewChat = new MutableLiveData<>();
    public LiveData<Message> onNewChat = _onNewChat;

    public SocketHandler() {
        try {
            socket = IO.socket(SOCKET_URL);
            socket.connect();

            registerOnNewChat();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void registerOnNewChat() {
        socket.on(CHAT_KEYS.BROADCAST, args -> {
            if (args != null && args.length > 0) {
                Object data = args[0];
                Log.d("DATADEBUG", data.toString());
                if (data != null && !data.toString().isEmpty()) {
                    Message chat = new Gson().fromJson(data.toString(), Message.class);
                    _onNewChat.postValue(chat);
                }
            }
        });
    }

    public void disconnectSocket() {
        socket.disconnect();
        socket.off();
    }

    public void emitChat(Message message) {
        String jsonStr = new Gson().toJson(message, Message.class);
        socket.emit(CHAT_KEYS.NEW_MESSAGE, jsonStr);
    }

    private static class CHAT_KEYS {
        static final String NEW_MESSAGE = "send";
        static final String BROADCAST = "receive";
    }

    private static final String SOCKET_URL = "ws://localhost:8085/";
}
