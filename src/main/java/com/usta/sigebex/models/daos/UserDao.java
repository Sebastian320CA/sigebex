package com.usta.sigebex.models.daos;

import com.usta.sigebex.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface UserDao extends JpaRepository<UserEntity, Long> {


    @Transactional
    @Modifying
    @Query("UPDATE UserEntity SET userState=false WHERE userName=?1")
    void changeUserState(String userName);


    @Transactional(readOnly = true)
    @Query("SELECT u FROM UserEntity u WHERE u.email=?1")
    UserEntity findByEmail(String email);


    List<UserEntity> findAllByOrderByUserLastNameAscUserNameAsc();


    @Transactional(readOnly = true)
    @Query(value = """
    SELECT * FROM users u 
    WHERE (:name IS NULL OR LOWER(u.user_name) LIKE LOWER('%' || CAST(:name AS text) || '%'))
      AND (:lastName IS NULL OR LOWER(u.user_last_name) LIKE LOWER('%' || CAST(:lastName AS text) || '%'))
    ORDER BY u.user_last_name ASC, u.user_name ASC
""", nativeQuery = true)
    List<UserEntity> search(
            @Param("name") String name,
            @Param("lastName") String lastName
    );
}
