package com.medicine.manager;

import com.medicine.manager.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MedicineBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineBackendApplication.class, args);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
    }
    @Bean
    public IdWorker idWorker() {
        //全局id
        return new IdWorker();
    }
}

