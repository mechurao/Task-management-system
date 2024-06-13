package com.mechurao.taskmanagementsystem.implementation.jdbc.service;

import com.mechurao.taskmanagementsystem.api.ProjectService;
import com.mechurao.taskmanagementsystem.api.request.ProjectAddRequest;
import com.mechurao.taskmanagementsystem.api.request.ProjectEditRequest;
import com.mechurao.taskmanagementsystem.domain.Project;

import java.util.List;

public class ProjectServiceJdbcImpl implements ProjectService {
    @Override
    public ProjectService get(long id) {
        return null;
    }

    @Override
    public List<Project> getAll() {
        return List.of();
    }

    @Override
    public List<Project> getAllByUser(long userId) {
        return List.of();
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public long add(ProjectAddRequest project) {
        return 0;
    }

    @Override
    public void edit(long id, ProjectEditRequest project) {

    }
}
