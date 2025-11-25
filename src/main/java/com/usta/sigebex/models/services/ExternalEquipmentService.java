package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.ExternalEquipmentEntity;

import java.util.List;

public interface ExternalEquipmentService {
    public List<ExternalEquipmentEntity> findAll();

    public void save(ExternalEquipmentEntity equipment);

    public ExternalEquipmentEntity findById(Long id);

    public void deleteById(Long id);

    public ExternalEquipmentEntity updateEquipment(ExternalEquipmentEntity equipment);

    public void changeStateEquipment(Long id);

    List<ExternalEquipmentEntity> search(String type, String brand);

    public List<ExternalEquipmentEntity> findByThirdPartyCompany(String companyName);
}
