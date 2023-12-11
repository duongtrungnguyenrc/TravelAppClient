package com.main.travelApp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.travelApp.adapters.AllPostAdapter;
import com.main.travelApp.adapters.NewestPostsAdapter;
import com.main.travelApp.adapters.TopPostsAdapter;
import com.main.travelApp.databinding.FragmentBlogBinding;
import com.main.travelApp.models.Post;

import java.util.ArrayList;
import java.util.List;

public class BlogFragment extends Fragment {
    private FragmentBlogBinding blogBinding;
    private NewestPostsAdapter newestPostsAdapter;
    private TopPostsAdapter topPostsAdapter;
    private AllPostAdapter allPostAdapter;
    private List<Post> newestPosts;
    private List<Post> topPosts;
    private List<Post> allPosts;
    private Handler newestPostsHandler = new Handler();
    private Runnable newestPostsRunnable = new Runnable() {
        @Override
        public void run() {
            ViewPager2 viewPager2 = blogBinding.pgNewestPosts;
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        blogBinding = FragmentBlogBinding.inflate(inflater, container, false);
        return blogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init(){
        fetchNewestPosts();
        fetchTopPosts();
        fetchAllPosts();

        newestPostsAdapter = new NewestPostsAdapter();
        topPostsAdapter = new TopPostsAdapter();
        allPostAdapter = new AllPostAdapter();

        newestPostsAdapter.setPosts(newestPosts);
        newestPostsAdapter.setViewPager2(blogBinding.pgNewestPosts);

        allPostAdapter.setPosts(allPosts);

        topPostsAdapter.setPosts(topPosts);

        initViewPager(blogBinding.pgNewestPosts, newestPostsAdapter);

        blogBinding.rcvTopPosts.setAdapter(topPostsAdapter);
        blogBinding.rcvTopPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        blogBinding.rcvAllPosts.setAdapter(allPostAdapter);
        blogBinding.rcvAllPosts.setLayoutManager(layoutManager);
        blogBinding.rcvAllPosts.setItemAnimator(new DefaultItemAnimator());
    }

    private void initViewPager(ViewPager2 viewPager2, RecyclerView.Adapter adapter){
        viewPager2.setAdapter(adapter);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(transformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                newestPostsHandler.removeCallbacks(newestPostsRunnable);
                newestPostsHandler.postDelayed(newestPostsRunnable, 3000);
            }
        });
    }

    private void fetchAllPosts(){
        allPosts = newestPosts;
    }

    private void fetchNewestPosts(){
        Post post1 = new Post(1, "Top 5 địa điểm du lịch đẹp nhất Đà Lạt", "Mô tả các địa điểm du lịch hấp dẫn ở Đà Lạt", "2 giờ trước", "Nguyễn Hoàng Anh", "da-lat-1.jpg");
        Post post2 = new Post(2, "Khám phá các bãi biển đẹp ở Nha Trang", "Giới thiệu về các bãi biển nổi tiếng ở Nha Trang", "4 giờ trước", "Lê Thị Hằng", "nha-trang-2.jpg");
        Post post3 = new Post(3, "Trải nghiệm du lịch tại phố cổ Hội An lịch sử", "Mô tả các điểm tham quan hấp dẫn tại phố cổ Hội An", "6 giờ trước", "Trần Việt Hoàng", "hoi-an-3.jpg");
        Post post4 = new Post(4, "Đi du lịch Sapa mùa xuân - những điều cần biết", "Hướng dẫn du lịch Sapa vào mùa xuân", "8 giờ trước", "Nguyễn Thị Hải Yến", "sapa-4.jpg");
        Post post5 = new Post(5, "Khám phá vẻ đẹp của thành phố Hồ Chí Minh qua du lịch", "Giới thiệu các điểm du lịch hấp dẫn ở TP. Hồ Chí Minh", "10 giờ trước", "Lê Tuấn Anh", "ho-chi-minh-5.jpg");

        newestPosts = new ArrayList<>();
        newestPosts.add(post1);
        newestPosts.add(post2);
        newestPosts.add(post3);
        newestPosts.add(post4);
        newestPosts.add(post5);
    }

    private void fetchTopPosts(){
        topPosts = newestPosts;
    }

    @Override
    public void onPause() {
        super.onPause();
        newestPostsHandler.removeCallbacks(newestPostsRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        newestPostsHandler.postDelayed(newestPostsRunnable, 3000);
    }
}