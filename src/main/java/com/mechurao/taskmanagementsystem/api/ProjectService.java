package com.mechurao.taskmanagementsystem.api;

import com.mechurao.taskmanagementsystem.api.request.ProjectAddRequest;
import com.mechurao.taskmanagementsystem.api.request.ProjectEditRequest;
import com.mechurao.taskmanagementsystem.domain.Project;

import java.util.List;

public interface ProjectService {
    ProjectService get(long id);
    List<Project> getAll();
    List<Project> getAllByUser(long userId);
    void delete(long id);
    long add(ProjectAddRequest project);
    void edit(long id, ProjectEditRequest project);
}
