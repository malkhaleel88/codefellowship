package com.codefellowship.codefellowship.controller;

import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView attemptSignUp(@ModelAttribute ApplicationUser user) {
        user.setDateOfBirth(passwordEncoder.encode(user.getPassword()));
        user = applicationUserRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
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
        return new RedirectView("/home");
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());

        model.addAttribute("user", user);
        return "profile";
    }
}
