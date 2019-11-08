package com.medicine.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MedicineBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineBackendApplication.class, args);
    }
}

