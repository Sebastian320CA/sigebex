package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.RolEntity;
import com.usta.sigebex.models.daos.RolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImplement implements  RolService{
    @Autowired
    private RolDao rolDao;

    @Transactional
    @Override
    public List<RolEntity> findAll() {
       return (List<RolEntity>) rolDao.findAll();
    }

    @Transactional
    @Override
    public void save(RolEntity rol) {
    rolDao.save(rol);
    }

    @Transactional
    @Override
    public RolEntity findById(Long id) {
        return rolDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
    rolDao.deleteById(id);
    }

    @Transactional
    @Override
    public RolEntity updateRol(RolEntity rol) {
        return rolDao.save(rol);
    }
}
