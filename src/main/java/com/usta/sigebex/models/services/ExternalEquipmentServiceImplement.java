package com.usta.sigebex.models.services;

import com.usta.sigebex.entities.ExternalEquipmentEntity;
import com.usta.sigebex.models.daos.ExternalEquipmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExternalEquipmentServiceImplement implements  ExternalEquipmentService{
    @Autowired
    private ExternalEquipmentDao externalEquipmentDao;

    @Transactional
    @Override
    public List<ExternalEquipmentEntity> findAll() {
        return externalEquipmentDao.findAll();
    }

    @Transactional
    @Override
    public void save(ExternalEquipmentEntity equipment) {
    externalEquipmentDao.save(equipment);
    }

    @Transactional
    @Override
    public ExternalEquipmentEntity findById(Long id) {
        return externalEquipmentDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
    externalEquipmentDao.desactivateEquipment(id);
    }

    @Transactional
    @Override
    public ExternalEquipmentEntity updateEquipment(ExternalEquipmentEntity equipment) {
        return externalEquipmentDao.save(equipment);
    }

    @Transactional
    @Override
    public void changeStateEquipment(Long id) {
        ExternalEquipmentEntity equipment = findById(id);
        if (equipment != null) {
            equipment.setActive(!equipment.isActive());

            externalEquipmentDao.save(equipment);
        }
    }

    @Override
    public List<ExternalEquipmentEntity> search(String type, String brand) {
        return externalEquipmentDao.search(type, brand);
    }

    @Transactional
    @Override
    public List<ExternalEquipmentEntity> findByThirdPartyCompany(String companyName) {
        return externalEquipmentDao.findByThirdPartyCompany(companyName);
    }
}
