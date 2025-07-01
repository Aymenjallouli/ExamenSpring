package com.example.adoptionanimals.controllers;

import com.example.adoptionanimals.entities.Animal;
import com.example.adoptionanimals.entities.DemandeAdoption;
import com.example.adoptionanimals.services.DemandeAdoptionServiceNew;
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
 * Contrôleur REST pour la gestion des demandes d'adoption
 */
@RestController
@RequestMapping("/api/demandes-adoption")
@CrossOrigin(origins = "*")
public class DemandeAdoptionControllerNew {
    
    @Autowired
    private DemandeAdoptionServiceNew demandeAdoptionService;
    
    /**
     * 2. Ajouter une demande d'adoption et ses animaux (2 pts)
     */
    @PostMapping("/ajouter-avec-animaux")
    public ResponseEntity<?> ajouterDemandeAdoptionEtAnimauxAssocies(@Valid @RequestBody DemandeAdoption demandeAdoption) {
        try {
            DemandeAdoption nouvelleDemande = demandeAdoptionService.ajouterDemandeAdoptionEtAnimauxAssocies(demandeAdoption);
            return new ResponseEntity<>(nouvelleDemande, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 3. Affecter plusieurs demandes à un utilisateur (2 pts)
     */
    @PostMapping("/affecter-utilisateur")
    public ResponseEntity<?> affecterDemandesAdoptionsAUtilisateur(@RequestBody Map<String, Object> requestBody) {
        try {
            @SuppressWarnings("unchecked")
            List<String> codeAdoptions = (List<String>) requestBody.get("codeAdoptions");
            String email = (String) requestBody.get("email");
            
            demandeAdoptionService.affecterDemandesAdoptionsAUtilisateur(codeAdoptions, email);
            
            return ResponseEntity.ok(Map.of("message", "Demandes d'adoption affectées avec succès à l'utilisateur " + email));
        } catch (ClassCastException e) {
            return new ResponseEntity<>(Map.of("erreur", "Format de données invalide"), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 4. Rechercher animaux blessés par état et date (2.5 pts)
     */
    @GetMapping("/animaux-par-etat-date")
    public ResponseEntity<?> trouverAnimauxParEtatEtDate(
            @RequestParam String etat,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateD) {
        try {
            List<Animal> animaux = demandeAdoptionService.trouverAnimauxParEtatEtDate(etat, dateD);
            return ResponseEntity.ok(animaux);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * 6. Vérifier si un animal blessé a une adoption demandée/validée (2.5 pts)
     */
    @GetMapping("/animal-adopte")
    public ResponseEntity<?> animalAdopte(@RequestParam String nomAnimal) {
        try {
            Boolean adopte = demandeAdoptionService.animalAdopte(nomAnimal);
            return ResponseEntity.ok(Map.of("nomAnimal", nomAnimal, "adopte", adopte));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(Map.of("erreur", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
