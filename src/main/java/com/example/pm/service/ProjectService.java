package com.example.pm.service;

import com.example.pm.dto.ProjectDTO;
import com.example.pm.entity.Project;
import com.example.pm.entity.User;
import com.example.pm.repository.ProjectRepository;
import com.example.pm.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ModelMapper modelMapper){
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public ProjectDTO createProject(Integer userId, ProjectDTO dto){
        User user = userRepository.findById(userId).orElseThrow();
        Project p = new Project();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setUser(user);
        return getDTOFromEntity(projectRepository.save(p));
    }

    public List<ProjectDTO> listForUser(Integer userId){
        List<Project> projectList=projectRepository.findByUserId(userId);
        return projectList.stream()
                .map(this::getDTOFromEntity)
                .toList();

    }

    public ProjectDTO getByIdForUser(Integer id, Integer userId){
        Project project=projectRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new RuntimeException("No Project exist"));
        return getDTOFromEntity(project);
    }

    public ProjectDTO updateProject(Integer id, Integer userId, ProjectDTO dto){
        getByIdForUser(id, userId);
        return getDTOFromEntity(projectRepository.save(getEntityFromDTO(dto)));
    }

    public void deleteProject(Integer id, Integer username){
        ProjectDTO projectDTO = getByIdForUser(id, username);
        projectRepository.deleteById(projectDTO.getId());
    }

    public ProjectDTO getDTOFromEntity(Project project){
        return modelMapper.map(project,ProjectDTO.class);
    }
    public Project getEntityFromDTO(ProjectDTO projectDTO){
        return modelMapper.map(projectDTO,Project.class);
    }
}
