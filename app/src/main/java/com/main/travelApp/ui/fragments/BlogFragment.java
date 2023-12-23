package com.main.travelApp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.main.travelApp.utils.LayoutManagerUtil;
import com.main.travelApp.viewmodels.BlogViewModel;

public class BlogFragment extends Fragment {
    private FragmentBlogBinding blogBinding;
    private NewestPostsAdapter newestPostsAdapter;
    private TopPostsAdapter topPostsAdapter;
    private AllPostAdapter allPostAdapter;
    private BlogViewModel blogViewModel;
    private final Handler newestPostsHandler = new Handler();
    private final Runnable newestPostsRunnable = new Runnable() {
        @Override
        public void run() {
            ViewPager2 viewPager2 = blogBinding.pgNewestPosts;
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        blogBinding = FragmentBlogBinding.inflate(inflater, container, false);
        return blogBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        blogViewModel = new ViewModelProvider(getActivity()).get(BlogViewModel.class);
        init();
    }

    private void init(){
        newestPostsAdapter = new NewestPostsAdapter();
        newestPostsAdapter.setContext(getContext());
        topPostsAdapter = new TopPostsAdapter();
        topPostsAdapter.setContext(getContext());
        allPostAdapter = new AllPostAdapter(getContext());

        newestPostsAdapter.setViewPager2(blogBinding.pgNewestPosts);

        initViewPager(blogBinding.pgNewestPosts, newestPostsAdapter);

        blogBinding.rcvTopPosts.setAdapter(topPostsAdapter);
        blogBinding.rcvTopPosts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        blogBinding.rcvAllPosts.setAdapter(allPostAdapter);
        blogBinding.rcvAllPosts.setLayoutManager(LayoutManagerUtil.disabledScrollLinearManager(getContext(), LinearLayoutManager.VERTICAL));
        blogBinding.rcvAllPosts.setItemAnimator(new DefaultItemAnimator());

        blogViewModel.getNewestPosts().observe(getViewLifecycleOwner(), posts -> {
            newestPostsAdapter.setPosts(posts);
        });

        blogViewModel.getAllPosts()
                .observe(getViewLifecycleOwner(), blogsResponse -> {
                    if(blogsResponse != null){
                        allPostAdapter.setPosts(blogsResponse.getPosts());
                    }
                });

        blogViewModel.getTopPosts().observe(getViewLifecycleOwner(), posts -> {
            if(posts != null)
                topPostsAdapter.setPosts(posts);
        });

       blogBinding.nextPage.setOnClickListener(view -> {
           if(blogViewModel.getAllPostPage() < blogViewModel.getAllPosts().getValue().getPages()){
               blogViewModel.setAllPostPage(blogViewModel.getAllPostPage() + 1);
           }
       });

       blogBinding.previousPage.setOnClickListener(view -> {
           if(blogViewModel.getAllPostPage() > 1){
               blogViewModel.setAllPostPage(blogViewModel.getAllPostPage() - 1);
           }
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
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                newestPostsHandler.removeCallbacks(newestPostsRunnable);
                newestPostsHandler.postDelayed(newestPostsRunnable, 3000);
            }
        });
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