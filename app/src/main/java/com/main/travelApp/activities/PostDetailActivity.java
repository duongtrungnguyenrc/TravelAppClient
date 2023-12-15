package com.main.travelApp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.main.travelApp.adapters.AllPostAdapter;
import com.main.travelApp.adapters.ParagraphAdapter;
import com.main.travelApp.adapters.PostCommentAdapter;
import com.main.travelApp.databinding.ActivityPostDetailBinding;
import com.main.travelApp.models.Paragraph;
import com.main.travelApp.models.Post;
import com.main.travelApp.models.Rate;
import com.main.travelApp.utils.LinearLayoutManagerUtil;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity {
    private ActivityPostDetailBinding binding;
    private ParagraphAdapter paragraphAdapter;
    private AllPostAdapter relevantPostAdapter;
    private PostCommentAdapter postCommentAdapter;
    private List<Paragraph> paragraphs;
    private List<Rate> comments;
    private List<Post> relevantPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.tbPostDetail);

        init();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void init(){
        this.setTitle("");

        fetchPost();
        paragraphAdapter = new ParagraphAdapter(paragraphs);
        relevantPostAdapter = new AllPostAdapter(relevantPosts, this);
        postCommentAdapter = new PostCommentAdapter(comments, this);

        binding.rcvParagraphs.setItemAnimator(new DefaultItemAnimator());
        binding.rcvParagraphs.setLayoutManager(LinearLayoutManagerUtil.disabledScrollManager(this, LinearLayoutManager.VERTICAL));
        binding.rcvParagraphs.setAdapter(paragraphAdapter);
        binding.rcvRelevantPost.setItemAnimator(new DefaultItemAnimator());
        binding.rcvRelevantPost.setLayoutManager(LinearLayoutManagerUtil.disabledScrollManager(this, LinearLayoutManager.VERTICAL));
        binding.rcvRelevantPost.setAdapter(relevantPostAdapter);
        binding.rcvComment.setItemAnimator(new DefaultItemAnimator());
        binding.rcvComment.setLayoutManager(LinearLayoutManagerUtil.disabledScrollManager(this, LinearLayoutManager.VERTICAL));
        binding.rcvComment.setAdapter(postCommentAdapter);
    }

    private void fetchPost(){
        paragraphs = new ArrayList<>();
        relevantPosts = new ArrayList<>();
        comments = new ArrayList<>();

        paragraphs.add(
                new Paragraph("In the early stages, a blog was a personal web log or journal in which someone could share information or their opinion on a variety of topics. The information was posted reverse chronologically, so the most recent post would appear first.\n" +
                        "\n" +
                        "Nowadays, a blog is a regularly updated website or web page, and can either be used for personal use or to fulfill a business need.\n" +
                        "\n" +
                        "For instance, HubSpot blogs about various topics concerning marketing, sales, and service because HubSpot sells products related to those three subjects — so, more than likely, the type of readers HubSpot‘s blog attracts are going to be similar to HubSpot’s core buyer persona.",
                        "hahah",
                        "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701367762/e9a0fee6-ae2b-46c5-b378-3bdd736ecd70.png")
        );
        paragraphs.add(
                new Paragraph("In the early stages, a blog was a personal web log or journal in which someone could share information or their opinion on a variety of topics. The information was posted reverse chronologically, so the most recent post would appear first.\n" +
                        "\n" +
                        "Nowadays, a blog is a regularly updated website or web page, and can either be used for personal use or to fulfill a business need.\n" +
                        "\n" +
                        "For instance, HubSpot blogs about various topics concerning marketing, sales, and service because HubSpot sells products related to those three subjects — so, more than likely, the type of readers HubSpot‘s blog attracts are going to be similar to HubSpot’s core buyer persona.",
                        "hahah",
                        "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701404439/aed9f2f3-b3c4-4f27-ba31-fa0570f0fb78.jpg")
        );
        paragraphs.add(
                new Paragraph("In the early stages, a blog was a personal web log or journal in which someone could share information or their opinion on a variety of topics. The information was posted reverse chronologically, so the most recent post would appear first.\n" +
                        "\n" +
                        "Nowadays, a blog is a regularly updated website or web page, and can either be used for personal use or to fulfill a business need.\n" +
                        "\n" +
                        "For instance, HubSpot blogs about various topics concerning marketing, sales, and service because HubSpot sells products related to those three subjects — so, more than likely, the type of readers HubSpot‘s blog attracts are going to be similar to HubSpot’s core buyer persona.",
                        "hahah",
                        "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701404439/aed9f2f3-b3c4-4f27-ba31-fa0570f0fb78.jpg")
        );
        paragraphs.add(
                new Paragraph("In the early stages, a blog was a personal web log or journal in which someone could share information or their opinion on a variety of topics. The information was posted reverse chronologically, so the most recent post would appear first.\n" +
                        "\n" +
                        "Nowadays, a blog is a regularly updated website or web page, and can either be used for personal use or to fulfill a business need.\n" +
                        "\n" +
                        "For instance, HubSpot blogs about various topics concerning marketing, sales, and service because HubSpot sells products related to those three subjects — so, more than likely, the type of readers HubSpot‘s blog attracts are going to be similar to HubSpot’s core buyer persona.",
                        "hahah",
                        "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701407190/3c78828c-0bba-4372-b716-c16ae9d0655f.png")
        );

        Post post1 = new Post(1, "10 mẹo giúp tăng hiệu suất làm việc từ xa", "Những mẹo nhỏ có thể giúp bạn làm việc hiệu quả hơn khi ở nhà", "2023-12-14", "John Doe", "thumbnail1.jpg");
        Post post2 = new Post(2, "Sự kiện công nghệ CES 2024: Những điểm nổi bật", "Cập nhật những công nghệ mới và sản phẩm đáng chú ý từ sự kiện CES năm nay", "2023-12-15", "Alice Smith", "thumbnail2.jpg");
        Post post3 = new Post(3, "Hướng dẫn sử dụng machine learning trong phân tích dữ liệu", "Một số kỹ thuật machine learning có thể áp dụng để phân tích dữ liệu hiệu quả", "2023-12-16", "Emma Johnson", "thumbnail3.jpg");
        relevantPosts.add(post1);
        relevantPosts.add(post2);
        relevantPosts.add(post3);

        Rate rate1 = new Rate("Phan Hoàn Việt", "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701403842/abd96c92-d58a-4dd8-98d8-d1c9cda58341.jpg", 5, "Đây là một blog cực hay luôn", true);
        Rate rate2 = new Rate("Phan Hoàn Việt", "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701403842/abd96c92-d58a-4dd8-98d8-d1c9cda58341.jpg", 5, "Chỗ này tui có tới nè", true);
        Rate rate3 = new Rate("Đặng Thị Ngọc Yến", "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701370053/a4c18c73-7cf0-43a3-bea3-e76404b3a95f.jpg", 5, "Hahah, hay á", false);
        Rate rate4 = new Rate("Phan Ngọc Bảo Trân", "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701758825/205fff99-ca49-4b2c-a933-991a7728a108.jpg", 5, "Đẹp", false);
        Rate rate5 = new Rate("Phan Hoàn Việt", "https://res.cloudinary.com/dwv7gmfcr/image/upload/v1701403842/abd96c92-d58a-4dd8-98d8-d1c9cda58341.jpg", 5, "Đây là một blog cực hay luôn", true);
        comments.add(rate1);
        comments.add(rate2);
        comments.add(rate3);
        comments.add(rate4);
        comments.add(rate5);

    }
}