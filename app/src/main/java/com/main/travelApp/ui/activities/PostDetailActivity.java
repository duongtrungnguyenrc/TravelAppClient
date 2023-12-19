package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.main.travelApp.R;
import com.main.travelApp.adapters.AllPostAdapter;
import com.main.travelApp.adapters.ParagraphAdapter;
import com.main.travelApp.adapters.PostCommentAdapter;
import com.main.travelApp.databinding.ActivityPostDetailBinding;
import com.main.travelApp.response.RateResponse;
import com.main.travelApp.utils.LayoutManagerUtil;
import com.main.travelApp.viewmodels.BlogDetailViewModel;
import com.main.travelApp.viewmodels.factories.BlogDetailViewModelFactory;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPostDetailBinding binding;
    private ParagraphAdapter paragraphAdapter;
    private AllPostAdapter relevantPostAdapter;
    private PostCommentAdapter postCommentAdapter;
    private BlogDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbPostDetail);
        long postId = getIntent().getExtras().getLong("postId", 1);

        ViewModelProvider.Factory factory = new BlogDetailViewModelFactory(postId);
        viewModel = new ViewModelProvider(this, factory).get(BlogDetailViewModel.class);

        init();
        setEvents();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void init(){
        this.setTitle("");
        paragraphAdapter = new ParagraphAdapter();
        relevantPostAdapter = new AllPostAdapter(this);
        postCommentAdapter = new PostCommentAdapter(this);
        viewModel.getPostDetailResponse().observe(this, data -> {
            paragraphAdapter.setParagraphs(data.getPost().getParagraphs());
            relevantPostAdapter.setPosts(data.getRelevantPosts());
            Picasso.get()
                    .load(data.getPost().getImg())
                    .placeholder(R.color.light_gray)
                    .error(R.color.light_gray)
                    .into(binding.imgPostThumbnail);
            binding.txtAuthor.setText(data.getPost().getAuthor());
            binding.txtPostTitle.setText(data.getPost().getTitle());
            binding.txtPostTime.setText(data.getPost().getTime().split(" ")[0]);
            binding.txtPostType.setText(data.getPost().getType());
        });
        viewModel.getRateResponse().observe(this, data -> {
            postCommentAdapter.setComments(data.getRates());
        });

        binding.rcvParagraphs.setItemAnimator(new DefaultItemAnimator());
        binding.rcvParagraphs.setLayoutManager(LayoutManagerUtil.disabledScrollLinearManager(this, LinearLayoutManager.VERTICAL));
        binding.rcvParagraphs.setAdapter(paragraphAdapter);
        binding.rcvRelevantPost.setItemAnimator(new DefaultItemAnimator());
        binding.rcvRelevantPost.setLayoutManager(LayoutManagerUtil.disabledScrollLinearManager(this, LinearLayoutManager.VERTICAL));
        binding.rcvRelevantPost.setAdapter(relevantPostAdapter);
        binding.rcvComment.setItemAnimator(new DefaultItemAnimator());
        binding.rcvComment.setLayoutManager(LayoutManagerUtil.disabledScrollLinearManager(this, LinearLayoutManager.VERTICAL));
        binding.rcvComment.setAdapter(postCommentAdapter);
    }

    public void setEvents(){
        binding.btnPrevPage.setOnClickListener(this);
        binding.btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RateResponse response = viewModel.getRateResponse().getValue();
                int commentPage = viewModel.getCommentPage();
                if(response != null && commentPage < response.getPages()){
                    viewModel.setCommentPage(viewModel.getCommentPage() + 1);
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view == binding.btnPrevPage){
            if(viewModel.getCommentPage() > 1){
                viewModel.setCommentPage(viewModel.getCommentPage() - 1);
            }
        }
    }
}