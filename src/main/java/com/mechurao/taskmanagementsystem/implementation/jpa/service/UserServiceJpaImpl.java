package com.mechurao.taskmanagementsystem.implementation.jpa.service;

import com.mechurao.taskmanagementsystem.api.UserService;
import com.mechurao.taskmanagementsystem.api.exception.BadRequestException;
import com.mechurao.taskmanagementsystem.api.exception.InternalErrorException;
import com.mechurao.taskmanagementsystem.api.exception.ResourceNotFoundException;
import com.mechurao.taskmanagementsystem.api.request.UserAddRequest;
import com.mechurao.taskmanagementsystem.domain.User;
import com.mechurao.taskmanagementsystem.implementation.jpa.entity.UserEntity;
import com.mechurao.taskmanagementsystem.implementation.jpa.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class UserServiceJpaImpl implements UserService {
    private final UserJpaRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceJpaImpl.class);

    public UserServiceJpaImpl(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public long add(UserAddRequest request) {
        try{
            return repository.save(new UserEntity(request.getName(), request.getEmail())).getId();
        }catch (DataIntegrityViolationException e){
            throw  new BadRequestException("User with this email already exists");
        }catch (DataAccessException e){
            logger.error("Error while adding user", e);
            throw new InternalErrorException("User with this email already exists");
        }
    }

    @Override
    public void delete(long id) {
        if(this.get(id) != null){
            repository.deleteById(id);
        }
    }

    @Override
    public User get(long id) {
        return repository.findById(id)
                .map( this::mapUserEntityToUser)
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll().stream().map(this::mapUserEntityToUser).toList();
    }

    private User mapUserEntityToUser(UserEntity entity) {
        return new User(entity.getId(), entity.getName(), entity.getEmail());
    }
}
