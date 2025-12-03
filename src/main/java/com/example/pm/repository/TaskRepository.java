package com.example.pm.repository;

import com.example.pm.entity.Task;
import com.example.pm.entity.TaskPriority;
import com.example.pm.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByProjectIdAndProjectUserId(Integer projectId, Integer userId, Pageable pageable);
    Task findByIdAndProjectUserId(Integer taskId, Integer userId);
    Page<Task> findByProjectUserId(Integer userId,Pageable pageable);

    Page<Task> findByProjectIdAndProjectUserIdAndStatus(Integer projectId,Integer userId, TaskStatus status, Pageable pageable);
    Page<Task> findByProjectIdAndProjectUserIdAndPriority(Integer projectId,Integer userId, TaskPriority priority, Pageable pageable);

    @Query("select t from Task t where (lower(t.title) like lower(concat('%', :q, '%')) or lower(t.description) like lower(concat('%', :q, '%'))) and t.project.user.id = :userId")
    Page<Task> searchForUser(Integer userId, String q, Pageable pageable);
}
