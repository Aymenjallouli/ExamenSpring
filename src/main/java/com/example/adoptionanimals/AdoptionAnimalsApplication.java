package com.example.adoptionanimals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe principale de l'application Spring Boot pour la gestion des demandes d'adoption d'animaux
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class AdoptionAnimalsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdoptionAnimalsApplication.class, args);
    }
}
