package com.example.adoptionanimals.controllers;

import com.example.adoptionanimals.entities.Animal;
import com.example.adoptionanimals.services.AnimalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des animaux
 */
@RestController
@RequestMapping("/api/animaux")
@CrossOrigin(origins = "*")
public class AnimalController {
    
    @Autowired
    private AnimalService animalService;
    
    /**
     * Créer un nouvel animal
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> creerAnimal(@Valid @RequestBody Animal animal) {
        try {
            Animal nouvelAnimal = animalService.creerAnimal(animal);
            return new ResponseEntity<>(nouvelAnimal, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Récupérer tous les animaux
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Animal>> obtenirTousLesAnimaux() {
        List<Animal> animaux = animalService.obtenirTousLesAnimaux();
        return ResponseEntity.ok(animaux);
    }
    
    /**
     * 4. Rechercher animaux par état et date (2.5 pts)
     */
    @GetMapping(value = "/rechercher-par-etat-date", produces = "application/json")
    public ResponseEntity<?> trouverAnimauxParEtatEtDate(
            @RequestParam String etat,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateD) {
        try {
            List<Animal> animaux = animalService.trouverAnimauxParEtatEtDate(etat, dateD);
            return ResponseEntity.ok(animaux);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 6. Vérifier si un animal blessé a une adoption demandée/validée (2.5 pts)
     */
    @GetMapping(value = "/animal-adopte/{nomAnimal}", produces = "application/json")
    public ResponseEntity<?> animalAdopte(@PathVariable String nomAnimal) {
        try {
            Boolean adopte = animalService.animalAdopte(nomAnimal);
            return ResponseEntity.ok(Map.of("adopte", adopte, "nomAnimal", nomAnimal));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}