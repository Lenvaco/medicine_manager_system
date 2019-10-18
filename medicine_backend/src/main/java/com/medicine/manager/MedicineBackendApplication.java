package com.medicine.manager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class MedicineBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineBackendApplication.class, args);
    }
}

