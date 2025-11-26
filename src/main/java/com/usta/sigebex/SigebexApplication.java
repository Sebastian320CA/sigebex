package com.usta.sigebex;

import com.usta.sigebex.models.daos.UserDao;
import com.usta.sigebex.entities.UserEntity;
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
    CommandLineRunner initData(UserDao userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificar si ya existe el usuario admin
            if (userRepository.findByEmail("admin@sigebex.com") == null) {
                UserEntity admin = new UserEntity();
                admin.setUserName("admin");
                admin.setUserLastName("Administrador");
                admin.setEmail("admin@sigebex.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setUserState(true);

                userRepository.save(admin);
                System.out.println("✅ USUARIO ADMIN CREADO: admin / admin123");
            } else {
                System.out.println("✅ Usuario admin ya existe");
            }
        };
    }
}