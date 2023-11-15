package com.main.travelApp.fragments;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.main.travelApp.R;
import com.main.travelApp.adapters.PlaceListAdapter;
import com.main.travelApp.adapters.PostAdapter;
import com.main.travelApp.adapters.TourListAdapter;
import com.main.travelApp.models.Post;
import com.main.travelApp.models.Tour;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private TourListAdapter tourListAdapter;
    private PlaceListAdapter placeListAdapter;
    private PostAdapter postAdapter;
    private List<Tour> tours;
    private List<Post> posts;
    private RecyclerView rcvTours, rcvPlaces;
    private ViewPager2 pgPosts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvTours = view.findViewById(R.id.rcvTours);
        rcvPlaces = view.findViewById(R.id.rcvPlaces);
        pgPosts = view.findViewById(R.id.pgPosts);

        init();
    }

    private void init(){
        fetchTours();
        fetchPosts();
        List<Tour> top4Tours = tours.subList(0, 4);

        tourListAdapter = new TourListAdapter();
        placeListAdapter = new PlaceListAdapter();
        postAdapter = new PostAdapter();

        placeListAdapter.setTours(top4Tours);
        tourListAdapter.setTours(tours);
        postAdapter.setPosts(posts);

        rcvTours.setAdapter(tourListAdapter);
        rcvTours.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        ));

        rcvPlaces.setAdapter(placeListAdapter);
        rcvPlaces.setLayoutManager(new GridLayoutManager(
                getContext(), 2
        ));

        pgPosts.setAdapter(postAdapter);

    }

    private void fetchTours(){
        tours = new ArrayList<>();
        tours.add(new Tour(1L, "Tour Hạ Long", 4.5f, "Hanoi", "2023-11-15", 3, 2500000L, 23)); // 2,500,000 VNĐ
        tours.add(new Tour(2L, "Tour Đà Nẵng", 4.2f, "Ho Chi Minh City", "2023-11-20", 5, 3500000L, 123)); // 3,500,000 VNĐ
        tours.add(new Tour(3L, "Tour Nha Trang", 4.8f, "Hanoi", "2023-11-25", 4, 4000000L, 8172)); // 4,000,000 VNĐ
        tours.add(new Tour(4L, "Tour Phú Quốc", 4.7f, "Da Nang", "2023-11-30", 7, 6000000L, 1723)); // 6,000,000 VNĐ
        tours.add(new Tour(5L, "Tour Sapa", 4.3f, "Hanoi", "2023-12-05", 2, 1500000L, 12873)); // 1,500,000 VNĐ
        tours.add(new Tour(6L, "Tour Đà Lạt", 4.6f, "Da Nang", "2023-12-10", 3, 1800000L, 1273)); // 1,800,000 VNĐ
        tours.add(new Tour(7L, "Tour Cần Thơ", 4.9f, "Ho Chi Minh City", "2023-12-15", 2, 1200000L, 1923)); // 1,200,000 VNĐ
        tours.add(new Tour(8L, "Tour Huế", 4.4f, "Da Nang", "2023-12-20", 4, 3000000L, 52423)); // 3,000,000 VNĐ
        tours.add(new Tour(9L, "Tour Hội An", 4.1f, "Da Nang", "2023-12-25", 3, 2200000L, 2934)); // 2,200,000 VNĐ
        tours.add(new Tour(10L, "Tour Mũi Né", 4.0f, "Ho Chi Minh City", "2023-12-30", 4, 2800000L, 28734)); // 2,800,000 VNĐ
    }

    private void fetchPosts(){
        Post post1 = new Post(1, "Top 5 địa điểm du lịch đẹp nhất Đà Lạt", "Mô tả các địa điểm du lịch hấp dẫn ở Đà Lạt", "2 giờ trước", "Nguyễn Hoàng Anh", "da-lat-1.jpg");
        Post post2 = new Post(2, "Khám phá các bãi biển đẹp ở Nha Trang", "Giới thiệu về các bãi biển nổi tiếng ở Nha Trang", "4 giờ trước", "Lê Thị Hằng", "nha-trang-2.jpg");
        Post post3 = new Post(3, "Trải nghiệm du lịch tại phố cổ Hội An lịch sử", "Mô tả các điểm tham quan hấp dẫn tại phố cổ Hội An", "6 giờ trước", "Trần Việt Hoàng", "hoi-an-3.jpg");
        Post post4 = new Post(4, "Đi du lịch Sapa mùa xuân - những điều cần biết", "Hướng dẫn du lịch Sapa vào mùa xuân", "8 giờ trước", "Nguyễn Thị Hải Yến", "sapa-4.jpg");
        Post post5 = new Post(5, "Khám phá vẻ đẹp của thành phố Hồ Chí Minh qua du lịch", "Giới thiệu các điểm du lịch hấp dẫn ở TP. Hồ Chí Minh", "10 giờ trước", "Lê Tuấn Anh", "ho-chi-minh-5.jpg");

        posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        posts.add(post5);
    }
}