package com.codefellowship.codefellowship.controller;

import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.model.Post;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import com.codefellowship.codefellowship.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;


    @GetMapping("/posts")
    public String getPostPage() {
        return "posts";
    }

    @Transactional
    @PostMapping("/posts")
    public RedirectView createNewSongs(@ModelAttribute Post post) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        post.setApplicationuser(user);
        user.getPost().add(post);
        postRepository.save(post);
        applicationUserRepository.save(user);
        return new RedirectView("/index");
    }
    @GetMapping("/home")
    public String gethome(){
        return "index";
    }
}
