package com.codefellowship.codefellowship.controller;

import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.model.Post;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import com.codefellowship.codefellowship.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class ApplicationController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
     PasswordEncoder passwordEncoder;



    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView attemptSignUp(ApplicationUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        return new RedirectView("/login");
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping ("/login")
    public RedirectView LoginPage(@RequestParam String username) {
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        List<Post> list = postRepository.findAllByUserId(user.getId()).orElseThrow();
        model.addAttribute("users", user);
        model.addAttribute("posts", list);
        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String getProfilePageById(@PathVariable String id , Model model) {
        long Id = Long.parseLong(id);
        ApplicationUser user = applicationUserRepository.findApplicationUserById(Id);
        List<Post> list = postRepository.findAllByUserId(user.getId()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("posts", list);
        return "oneUser";
    }

    @GetMapping("/posts")
    public String getPosts(@ModelAttribute Post posts, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Post> post = postRepository.findAllByUser_Username(userDetails.getUsername()).orElseThrow();
        model.addAttribute("posts", post);

        return "post";
    }

    @PostMapping("/posts")
    public RedirectView addPosts(@ModelAttribute Post posts) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        posts.setUser(user);
        applicationUserRepository.save(user);
        postRepository.save(posts);
        user.setPosts(Collections.singletonList(posts));
        return new RedirectView("/profile") ;
    }

    @GetMapping("/users")
    public String getUsersPage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        List<ApplicationUser> users = applicationUserRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/follow/{id}")
    public RedirectView followUser(@PathVariable long id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        ApplicationUser userFollow = applicationUserRepository.findApplicationUserById(id);

        user.getFollowers().add(userFollow);
        applicationUserRepository.save(user);
        return new RedirectView("/users");
    }


    @GetMapping("/feed")
    public String getFeedPage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        Set<ApplicationUser> userFeed = user.getFollowers();
        model.addAttribute("followers", userFeed);
        return "feed";
    }

}
