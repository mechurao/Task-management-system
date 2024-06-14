package com.mechurao.taskmanagementsystem.implementation.jpa.service;

import com.mechurao.taskmanagementsystem.api.ProjectService;
import com.mechurao.taskmanagementsystem.api.UserService;
import com.mechurao.taskmanagementsystem.api.exception.InternalErrorException;
import com.mechurao.taskmanagementsystem.api.exception.ResourceNotFoundException;
import com.mechurao.taskmanagementsystem.api.request.ProjectAddRequest;
import com.mechurao.taskmanagementsystem.api.request.ProjectEditRequest;
import com.mechurao.taskmanagementsystem.domain.Project;
import com.mechurao.taskmanagementsystem.domain.User;
import com.mechurao.taskmanagementsystem.implementation.jpa.entity.ProjectEntity;
import com.mechurao.taskmanagementsystem.implementation.jpa.entity.UserEntity;
import com.mechurao.taskmanagementsystem.implementation.jpa.repository.ProjectJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@Primary
public class ProjectServiceJpaImpl implements ProjectService {
    private final ProjectJpaRepository repository;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceJpaImpl.class);


    public ProjectServiceJpaImpl(ProjectJpaRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Project get(long id) {
        return repository
                .findById(id)
                .map(this::mapProjectEntityToProject)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
    }

    @Override
    public List<Project> getAll() {
        return repository.findAll().stream().map(this::mapProjectEntityToProject).toList();
    }

    @Override
    public List<Project> getAllByUser(long userId) {
        if(userService.get(userId) != null) {
            return repository.findAllByUserId(userId).stream().map(this::mapProjectEntityToProject).toList();
        }
        return null;
    }

    @Override
    public void delete(long id) {
        if(this.get(id) != null) {
            repository.deleteById(id);
        }
    }

    @Override
    public long add(ProjectAddRequest request) {
        final User user = userService.get(request.getUserId());
        final UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getEmail());
        try{
            return repository.save(new ProjectEntity(userEntity, request.getName(), request.getDescription(), OffsetDateTime.now())).getId();
        }catch (DataAccessException e){
            logger.error("Error while adding project", e);
            throw new InternalErrorException("Project not found");
        }
    }

    @Override
    public void edit(long id, ProjectEditRequest request) {
        final ProjectEntity projectEntity = repository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        projectEntity.setName(request.getName());
        projectEntity.setDescription(request.getDescription());
        repository.save(projectEntity);
    }

    private Project mapProjectEntityToProject(ProjectEntity entity) {
        return new Project(
                entity.getId(),
                entity.getUser().getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt()
        );
    }
}
