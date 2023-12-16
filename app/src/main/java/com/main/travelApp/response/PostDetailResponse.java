package com.main.travelApp.response;

import com.main.travelApp.models.GeneralPost;
import com.main.travelApp.models.Post;

import java.util.List;

public class PostDetailResponse {
    private Post post;
    private List<GeneralPost> relevantPosts;

    public PostDetailResponse(Post post, List<GeneralPost> relevantPosts) {
        this.post = post;
        this.relevantPosts = relevantPosts;
    }

    public PostDetailResponse() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<GeneralPost> getRelevantPosts() {
        return relevantPosts;
    }

    public void setRelevantPosts(List<GeneralPost> relevantPosts) {
        this.relevantPosts = relevantPosts;
    }
}
