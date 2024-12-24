package com.social_app.social_app.service;

import com.social_app.social_app.models.Post;
import com.social_app.social_app.models.User;
import com.social_app.social_app.repository.PostRepository;
import com.social_app.social_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {
        User user = userService.findUserById(userId);

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setUser(user);
        postRepository.save(newPost);
        return newPost;
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getUser().getId() != user.getId()) {
            throw new Exception("You canot delete users posts");
        }
        postRepository.delete(post);
        return "Post is deleted successfully!";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws Exception{
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null){
            throw new Exception("User with id " + postId + " not found");
        }
        return post;
    }

    @Override
    public List<Post> findAllPosts() throws Exception {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);
        }
        else{
            user.getSavedPosts().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        }
        else{
            post.getLiked().add(user);
        }
        return postRepository.save(post);
    }
}
