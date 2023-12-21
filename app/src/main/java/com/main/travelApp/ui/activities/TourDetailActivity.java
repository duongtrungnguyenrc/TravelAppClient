package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.main.travelApp.R;
import com.main.travelApp.adapters.ParagraphAdapter;
import com.main.travelApp.adapters.RatingAdapter;
import com.main.travelApp.adapters.TourDateAdapter;
import com.main.travelApp.adapters.TourGalleryAdapter;
import com.main.travelApp.databinding.ActivityTourDetailBinding;
import com.main.travelApp.models.Paragraph;
import com.main.travelApp.models.Tour;
import com.main.travelApp.ui.components.BottomSheet;
import com.main.travelApp.ui.components.Stepper.Step;
import com.main.travelApp.ui.components.Stepper.StepperFormListener;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.utils.TourScheduleStep;
import com.main.travelApp.viewmodels.TourDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TourDetailActivity extends AppCompatActivity implements StepperFormListener {
    private ActivityTourDetailBinding binding;
    private TourDetailViewModel tourDetailViewModel;
    private BottomSheet overviewBottomSheet;
    private int tourId = -1;
    private Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        overviewBottomSheet = new BottomSheet(this, getLayoutInflater(), R.layout.frame_tour_overview, "Thông tin về chuyến đi");
        this.binding = ActivityTourDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ScreenManager.enableFullScreen(getWindow());

        Intent intent = getIntent();
        tourId = intent.getIntExtra("tour-id", -1);

        binding.btnSeeAllOverview.setOnClickListener(view -> {
            showOverviewBottomSheet(tour.getOverview().getParagraphs());
        });

        binding.btnBack.setOnClickListener(view -> {
            finish();
        });

        binding.layoutToOverview.setOnClickListener(view -> {
            binding.rootScrollView.smoothScrollTo(0, binding.layoutOverview.getTop() - 50);
        });

        tourDetailViewModel = new ViewModelProvider(this).get(TourDetailViewModel.class);

        if(tourId != -1) {
            tourDetailViewModel.getTour(tourId).observe(this, tour -> {
                this.tour = tour;
                binding.txtTourName.setText(tour.getName());
                binding.txtLocation.setText(tour.getLocation());
                binding.txtMaxPeople.setText(tour.getMaxPeople() + " Người");
                binding.txtVehicle.setText(tour.getVehicle());
                binding.txtRatingAverage.setText(generateRatingRank(tour.getRatedStar()));
                binding.txtRatingCount.setText("(" + tour.getTotalRates() + " lượt đánh giá)");

                TourGalleryAdapter tourGalleryAdapter = new TourGalleryAdapter(tour.getOverview().getParagraphs(), this);
                TourDateAdapter tourDateAdapter = new TourDateAdapter(tour.getTourDate(), tour.getName(),this, tour.getId());
                initViewPager(binding.pgTourGallery, tourGalleryAdapter);
                binding.rcvTourDate.setAdapter(tourDateAdapter);
                binding.rcvTourDate.setLayoutManager(new LinearLayoutManager(this));

                List<Step<?>> scheduleSteps = new ArrayList<>();
                tour.getSchedules().forEach(step -> {
                    final TourScheduleStep tourScheduleStep = new TourScheduleStep(step.getTime(), step.getContent());
                    scheduleSteps.add(tourScheduleStep);
                });

                binding.btnSeeAllComment.setOnClickListener(view -> {
                    Intent ratingIntent = new Intent(TourDetailActivity.this, RatingActivity.class);
                    ratingIntent.putExtra("tour-id", tourId);
                    ratingIntent.putExtra("tour-name", tour.getName());
                    startActivity(ratingIntent);
                });
                binding.stepperForm.setup(TourDetailActivity.this, scheduleSteps).init();
                binding.txtDescription.setText(Objects.requireNonNull(tour.getOverview().getParagraphs().get(0)).getContent());
                Picasso.get()
                        .load(tour.getOverview().getParagraphs().get(0).getImage().getSrc())
                        .placeholder(R.color.light_gray)
                        .error(R.color.light_gray)
                        .into(binding.imgOverview);
                fetchRate(tourId);
            });
        }
    }

    private void fetchRate(long id) {
        tourDetailViewModel.getRates(id).observe(this, res -> {
            Log.d("rating", res.toString());
            RatingAdapter ratingAdapter = new RatingAdapter(res.getRates(), this);
            binding.rcvRating.setAdapter(ratingAdapter);
            binding.rcvRating.setLayoutManager(new LinearLayoutManager(this));
        });
    }

    private void initViewPager(ViewPager2 viewPager2, RecyclerView.Adapter adapter){
        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(transformer);
    }

    private String generateRatingRank(double star) {
        String ratingRank = "Kém";

        if(star == 5) {
            ratingRank = "Rất tốt";
        } else if (star > 4) {
            ratingRank = "Tốt";
        } else if (star > 3) {
            ratingRank = "Trung bình";
        }

        return star + " " + ratingRank;
    }

    private void showOverviewBottomSheet(List<Paragraph> paragraphs) {
        overviewBottomSheet.show((dialogWindow, contentView) -> {
            ParagraphAdapter paragraphAdapter = new ParagraphAdapter(paragraphs);
            RecyclerView rcvTourOverview =  contentView.findViewById(R.id.rcv_tour_overview);

            rcvTourOverview.setAdapter(paragraphAdapter);
            rcvTourOverview.setLayoutManager(new LinearLayoutManager(this));
        });
    }
}