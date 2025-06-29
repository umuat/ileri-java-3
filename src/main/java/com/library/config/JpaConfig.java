package com.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JPA Konfigürasyon Sınıfı
 * 
 * Bu sınıf JPA Auditing ve repository konfigürasyonlarını sağlar.
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.library.repository")
public class JpaConfig {
    // JPA konfigürasyonları burada tanımlanabilir
} 