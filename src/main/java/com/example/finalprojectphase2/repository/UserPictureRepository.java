package com.example.finalprojectphase2.repository;

import com.example.finalprojectphase2.model.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {
}
