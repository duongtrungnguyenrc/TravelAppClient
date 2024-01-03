package com.main.travelApp.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.main.travelApp.R;
import com.main.travelApp.adapters.RatingAdapter;
import com.main.travelApp.databinding.ActivityRatingBinding;
import com.main.travelApp.models.Rate;
import com.main.travelApp.models.StarDistribution;
import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.request.UpdateRateRequest;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.ui.components.MyDialog;
import com.main.travelApp.utils.KeyBoardUtils;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.RatingViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RatingActivity extends AppCompatActivity {

    private long tourId = -1;
    private String tourName;
    private ActivityRatingBinding binding;
    private RatingViewModel ratingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityRatingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ScreenManager.enableFullScreen(getWindow());

        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        BottomSheet filterBottomSheet = new BottomSheet(this, getLayoutInflater(), R.layout.frame_rating_filter, "Sắp xếp & Lọc");
        Intent intent = getIntent();
        this.tourId = intent.getLongExtra("tour-id", -1);
        this.tourName = intent.getStringExtra("tour-name");
        ratingViewModel = new ViewModelProvider(this).get(RatingViewModel.class);
        ratingViewModel.setSharedPreferences(getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, MODE_PRIVATE));
        ratingViewModel.setContext(this);

        binding.txtHeading.setText(tourName);
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });

        if(tourId != -1) {
            ratingViewModel.getRateResponse(tourId).observe(this, response -> {
                RatingAdapter ratingAdapter = new RatingAdapter(response.getRates(), this);
                ratingAdapter.setAllScreen(true);

                ratingViewModel.setRates(response);

                binding.rcvRating.setAdapter(ratingAdapter);
                binding.rcvRating.setLayoutManager(new LinearLayoutManager(this));
                binding.txtRateAverage.setText(Math.round(response.getAverage() * 10.0) / 10.0 + "");
                binding.txtTotalRates.setText("Số lượt đánh giá " + response.getTotalStar());

                StarDistribution starDistribution = response.getStarDistribution();
                binding.txtFiveStarCount.setText(String.valueOf(starDistribution.getFiveStar()));
                binding.txtFourStarCount.setText(String.valueOf(starDistribution.getFourStar()));
                binding.txtThreeStarCount.setText(String.valueOf(starDistribution.getThreeStar()));
                binding.txtTwoStarCount.setText(String.valueOf(starDistribution.getTwoStar()));

                binding.prgsFiveStar.setProgress((int) Math.round(starDistribution.getFiveStar() / (response.getAverage() / 100)));
                binding.prgsFourStar.setProgress((int) Math.round(starDistribution.getFourStar() / (response.getAverage() / 100)));
                binding.prgsThreeStar.setProgress((int) Math.round(starDistribution.getThreeStar() / (response.getAverage() / 100)));
                binding.prgsTwoStar.setProgress((int) Math.round(starDistribution.getTwoStar() / (response.getAverage() / 100)));

                filterBottomSheet.setup((dialogWindow, contentView) -> {
                    RadioGroup filterGroup = ((RadioGroup) contentView.findViewById(R.id.filter_group));
                    RadioGroup sortGroup = ((RadioGroup) contentView.findViewById(R.id.sort_group));

                    filterGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                        if (checkedId == R.id.option_newest) {
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            List<Rate> filteredComments = response.getRates();

                            filteredComments.sort((comment1, comment2) -> {
                                try {
                                    Date date1 = dateFormat.parse(comment1.getRatedDate());
                                    Date date2 = dateFormat.parse(comment2.getRatedDate());
                                    assert date2 != null;
                                    return date2.compareTo(date1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    return 0;
                                }
                            });

                            sortGroup.clearCheck();
                            ratingAdapter.setRates(filteredComments);
                            dialogWindow.dismiss();

                        }else if(checkedId == R.id.option_lowest_rating){
                            List<Rate> filteredComments = response.getRates();

                            int maxStar = getMinStar(response.getRates());

                            sortGroup.clearCheck();
                            ratingAdapter.setRates(filteredComments.stream().filter(rate -> rate.getStar() == maxStar).collect(Collectors.toList()));
                            dialogWindow.dismiss();
                        }else if(checkedId == R.id.option_highest_rating){
                            List<Rate> filteredComments = response.getRates();

                            int minStar = getMaxStar(response.getRates());

                            sortGroup.clearCheck();
                            ratingAdapter.setRates(filteredComments.stream().filter(rate -> rate.getStar() == minStar).collect(Collectors.toList()));
                            dialogWindow.dismiss();
                        }
                    });

                    sortGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                        if(checkedId == R.id.item_sort_ascending){
                            List<Rate> filteredComments = response.getRates();

                            filteredComments = filteredComments.stream().sorted(new Comparator<Rate>() {
                                @Override
                                public int compare(Rate rate, Rate t1) {
                                    return rate.getStar() - t1.getStar();
                                }
                            }).collect(Collectors.toList());

                            filterGroup.clearCheck();
                            ratingAdapter.setRates(filteredComments);
                            dialogWindow.dismiss();
                        }else if(checkedId == R.id.item_sort_descending){
                            List<Rate> filteredComments = response.getRates();

                            filteredComments = filteredComments.stream().sorted(new Comparator<Rate>() {
                                @Override
                                public int compare(Rate rate, Rate t1) {
                                    return t1.getStar() - rate.getStar();
                                }
                            }).collect(Collectors.toList());

                            filterGroup.clearCheck();
                            ratingAdapter.setRates(filteredComments);
                            dialogWindow.dismiss();
                        }
                    });

                    Button btnCancel = (Button) contentView.findViewById(R.id.btn_clear_filter);
                    Button btnApply = (Button) contentView.findViewById(R.id.btn_apply_filter);

                    btnApply.setOnClickListener(view -> {
                        dialogWindow.dismiss();
                    });
                    btnCancel.setOnClickListener(view -> {
                        filterGroup.clearCheck();
                        sortGroup.clearCheck();
                        ratingAdapter.setRates(response.getRates());
                        dialogWindow.dismiss();
                    });
                });

                binding.btnOpenFilter.setOnClickListener(view -> filterBottomSheet.show());
                binding.btnSendComment.setOnClickListener(view -> {
                    int ratedStar = (int) binding.rbTourRating.getRating();
                    String comment = binding.edtComment.getText().toString();
                    AddRateRequest request = new AddRateRequest();
                    request.setTourId(tourId);
                    request.setComment(comment);
                    request.setStar(ratedStar);

                    ratingViewModel.addRate(request, tourId);
                });
                ratingViewModel.getIsRateAdded().observe(this, data -> {
                    if(data){
                        binding.rbTourRating.setRating(0);
                        binding.edtComment.setText("");
                    }
                });
            });
        }
    }

    private int getMaxStar(List<Rate> rates) {
        final int[] max = {rates.get(0).getStar()};
        rates.forEach(rate -> {
            if(rate.getStar() > max[0]){
                max[0] = rate.getStar();
            }
        });

        return max[0];
    }

    private int getMinStar(List<Rate> rates) {
        final int[] max = {rates.get(0).getStar()};
        rates.forEach(rate -> {
            if(rate.getStar() < max[0]){
                max[0] = rate.getStar();
            }
        });

        return max[0];
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        String rateId = String.valueOf(item.getGroupId());
        switch (item.getItemId()){
            case 0 -> {
                UpdateRateRequest request = new UpdateRateRequest();
                request.setId(item.getGroupId());
                Rate currentRate = ratingViewModel.getRates().getRates().stream().filter(
                        rate -> rate.getId().equals(rateId)
                ).findFirst().get();

                MyDialog dialog = new MyDialog(this, getLayoutInflater(), R.layout.fragment_update_rate, new MyDialog.Handler() {
                    @Override
                    public void handle(AlertDialog dialog, View contentView) {
                        TextView txtTitle = (TextView) contentView.findViewById(R.id.txtTitle);
                        EditText edtContent = (EditText) contentView.findViewById(R.id.edtContent);
                        Button btnSave = (Button) contentView.findViewById(R.id.btnAgree);
                        Button btnCancel = (Button) contentView.findViewById(R.id.btnCancel);
                        RatingBar ratingBar = (RatingBar) contentView.findViewById(R.id.rbTourRating);

                        btnSave.setText("Gửi");
                        txtTitle.setText("Nội dung bình luận");
                        ratingBar.setRating(currentRate.getStar());
                        edtContent.setHint("Nhập nội dung bình luận...");
                        edtContent.setText(currentRate.getContent());

                        btnCancel.setOnClickListener(view -> {
                            dialog.dismiss();
                        });
                        btnSave.setOnClickListener(view -> {
                            request.setComment(edtContent.getText().toString());
                            request.setStar((int) ratingBar.getRating());
                            ratingViewModel.updateRate(request, dialog, tourId);
                        });
                    }
                });
                dialog.show(getSupportFragmentManager(), "UPDATE_COMMENT_DIALOG");
            }
            case 1 -> {
                MyDialog dialog = new MyDialog(this, getLayoutInflater(), R.layout.fragment_confirm_dialog, new MyDialog.Handler() {
                    @Override
                    public void handle(AlertDialog dialog, View contentView) {
                        TextView txtMessage = (TextView) contentView.findViewById(R.id.txtMessage);
                        Button btnSave = (Button) contentView.findViewById(R.id.btnChange);
                        Button btnCancel = (Button) contentView.findViewById(R.id.btnCancel);

                        btnSave.setText("Xóa");
                        txtMessage.setText("Bạn có chắc là muốn xóa bình luận này?");

                        btnCancel.setOnClickListener(view -> {
                            dialog.dismiss();
                        });
                        btnSave.setOnClickListener(view -> {
                            ratingViewModel.deleteRate(Long.valueOf(rateId), tourId);
                            dialog.dismiss();
                        });
                    }
                });
                dialog.show(getSupportFragmentManager(), "DELETE_COMMENT_DIALOG");
            }
        }
        return true;
    }
}