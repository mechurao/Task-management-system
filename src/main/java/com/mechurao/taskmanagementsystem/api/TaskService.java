package com.mechurao.taskmanagementsystem.api;

import com.mechurao.taskmanagementsystem.api.request.TaskAddRequest;
import com.mechurao.taskmanagementsystem.api.request.TaskEditRequest;
import com.mechurao.taskmanagementsystem.domain.Task;
import com.mechurao.taskmanagementsystem.domain.TaskStatus;

import java.util.List;

public interface TaskService {
    long add(TaskAddRequest request);
    void edit(long id, TaskEditRequest request);
    void changeStatus(long id, TaskStatus status);
    void assignProject(long taskId, long projectId);
    void delete(long id);
    Task get(long id);
    List<Task> getAll();
    List<Task> getAllByUserId(Long userId);
    List<Task> getAllByProjectId(Long projectId);
}
