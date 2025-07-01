package com.example.adoptionanimals.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 5. Aspect logging (1.5 pts)
 * Créer un @Aspect qui affiche le message "Bienvenue dans le service ajouterUtilisateur"
 * avant l'exécution de ajouterUtilisateur.
 */
@Aspect
@Component
public class LoggingAspect {
    
    /**
     * Aspect qui s'exécute avant la méthode ajouterUtilisateur
     */
    @Before("execution(* com.example.adoptionanimals.services.UtilisateurService.ajouterUtilisateur(..))")
    public void logBeforeAjouterUtilisateur() {
        System.out.println("Bienvenue dans le service ajouterUtilisateur");
    }
}
