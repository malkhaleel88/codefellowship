package com.codefellowship.codefellowship.repository;

import com.codefellowship.codefellowship.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long>{

    ApplicationUser findApplicationUserByUsername(String username);

    ApplicationUser findApplicationUserById(long id);
}
