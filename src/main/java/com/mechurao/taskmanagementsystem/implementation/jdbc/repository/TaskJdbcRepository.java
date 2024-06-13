package com.mechurao.taskmanagementsystem.implementation.jdbc.repository;

import com.mechurao.taskmanagementsystem.api.exception.InternalErrorException;
import com.mechurao.taskmanagementsystem.api.exception.ResourceNotFoundException;
import com.mechurao.taskmanagementsystem.domain.Task;
import com.mechurao.taskmanagementsystem.implementation.jdbc.mapper.TaskRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TaskRowMapper taskRowMapper;

    public TaskJdbcRepository(JdbcTemplate jdbcTemplate, TaskRowMapper taskRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskRowMapper = taskRowMapper;
    }

    private static final Logger logger;
    private static final String GET_ALL;
    private static final String GET_BY_ID;
    private static final String GET_ALL_BY_USER;
    private static final String GET_ALL_BY_PROJECT;

    static{
        logger = LoggerFactory.getLogger(TaskJdbcRepository.class);
        GET_ALL = "SELECT * FROM task";
        GET_BY_ID = "SELECT * FROM task WHERE id = ?";
        GET_ALL_BY_USER = "SELECT * FROM task WHERE user_id = ?";
        GET_ALL_BY_PROJECT = "SELECT * FROM task WHERE project_id = ?";
    }

    public List<Task> getAll(){
        try{
            return jdbcTemplate.query(GET_ALL, taskRowMapper);
        }catch(DataAccessException e ){
            logger.error("Error while getting all tasks", e);
            throw new InternalErrorException("Error while getting all tasks");
        }
    }

    public Task getById(String id){
        try{
            return jdbcTemplate.queryForObject(GET_BY_ID, taskRowMapper, id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Task with id " + id + " not found");
        }catch (DataAccessException e){
            logger.error("Error while getting task by id", e);
            throw new InternalErrorException("Error while getting task by id");
        }
    }

    public List<Task> getAllByUser(long userId){
        try{
            return jdbcTemplate.query(GET_ALL_BY_USER, taskRowMapper, userId);
        }catch (DataAccessException e){
            logger.error("Error while getting all tasks by user ID", e);
            throw new InternalErrorException("Error while getting all tasks by user ID");
        }
    }

    public List<Task> getAllByProject(long projectId){
        try{
            return jdbcTemplate.query(GET_ALL_BY_PROJECT, taskRowMapper, projectId);
        }catch (DataAccessException e){
            logger.error("Error while getting all tasks by project ID", e);
            throw new InternalErrorException("Error while getting all tasks by project ID");
        }
    }






}
