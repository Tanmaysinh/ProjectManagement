package com.example.pm.repository;

import com.example.pm.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByUserId(Integer userId);
    Optional<Project> findByIdAndUserId(Integer id, Integer userId);
}
