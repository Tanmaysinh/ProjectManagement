package com.example.pm.controller;

import com.example.pm.config.ActiveUser;
import com.example.pm.dto.TaskDTO;
import com.example.pm.entity.Task;
import com.example.pm.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @PostMapping("/{projectId}/create")
    public ResponseEntity<?> add(@ActiveUser Integer userId, @PathVariable Integer projectId, @Valid @RequestBody TaskDTO dto){
        return ResponseEntity.ok(taskService.addTask(projectId, userId, dto));
    }

    @GetMapping
    public ResponseEntity<?> allTasklist(@ActiveUser Integer userId, @PathVariable Integer projectId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return ResponseEntity.ok(taskService.listForUser(userId, page, size));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> list(@ActiveUser Integer userId, @PathVariable Integer projectId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return ResponseEntity.ok(taskService.listForProject(projectId,userId, page, size));
    }


    @GetMapping("/search")
    public ResponseEntity<?> search(@ActiveUser Integer userId, @RequestParam String query, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return ResponseEntity.ok(taskService.searchTasks(userId, query, page, size));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> update(@ActiveUser Integer userId,  @PathVariable Integer taskId, @Valid @RequestBody TaskDTO dto){
        return ResponseEntity.ok(taskService.updateTask(taskId, userId, dto));
    }

    @GetMapping("/{taskId}/status/{status}")
    public ResponseEntity<?> listforStatus(@ActiveUser Integer userId,  @PathVariable Integer taskId, @PathVariable String status, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return ResponseEntity.ok(taskService.listForStatus(taskId, userId, status,page,size));
    }

    @GetMapping("/{taskId}/priority/{status}")
    public ResponseEntity<?> listforPriority(@ActiveUser Integer userId,  @PathVariable Integer taskId, @PathVariable String priority, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        return ResponseEntity.ok(taskService.listForPriority(taskId, userId, priority,page,size));
    }



    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> delete(@ActiveUser Integer userId, @PathVariable Integer taskId){
        taskService.deleteTask(taskId, userId);
        return ResponseEntity.noContent().build();
    }

}
