package com.example.adoptionanimals.controllers;

import com.example.adoptionanimals.entities.DemandeAdoption;
import com.example.adoptionanimals.services.DemandeAdoptionServiceNew;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Contrôleur REST pour la gestion des demandes d'adoption
 */
@RestController
@RequestMapping("/api/demandes-adoption")
@CrossOrigin(origins = "*")
public class DemandeAdoptionController {
    
    @Autowired
    private DemandeAdoptionServiceNew demandeAdoptionService;
    
    /**
     * 2. Ajouter une demande d'adoption et ses animaux (2 pts)
     */
    @PostMapping(value = "/ajouter-avec-animaux", consumes = "application/json", produces = "application/json")
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
     * Récupérer toutes les demandes d'adoption
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<DemandeAdoption>> obtenirToutesLesDemandesAdoption() {
        List<DemandeAdoption> demandes = demandeAdoptionService.obtenirToutesLesDemandesAdoption();
        return ResponseEntity.ok(demandes);
    }
}
