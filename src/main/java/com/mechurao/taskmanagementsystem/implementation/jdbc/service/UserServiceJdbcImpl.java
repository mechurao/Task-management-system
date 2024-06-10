package com.mechurao.taskmanagementsystem.implementation.jdbc.service;

import com.mechurao.taskmanagementsystem.api.UserService;
import com.mechurao.taskmanagementsystem.api.request.UserAddRequest;
import com.mechurao.taskmanagementsystem.domain.User;
import com.mechurao.taskmanagementsystem.implementation.jdbc.repository.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceJdbcImpl implements UserService {

    private final UserJdbcRepository userJdbcRepository;

    public UserServiceJdbcImpl(UserJdbcRepository userJdbcRepository) {
        this.userJdbcRepository = userJdbcRepository;
    }

    @Override
    public long add(UserAddRequest request) {
        return 0;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return userJdbcRepository.getAll();
    }
}
