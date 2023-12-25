package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.main.travelApp.R;
import com.main.travelApp.adapters.RatingAdapter;
import com.main.travelApp.databinding.ActivityRatingBinding;
import com.main.travelApp.models.Rate;
import com.main.travelApp.models.StarDistribution;
import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.viewmodels.RatingViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class RatingActivity extends AppCompatActivity {

    private long tourId = -1;
    private String tourName;
    private ActivityRatingBinding binding;

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

        binding.txtHeading.setText(tourName);
        binding.btnBack.setOnClickListener(view -> {
            finish();
        });

        if(tourId != -1) {
            RatingViewModel ratingViewModel = new ViewModelProvider(this).get(RatingViewModel.class);
            ratingViewModel.setSharedPreferences(getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, MODE_PRIVATE));
            ratingViewModel.setContext(this);

            ratingViewModel.getRateResponse(tourId).observe(this, response -> {
                RatingAdapter ratingAdapter = new RatingAdapter(response.getRates(), this);

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
                    ((RadioGroup) contentView.findViewById(R.id.filter_group)).setOnCheckedChangeListener((radioGroup, checkedId) -> {
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

                            ratingAdapter.setRates(filteredComments);
                            
                        }else if(checkedId == R.id.option_lowest_rating){
                            List<Rate> filteredComments = response.getRates();

                            filteredComments = filteredComments.stream().sorted(new Comparator<Rate>() {
                                @Override
                                public int compare(Rate rate, Rate t1) {
                                    return rate.getRatedStar() - t1.getRatedStar();
                                }
                            }).collect(Collectors.toList());

                            ratingAdapter.setRates(filteredComments);
                        }
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
}