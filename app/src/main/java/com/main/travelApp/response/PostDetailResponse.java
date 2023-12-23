package com.main.travelApp.response;

import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.models.Post;

import java.util.List;

public class PostDetailResponse {
    private Post post;
    private List<MinimizePost> relevantPosts;

    public PostDetailResponse(Post post, List<MinimizePost> relevantPosts) {
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

    public List<MinimizePost> getRelevantPosts() {
        return relevantPosts;
    }

    public void setRelevantPosts(List<MinimizePost> relevantPosts) {
        this.relevantPosts = relevantPosts;
    }
}
