package com.mechurao.taskmanagementsystem.implementation.jpa.service;

import com.mechurao.taskmanagementsystem.api.ProjectService;
import com.mechurao.taskmanagementsystem.api.TaskService;
import com.mechurao.taskmanagementsystem.api.UserService;
import com.mechurao.taskmanagementsystem.api.exception.BadRequestException;
import com.mechurao.taskmanagementsystem.api.exception.InternalErrorException;
import com.mechurao.taskmanagementsystem.api.exception.ResourceNotFoundException;
import com.mechurao.taskmanagementsystem.api.request.TaskAddRequest;
import com.mechurao.taskmanagementsystem.api.request.TaskEditRequest;
import com.mechurao.taskmanagementsystem.domain.Project;
import com.mechurao.taskmanagementsystem.domain.Task;
import com.mechurao.taskmanagementsystem.domain.TaskStatus;
import com.mechurao.taskmanagementsystem.domain.User;
import com.mechurao.taskmanagementsystem.implementation.jpa.entity.ProjectEntity;
import com.mechurao.taskmanagementsystem.implementation.jpa.entity.TaskEntity;
import com.mechurao.taskmanagementsystem.implementation.jpa.entity.UserEntity;
import com.mechurao.taskmanagementsystem.implementation.jpa.repository.TaskJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Primary
public class TaskServiceJpaImpl implements TaskService {

    private final TaskJpaRepository repository;
    private final ProjectService projectService;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceJpaImpl.class);

    public TaskServiceJpaImpl(TaskJpaRepository repository, ProjectService projectService, UserService userService) {
        this.repository = repository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public long add(TaskAddRequest request) {
        final User user = userService.get(request.getUserId());
        final UserEntity userEntity = new UserEntity(user.getId(), user.getName(), user.getEmail());

        final ProjectEntity projectEntity;
        if(request.getProjectId() != null){
            final Project project = projectService.get(request.getProjectId());
            projectEntity = new ProjectEntity(project.getId(), userEntity, project.getName(), project.getDescription(), project.getCreatedAt());
        }else{
            projectEntity = null;
        }
        try{
            return  repository.save(new TaskEntity(userEntity, projectEntity, request.getName(), request.getDescription(), TaskStatus.NEW, OffsetDateTime.now())).getId();
        }catch (DataAccessException e){
            logger.error("Error while saving task", e);
            throw new InternalErrorException("Error while saving task");
        }
    }

    @Override
    public void edit(long id, TaskEditRequest request) {
        final TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskEntity.setName(request.getName());
        taskEntity.setDescription(request.getDescription());
        taskEntity.setStatus(request.getStatus());
        repository.save(taskEntity);
    }

    @Override
    public void changeStatus(long id, TaskStatus status) {
        final TaskEntity taskEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskEntity.setStatus(status);
        repository.save(taskEntity);
    }

    @Override
    public void assignProject(long taskId, long projectId) {
        final TaskEntity taskEntity = repository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        final Project project = projectService.get(projectId);

        if (!Objects.equals(project.getUserId(), taskEntity.getUser().getId())) {
            throw new BadRequestException("Task and project must have same owner");
        }

        final ProjectEntity projectEntity = new ProjectEntity(project.getId(), taskEntity.getUser(), project.getName(), project.getDescription(), OffsetDateTime.now());

        taskEntity.setProject(projectEntity);
        repository.save(taskEntity);
    }

    @Override
    public void delete(long id) {
        if(this.get(id) != null){
            repository.deleteById(id);
        }
    }

    @Override
    public Task get(long id) {
        return repository.findById(id)
                .map(this::mapTaskEntityToTask)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
    }

    @Override
    public List<Task> getAll() {
        return repository.findAll().stream().map(this::mapTaskEntityToTask).toList();
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        if(userService.get(userId) != null){
            return repository.findAllByUserId(userId).stream().map(this::mapTaskEntityToTask).toList();
        }
        return null;
    }

    @Override
    public List<Task> getAllByProjectId(Long projectId) {
        if(projectService.get(projectId) != null){
            return repository.findAllByProjectId(projectId).stream().map(this::mapTaskEntityToTask).toList();
        }
        return null;
    }

    private Task mapTaskEntityToTask(TaskEntity taskEntity) {
        return new Task(
                taskEntity.getId(),
                taskEntity.getUser().getId(),
                taskEntity.getProject() != null ? taskEntity.getProject().getId() : null,
                taskEntity.getName(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getCreatedAt()
        );
    }
}
