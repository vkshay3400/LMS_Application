package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Integer> {
    @Query("select u from user u where u.firstName = ?1")
    Optional<UserDao> findByFirstName(String firstName);
    Optional<UserDao> findByEmail(String email);
    Optional<UserDao> findById(long id);

}
