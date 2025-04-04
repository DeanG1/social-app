package com.social_app.social_app.service;

import com.social_app.social_app.models.Post;

import java.util.List;

public interface PostService {
    Post createNewPost(Post post, Integer userId) throws Exception;
    String deletePost(Integer postId, Integer userId) throws Exception;
    List<Post> findPostByUserId(Integer userId);
    Post findPostById(Integer postId) throws Exception;
    List<Post> findAllPosts() throws Exception;
    Post savedPost(Integer postId, Integer userId) throws Exception;
    Post likePost(Integer postId, Integer userId) throws Exception;
}
