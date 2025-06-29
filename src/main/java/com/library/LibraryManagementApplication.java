package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Library Management System - Ana Uygulama Sınıfı
 * 
 * Bu sınıf Spring Boot uygulamasının giriş noktasıdır.
 * @EnableAsync annotation'ı ile asenkron işlemler aktif edilmiştir.
 */
@SpringBootApplication
@EnableAsync
public class LibraryManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
        System.out.println("=== Kütüphane Yönetim Sistemi Başlatıldı ===");
        System.out.println("Uygulama URL: http://localhost:8080");
        System.out.println("H2 Console: http://localhost:8080/h2-console");
    }
} 