package com.codefellowship.codefellowship.webSecurityConfig;

import com.codefellowship.codefellowship.model.ApplicationUser;
import com.codefellowship.codefellowship.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ApplicationUser applicationUser = applicationUserRepository.findApplicationUserByUsername(username);

        if(applicationUser == null){
            throw new UsernameNotFoundException(username + "Not Found");
        }
        return applicationUser;
    }

}
