package com.example.adoptionanimals.services;

import com.example.adoptionanimals.entities.Animal;
import com.example.adoptionanimals.entities.DemandeAdoption;
import com.example.adoptionanimals.enums.Etat;
import com.example.adoptionanimals.enums.StatutAdoption;
import com.example.adoptionanimals.repositories.AnimalRepository;
import com.example.adoptionanimals.repositories.DemandeAdoptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service pour la gestion des animaux
 */
@Service
public class AnimalService {
    
    @Autowired
    private AnimalRepository animalRepository;
    
    @Autowired
    private DemandeAdoptionRepository demandeAdoptionRepository;
    
    /**
     * Créer un nouvel animal
     */
    public Animal creerAnimal(Animal animal) {
        if (animal.getNom() == null || animal.getNom().trim().isEmpty()) {
            throw new RuntimeException("Le nom de l'animal est obligatoire");
        }
        if (animal.getEspece() == null || animal.getEspece().trim().isEmpty()) {
            throw new RuntimeException("L'espèce de l'animal est obligatoire");
        }
        if (animal.getEtat() == null) {
            throw new RuntimeException("L'état de l'animal est obligatoire");
        }
        
        return animalRepository.save(animal);
    }
    
    /**
     * 4. Rechercher animaux par état et date (2.5 pts)
     * Retourne les animaux ayant l'état donné et dont les demandes d'adoption 
     * ont été créées après la date donnée
     */
    public List<Animal> trouverAnimauxParEtatEtDate(String etat, LocalDate dateD) {
        if (etat == null || etat.trim().isEmpty()) {
            throw new RuntimeException("L'état est obligatoire");
        }
        if (dateD == null) {
            throw new RuntimeException("La date est obligatoire");
        }
        
        try {
            Etat etatAnimal = Etat.valueOf(etat.toUpperCase());
            return animalRepository.findAnimauxParEtatEtDate(etatAnimal, dateD);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("État d'animal invalide: " + etat + ". Valeurs possibles: SAIN, BLESSE");
        }
    }
    
    /**
     * 6. Vérifier si un animal blessé a une adoption demandée/validée (2.5 pts)
     * Retourne true si l'animal blessé a au moins une demande d'adoption 
     * avec statut DEMANDEE ou VALIDEE
     */
    public Boolean animalAdopte(String nomAnimal) {
        Animal animal = animalRepository.findByNom(nomAnimal);
        if (animal == null) {
            throw new RuntimeException("Animal non trouvé: " + nomAnimal);
        }
        
        // Vérifier si l'animal est blessé
        if (!animal.getEtat().equals(Etat.BLESSE)) {
            return false;
        }
        
        // Chercher les demandes d'adoption pour cet animal avec statut DEMANDEE ou VALIDEE
        List<DemandeAdoption> demandes = demandeAdoptionRepository.findByAnimauxContainingAndStatutIn(
            animal, 
            List.of(StatutAdoption.DEMANDEE, StatutAdoption.VALIDEE)
        );
        
        return !demandes.isEmpty();
    }
    
    /**
     * Récupérer tous les animaux
     */
    public List<Animal> obtenirTousLesAnimaux() {
        return animalRepository.findAll();
    }
}
