package com.mechurao.taskmanagementsystem.implementation.jdbc.service;

import com.mechurao.taskmanagementsystem.api.ProjectService;
import com.mechurao.taskmanagementsystem.api.TaskService;
import com.mechurao.taskmanagementsystem.api.UserService;
import com.mechurao.taskmanagementsystem.api.exception.BadRequestException;
import com.mechurao.taskmanagementsystem.api.request.TaskAddRequest;
import com.mechurao.taskmanagementsystem.api.request.TaskEditRequest;
import com.mechurao.taskmanagementsystem.domain.Project;
import com.mechurao.taskmanagementsystem.domain.Task;
import com.mechurao.taskmanagementsystem.domain.TaskStatus;
import com.mechurao.taskmanagementsystem.implementation.jdbc.repository.TaskJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceJdbcImpl implements TaskService {

    private final TaskJdbcRepository repository;
    private final UserService userService;
    private final ProjectService projectService;

    public TaskServiceJdbcImpl(TaskJdbcRepository repository, UserService userService, ProjectService projectService) {
        this.repository = repository;
        this.userService = userService;
        this.projectService = projectService;
    }

    @Override
    public long add(TaskAddRequest request) {
        return repository.add(request);
    }

    @Override
    public void edit(long taskId, TaskEditRequest request) {
        if(this.get(taskId) != null){
            repository.update(taskId, request);
        }
    }

    @Override
    public void changeStatus(long taskId, TaskStatus status) {
        if(this.get(taskId) != null){
            repository.updateStatus(taskId, status);
        }
    }

    @Override
    public void assignProject(long taskId, long projectId) {
        final Task task = this.get(taskId);
        final Project project = projectService.get(projectId);
        if(task != null && project != null){
            if(task.getUserId() != project.getUserId()){
                throw  new BadRequestException("Task and project must be owned by same user");
            }
            repository.updateProject(taskId, projectId);
        }
    }

    @Override
    public void delete(long taskId) {
        if(this.get(taskId) != null){
            repository.delete(taskId);
        }
    }

    @Override
    public Task get(long taskId) {
        return repository.getById(String.valueOf(taskId));
    }

    @Override
    public List<Task> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        if(userService.get(userId) != null){
            return repository.getAllByUser(userId);
        }
        return null;
    }

    @Override
    public List<Task> getAllByProjectId(Long projectId) {
        if(projectService.get(projectId) != null){
            return repository.getAllByProject(projectId);
        }
        return null;
    }
}
