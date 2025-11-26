package com.usta.sigebex.models.daos;

import com.usta.sigebex.entities.RolEntity;
import org.springframework.data.repository.CrudRepository;

public interface RolDao  extends CrudRepository<RolEntity, Long> {
    RolEntity findByNameRol(String nameRol);
}
