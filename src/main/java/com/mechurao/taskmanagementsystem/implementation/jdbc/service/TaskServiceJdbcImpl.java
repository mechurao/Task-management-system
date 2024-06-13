package com.mechurao.taskmanagementsystem.implementation.jdbc.service;

import com.mechurao.taskmanagementsystem.api.TaskService;
import com.mechurao.taskmanagementsystem.api.request.TaskAddRequest;
import com.mechurao.taskmanagementsystem.api.request.TaskEditRequest;
import com.mechurao.taskmanagementsystem.domain.Task;
import com.mechurao.taskmanagementsystem.domain.TaskStatus;

import java.util.List;

public class TaskServiceJdbcImpl implements TaskService {
    @Override
    public long add(TaskAddRequest request) {
        return 0;
    }

    @Override
    public void edit(long id, TaskEditRequest request) {

    }

    @Override
    public void changeStatus(long id, TaskStatus status) {

    }

    @Override
    public void assignProject(long taskId, long projectId) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Task get(long id) {
        return null;
    }

    @Override
    public List<Task> getAll() {
        return List.of();
    }

    @Override
    public List<Task> getAllByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<Task> getAllByProjectId(Long projectId) {
        return List.of();
    }
}
