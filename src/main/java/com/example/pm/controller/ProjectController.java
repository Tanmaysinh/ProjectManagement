package com.example.pm.controller;

import com.example.pm.config.ActiveUser;
import com.example.pm.dto.ProjectDTO;
import com.example.pm.entity.Project;
import com.example.pm.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@ActiveUser Integer userId, @Valid @RequestBody ProjectDTO dto){
        return ResponseEntity.ok(projectService.createProject(userId, dto));
    }

    @GetMapping
    public ResponseEntity<?> list(@ActiveUser Integer userId){
        return ResponseEntity.ok(projectService.listForUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@ActiveUser Integer userId, @PathVariable Integer id){
        return ResponseEntity.ok(projectService.getByIdForUser(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@ActiveUser Integer userId, @PathVariable Integer id, @Valid @RequestBody ProjectDTO dto){
          return ResponseEntity.ok(projectService.updateProject(id, userId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@ActiveUser Integer userId, @PathVariable Integer id){
        projectService.deleteProject(id, userId);
        return ResponseEntity.noContent().build();
    }
}
