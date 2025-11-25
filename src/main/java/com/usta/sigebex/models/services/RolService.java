package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.RolEntity;

import java.util.List;

public interface RolService {
    public List<RolEntity> findAll();
    public void save(RolEntity rol);
    public RolEntity findById(Long id);
    public void deleteById(Long id);
    public RolEntity updateRol(RolEntity rol);

}
