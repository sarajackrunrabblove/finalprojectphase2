package com.example.finalprojectphase2.repository;

import com.example.finalprojectphase2.model.HomeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeServiceRepository extends JpaRepository<HomeService, Long> {
    Optional<HomeService> findByTitle(String title);
}
