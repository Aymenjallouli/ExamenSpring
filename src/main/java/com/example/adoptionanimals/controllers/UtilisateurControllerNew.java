package com.example.adoptionanimals.controllers;

import com.example.adoptionanimals.entities.Utilisateur;
import com.example.adoptionanimals.services.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des utilisateurs
 */
@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "*")
public class UtilisateurControllerNew {
    
    @Autowired
    private UtilisateurService utilisateurService;
    
    /**
     * 1. Ajouter un utilisateur (1.5 pts)
     */
    @PostMapping(value = "/ajouter", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> ajouterUtilisateur(@Valid @RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur nouvelUtilisateur = utilisateurService.ajouterUtilisateur(utilisateur);
            return new ResponseEntity<>(nouvelUtilisateur, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    

    
    /**
     * 7. Endpoint pour déclencher manuellement la tâche automatique (pour test)
     */
    @GetMapping("/utilisateurs-multi-adoption")
    public ResponseEntity<List<Utilisateur>> trouverUtilisateursAvecAdoptionMultiEtat() {
        List<Utilisateur> utilisateurs = utilisateurService.trouverUtilisateursAvecAdoptionMultiEtat();
        return ResponseEntity.ok(utilisateurs);
    }

   
}
