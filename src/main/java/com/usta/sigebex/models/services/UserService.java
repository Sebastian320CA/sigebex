package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.RolEntity;
import com.usta.sigebex.entities.UserEntity;

import java.util.List;

public interface UserService {
    public List<UserEntity> findAll();
    public void save(UserEntity user);
    public UserEntity findById(Long id);
    public void deleteById(Long id);
    public UserEntity updateUser(UserEntity user);
    public void changeStateUser(UserEntity user);
    List<UserEntity> listFiltered(String user_last_Name, String userName);
}
