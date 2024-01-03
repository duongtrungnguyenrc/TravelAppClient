package com.main.travelApp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;

import com.main.travelApp.R;
import com.main.travelApp.adapters.AllPostAdapter;
import com.main.travelApp.adapters.ParagraphAdapter;
import com.main.travelApp.adapters.PostCommentAdapter;
import com.main.travelApp.databinding.ActivityPostDetailBinding;
import com.main.travelApp.request.AddRateRequest;
import com.main.travelApp.response.RateResponse;
import com.main.travelApp.ui.components.ExpiredDialog;
import com.main.travelApp.utils.KeyBoardUtils;
import com.main.travelApp.utils.LayoutManagerUtil;
import com.main.travelApp.utils.SharedPreferenceKeys;
import com.main.travelApp.utils.ScreenManager;
import com.main.travelApp.viewmodels.BlogDetailViewModel;
import com.main.travelApp.viewmodels.factories.BlogDetailViewModelFactory;
import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPostDetailBinding binding;
    private ParagraphAdapter paragraphAdapter;
    private AllPostAdapter relevantPostAdapter;
    private PostCommentAdapter postCommentAdapter;
    private BlogDetailViewModel viewModel;
    private SharedPreferences sharedPreferences;
    private String accessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        long postId = getIntent().getExtras().getLong("postId", 1);

        sharedPreferences = getSharedPreferences(SharedPreferenceKeys.USER_SHARED_PREFS, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SharedPreferenceKeys.USER_ACCESS_TOKEN, "");

        ViewModelProvider.Factory factory = new BlogDetailViewModelFactory(accessToken, postId);
        viewModel = new ViewModelProvider(this, factory).get(BlogDetailViewModel.class);
        viewModel.setContext(this);
        viewModel.setSharedPreferences(sharedPreferences);

        ScreenManager.enableFullScreen(getWindow());
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
        postCommentAdapter.setFragmentManager(getSupportFragmentManager());
        postCommentAdapter.setLayoutInflater(getLayoutInflater());
        postCommentAdapter.setViewModel(viewModel);

        viewModel.addView();

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
            postCommentAdapter.submitList(data.getRates());
        });

        viewModel.getIsExpired().observe(this, isExpired -> {
            if(isExpired == true){
                ExpiredDialog dialog = new ExpiredDialog(this, sharedPreferences);
                dialog.show(getSupportFragmentManager(), "EXPIRED_DIALOG");
            }
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
        binding.btnBack.setOnClickListener(view -> finish());
        binding.btnSubmitComment.setEnabled(false);
        binding.mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                KeyBoardUtils.hideKeyboard(PostDetailActivity.this);
                return true;
            }
        });
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
        binding.btnSubmitComment.setOnClickListener(this);
        binding.edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    binding.btnSubmitComment.setEnabled(false);
                }else{
                    binding.btnSubmitComment.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view == binding.btnPrevPage){
            if(viewModel.getCommentPage() > 1){
                viewModel.setCommentPage(viewModel.getCommentPage() - 1);
            }
        }else if(view == binding.btnSubmitComment){
            String content = binding.edtComment.getText().toString();
            if(!content.isEmpty()){
                AddRateRequest request = new AddRateRequest();
                request.setStar(5);
                request.setComment(content);
                request.setBlogId(viewModel.getId());
                request.setTourId(null);
                viewModel.addRate(accessToken, request);
                binding.edtComment.setText("");
            }
        }
    }
}