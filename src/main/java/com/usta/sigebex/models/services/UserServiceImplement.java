package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.UserEntity;
import com.usta.sigebex.models.daos.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;


@Service
public class UserServiceImplement implements  UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public List<UserEntity> findAll() {
        return (List<UserEntity>) userDao.findAll();
    }

    @Transactional
    @Override
    public void save(UserEntity user) {

        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userDao.save(user);
    }


    @Transactional
    @Override
    public UserEntity findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
    userDao.deleteById(id);
    }

    @Transactional
    @Override
    public UserEntity updateUser(UserEntity user) {
        return userDao.save(user);
    }

    @Transactional
    @Override
    public void changeStateUser(UserEntity user) {
    userDao.save(user);
    }

    @Override
    public List<UserEntity> listFiltered(String userName, String userLastName) {
        String namePattern = normalize(userName);
        String lastNamePattern = normalize(userLastName);

        String name = (namePattern == null) ? null : "%" + namePattern.toLowerCase(Locale.ROOT) + "%";
        String lastName = (lastNamePattern == null) ? null : "%" + lastNamePattern.toLowerCase(Locale.ROOT) + "%";

        if (name == null && lastName == null) {
            return userDao.findAllByOrderByUserLastNameAscUserNameAsc();
        }

        return userDao.search(name, lastName);
    }

    private String normalize(String s) {
        if (s == null) return null;
        String trimmed = s.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
