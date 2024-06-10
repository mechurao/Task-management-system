package com.mechurao.taskmanagementsystem.implementation.jdbc.service;

import com.mechurao.taskmanagementsystem.api.UserService;
import com.mechurao.taskmanagementsystem.api.request.UserAddRequest;
import com.mechurao.taskmanagementsystem.domain.User;
import com.mechurao.taskmanagementsystem.implementation.jdbc.repository.UserJdbcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceJdbcImpl implements UserService {

    private final UserJdbcRepository repository;

    public UserServiceJdbcImpl(UserJdbcRepository repository) {
        this.repository = repository;
    }

    @Override
    public long add(UserAddRequest request) {
        return repository.add(request);
    }

    @Override
    public void delete(long id) {
        if(this.get(id) != null){
            repository.delete(id);
        }
    }

    @Override
    public User get(long id) {
        return repository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }
}
