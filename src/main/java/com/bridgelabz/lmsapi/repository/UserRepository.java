package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Integer> {
    @Query("select u from user u where u.firstName = ?1")
    UserDao findByFirstName(String firstName);
    UserDao findByEmail(String email);
    UserDao findById(long id);
}
