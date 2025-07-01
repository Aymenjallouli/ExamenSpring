package com.example.adoptionanimals.services;

import com.example.adoptionanimals.entities.Animal;
import com.example.adoptionanimals.entities.DemandeAdoption;
import com.example.adoptionanimals.entities.Utilisateur;
import com.example.adoptionanimals.enums.Etat;
import com.example.adoptionanimals.enums.StatutAdoption;
import com.example.adoptionanimals.repositories.AnimalRepository;
import com.example.adoptionanimals.repositories.DemandeAdoptionRepository;
import com.example.adoptionanimals.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service pour la gestion des demandes d'adoption
 */
@Service
public class DemandeAdoptionServiceNew {
    
    @Autowired
    private DemandeAdoptionRepository demandeAdoptionRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private AnimalRepository animalRepository;
    
    /**
     */
    public DemandeAdoption ajouterDemandeAdoptionEtAnimauxAssocies(DemandeAdoption da) {
        if (da == null) {
            throw new RuntimeException("La demande d'adoption ne peut pas être null");
        }
        
        // Vérifier que les champs obligatoires sont présents
        if (da.getCode() == null || da.getCode().trim().isEmpty()) {
            throw new RuntimeException("Le code de la demande est obligatoire");
        }
        
        if (da.getUtilisateur() == null || da.getUtilisateur().getId() == null) {
            throw new RuntimeException("L'utilisateur est obligatoire pour créer une demande");
        }
        
        // Vérifier si le code existe déjà
        if (demandeAdoptionRepository.existsByCode(da.getCode())) {
            throw new RuntimeException("Une demande avec ce code existe déjà: " + da.getCode());
        }
        
        // Vérifier et récupérer l'utilisateur
        Utilisateur utilisateur = utilisateurRepository.findById(da.getUtilisateur().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + da.getUtilisateur().getId()));
        da.setUtilisateur(utilisateur);
        
        // Vérifier et récupérer les animaux associés
        if (da.getAnimaux() != null && !da.getAnimaux().isEmpty()) {
            List<Animal> animauxVerifies = da.getAnimaux().stream()
                    .map(animal -> {
                        if (animal.getIdAnimal() == null) {
                            throw new RuntimeException("L'ID de l'animal ne peut pas être null");
                        }
                        return animalRepository.findById(animal.getIdAnimal())
                                .orElseThrow(() -> new RuntimeException("Animal non trouvé avec l'ID: " + animal.getIdAnimal()));
                    })
                    .toList();
            da.setAnimaux(animauxVerifies);
        } else {
            throw new RuntimeException("Au moins un animal doit être associé à la demande d'adoption");
        }
        
        // Définir les valeurs par défaut
        if (da.getDateDemande() == null) {
            da.setDateDemande(LocalDateTime.now());
        }
        
        if (da.getStatut() == null) {
            da.setStatut(StatutAdoption.DEMANDEE);
        }
        
        return demandeAdoptionRepository.save(da);
    }
    
    /**
     * 3. Affecter plusieurs demandes à un utilisateur
     */
    public void affecterDemandesAdoptionsAUtilisateur(List<String> codeAdoptions, String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("L'email de l'utilisateur est obligatoire");
        }
        
        if (codeAdoptions == null || codeAdoptions.isEmpty()) {
            throw new RuntimeException("La liste des codes d'adoption ne peut pas être vide");
        }
        
        // Vérifier que l'utilisateur existe
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aucun utilisateur trouvé avec l'email: " + email));
        
        // Affecter chaque demande à l'utilisateur
        for (String code : codeAdoptions) {
            if (code != null && !code.trim().isEmpty()) {
                DemandeAdoption demande = demandeAdoptionRepository.findByCode(code)
                        .orElseThrow(() -> new RuntimeException("Aucune demande d'adoption trouvée avec le code: " + code));
                
                demande.setUtilisateur(utilisateur);
                demandeAdoptionRepository.save(demande);
            }
        }
        
        System.out.println("Demandes d'adoption affectées avec succès à l'utilisateur " + email);
    }
    
    /**
     * 4. Rechercher animaux blessés par état et date )
     * Retourne tous les animaux ayant une demande envoyée après la date donnée, 
     * avec état et statut spécifiques.
     */
    public List<Animal> trouverAnimauxParEtatEtDate(String etat, LocalDate dateD) {
        if (etat == null || etat.trim().isEmpty()) {
            throw new RuntimeException("L'état est obligatoire");
        }
        
        if (dateD == null) {
            throw new RuntimeException("La date est obligatoire");
        }
        
        try {
            Etat etatEnum = Etat.valueOf(etat.toUpperCase());
            return animalRepository.findAnimauxParEtatEtDate(etatEnum, dateD);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("État invalide: " + etat + ". Valeurs possibles: SAIN, BLESSE");
        }
    }
    
    /**
     * 6. Vérifier si un animal blessé a une adoption demandée/validée 
     */
    public Boolean animalAdopte(String nomAnimal) {
        if (nomAnimal == null || nomAnimal.trim().isEmpty()) {
            throw new RuntimeException("Le nom de l'animal est obligatoire");
        }
        
        return animalRepository.isAnimalAdopte(nomAnimal);
    }
    
    /**
     * Récupérer toutes les demandes d'adoption
     */
    public List<DemandeAdoption> obtenirToutesLesDemandesAdoption() {
        return demandeAdoptionRepository.findAll();
    }
}
