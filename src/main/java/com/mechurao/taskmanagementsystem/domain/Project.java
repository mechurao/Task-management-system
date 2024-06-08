package com.mechurao.taskmanagementsystem.domain;

import lombok.Value;

@Value
public class Project {
    long id;
    long userId;
    String name;
    String description;
}
