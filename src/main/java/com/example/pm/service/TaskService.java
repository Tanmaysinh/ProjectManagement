package com.example.pm.service;

import com.example.pm.dto.TaskDTO;
import com.example.pm.entity.Task;
import com.example.pm.entity.TaskPriority;
import com.example.pm.entity.TaskStatus;
import com.example.pm.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, ProjectService projectService, ModelMapper modelMapper){
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.modelMapper = modelMapper;
    }

    public TaskDTO addTask(Integer projectId, Integer userId, TaskDTO dto){
        projectService.getByIdForUser(projectId,userId);
        return getDTOFromEntity(taskRepository.save(getEntityFromDTO(dto)));
    }

    public List<TaskDTO> listForProject(Integer projectId, Integer userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> p = taskRepository.findByProjectIdAndProjectUserId(projectId,userId, pageable);
        return p.stream().map(this::getDTOFromEntity).toList();
    }

    public List<TaskDTO> listForStatus(Integer projectId, Integer userId,String status, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> p = taskRepository.findByProjectIdAndProjectUserIdAndStatus(projectId,userId, TaskStatus.valueOf(status), pageable);
        return p.stream().map(this::getDTOFromEntity).toList();
    }

    public List<TaskDTO> listForPriority(Integer projectId, Integer userId,String priority, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> p = taskRepository.findByProjectIdAndProjectUserIdAndPriority(projectId,userId, TaskPriority.valueOf(priority), pageable);
        return p.stream().map(this::getDTOFromEntity).toList();
    }

    public List<TaskDTO> listForUser(Integer userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> p = taskRepository.findByProjectUserId(userId, pageable);
        return p.stream().map(this::getDTOFromEntity).toList();
    }

    public List<TaskDTO> searchTasks(Integer userId, String q, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> p = taskRepository.searchForUser(userId, q, pageable);
        return p.stream().map(this::getDTOFromEntity).toList();
    }

    public TaskDTO updateTask(Integer taskId, Integer userId, TaskDTO dto){
        Task t = taskRepository.findByIdAndProjectUserId(taskId,userId);
        if(t==null) throw new RuntimeException("Task Not Found");
        return getDTOFromEntity(taskRepository.save(getEntityFromDTO(dto)));
    }


    public void deleteTask(Integer taskId, Integer userId){
        Task t = taskRepository.findByIdAndProjectUserId(taskId,userId);
        if(t==null) throw new RuntimeException("Task Not Found");
        taskRepository.delete(t);
    }

    public TaskDTO getDTOFromEntity(Task task){
        return modelMapper.map(task,TaskDTO.class);
    }
    public Task getEntityFromDTO(TaskDTO taskDTO){
        return modelMapper.map(taskDTO,Task.class);
    }
}
