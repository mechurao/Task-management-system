package com.mechurao.taskmanagementsystem.controller;

import com.mechurao.taskmanagementsystem.api.TaskService;
import com.mechurao.taskmanagementsystem.api.request.TaskAddRequest;
import com.mechurao.taskmanagementsystem.api.request.TaskAssignStatusRequest;
import com.mechurao.taskmanagementsystem.api.request.TaskChangeStatusRequest;
import com.mechurao.taskmanagementsystem.api.request.TaskEditRequest;
import com.mechurao.taskmanagementsystem.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long projectId
    ) {
        if (userId != null) {
            return ResponseEntity.ok().body(service.getAllByUserId(userId));
        }else if(projectId != null){
            return ResponseEntity.ok().body(service.getAllByProjectId(projectId));
        }
        return ResponseEntity.ok().body(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody TaskAddRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> edit(@PathVariable("id") long id, @RequestBody TaskEditRequest request){
        service.edit(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") long id, @RequestBody TaskChangeStatusRequest request){
        service.changeStatus(id, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}/assign")
    public ResponseEntity<Void> assignProject(@PathVariable("id") long id, @RequestBody TaskAssignStatusRequest request){
        service.assignProject(id, request.getProjectId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> get(@PathVariable("id") long id){
        return ResponseEntity.ok().body(service.get(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
