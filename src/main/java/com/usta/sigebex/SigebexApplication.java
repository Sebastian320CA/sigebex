package com.usta.sigebex;

import com.usta.sigebex.models.daos.UserDao;
import com.usta.sigebex.models.daos.RolDao;
import com.usta.sigebex.entities.UserEntity;
import com.usta.sigebex.entities.RolEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SigebexApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigebexApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(UserDao userRepository, RolDao rolRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            crearRolesSiNoExisten(rolRepository);


            crearAdminSiNoExiste(userRepository, rolRepository, passwordEncoder);
        };
    }

    private void crearRolesSiNoExisten(RolDao rolRepository) {
        if (rolRepository.findByName("ROLE_ADMIN") == null) {
            RolEntity roleAdmin = new RolEntity();
            roleAdmin.setNameRol("ROLE_ADMIN");
            rolRepository.save(roleAdmin);
            System.out.println("✅ ROL CREADO: ROLE_ADMIN");
        } else {
            System.out.println("✅ Rol ROLE_ADMIN ya existe");
        }


        if (rolRepository.findByName("ROLE_BIOMEDICO") == null) {
            RolEntity roleBiomedico = new RolEntity();
            roleBiomedico.setNameRol("ROLE_BIOMEDICO");
            rolRepository.save(roleBiomedico);
            System.out.println("✅ ROL CREADO: ROLE_BIOMEDICO");
        } else {
            System.out.println("✅ Rol ROLE_BIOMEDICO ya existe");
        }


        if (rolRepository.findByName("ROLE_SEGURIDAD") == null) {
            RolEntity roleSeguridad = new RolEntity();
            roleSeguridad.setNameRol("ROLE_SEGURIDAD");
            rolRepository.save(roleSeguridad);
            System.out.println("✅ ROL CREADO: ROLE_SEGURIDAD");
        } else {
            System.out.println("✅ Rol ROLE_SEGURIDAD ya existe");
        }
    }

    private void crearAdminSiNoExiste(UserDao userRepository, RolDao rolRepository, PasswordEncoder passwordEncoder) {

        if (userRepository.findByEmail("admin@sigebex.com") == null) {
            UserEntity admin = new UserEntity();
            admin.setUserName("admin");
            admin.setUserLastName("Administrador");
            admin.setEmail("admin@sigebex.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setUserState(true);
            RolEntity adminRole = rolRepository.findByName("ROLE_ADMIN");
            if (adminRole != null) {
                admin.setRol(adminRole);
            }

            userRepository.save(admin);
            System.out.println("✅ USUARIO ADMIN CREADO: admin / admin123");
        } else {
            System.out.println("✅ Usuario admin ya existe");
        }
    }
}