package com.mechurao.taskmanagementsystem.implementation.jdbc.repository;

import com.mechurao.taskmanagementsystem.domain.User;
import com.mechurao.taskmanagementsystem.implementation.jdbc.mapper.UserRowMapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public class UserJdbcRepository {
    private  final UserRowMapper userRowMapper;
    private static final Logger logger;
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL;
    private static final String GET_BY_ID;

    static {
        logger = LoggerFactory.getLogger(UserJdbcRepository.class);
        GET_ALL = "select * from user";
        GET_BY_ID = "select * from user where id = ?";
    }

    public UserJdbcRepository(UserRowMapper userRowMapper, JdbcTemplate jdbcTemplate) {
        this.userRowMapper = userRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getAll() {
        return jdbcTemplate.query(GET_ALL, userRowMapper);
    }

    public User getById(long id) {
        try{
            return jdbcTemplate.queryForObject(GET_BY_ID, userRowMapper, id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }catch (DataAccessException e){
            logger.error("Error while getting user by id", e);
            return null;
        }
    }
}
