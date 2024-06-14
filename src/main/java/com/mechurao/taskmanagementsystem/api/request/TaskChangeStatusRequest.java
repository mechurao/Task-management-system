package com.mechurao.taskmanagementsystem.api.request;

import com.mechurao.taskmanagementsystem.domain.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskChangeStatusRequest {
    private TaskStatus status;
}
