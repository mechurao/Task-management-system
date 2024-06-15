package com.mechurao.taskmanagementsystem.implementation.jdbc.service;

import com.mechurao.taskmanagementsystem.api.ProjectService;
import com.mechurao.taskmanagementsystem.api.request.ProjectAddRequest;
import com.mechurao.taskmanagementsystem.api.request.ProjectEditRequest;
import com.mechurao.taskmanagementsystem.domain.Project;
import com.mechurao.taskmanagementsystem.implementation.jdbc.repository.ProjectJdbcRepository;
import com.mechurao.taskmanagementsystem.implementation.jdbc.repository.TaskJdbcRepository;
import com.mechurao.taskmanagementsystem.implementation.jdbc.repository.UserJdbcRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("jdbc")
public class ProjectServiceJdbcImpl implements ProjectService {

    private final ProjectJdbcRepository repository;
    private final UserJdbcRepository userJdbcRepository;
    private final TaskJdbcRepository taskJdbcRepository;

    public ProjectServiceJdbcImpl(ProjectJdbcRepository repository, UserJdbcRepository userJdbcRepository, TaskJdbcRepository taskJdbcRepository) {
        this.repository = repository;
        this.userJdbcRepository = userJdbcRepository;
        this.taskJdbcRepository = taskJdbcRepository;
    }

    @Override
    public long add(ProjectAddRequest request) {
        return repository.add(request);
    }

    @Override
    public void edit(long id, ProjectEditRequest request) {
        if(this.get(id) != null){
            repository.update(request);
        }
    }

    @Override
    public void delete(long id) {
        if(this.get(id) != null){
            taskJdbcRepository.deleteAllByProject(id);
            repository.delete(id);
        }
    }

    @Override
    public Project get(long id) {
        return repository.getById(id);
    }

    @Override
    public List<Project> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Project> getAllByUser(long userId) {
        if(userJdbcRepository.getById(userId) != null){
            return repository.getAllByUserId(userId);
        }
        return null;
    }

}
