package com.example.adoptionanimals.services;

import com.example.adoptionanimals.entities.Utilisateur;
import com.example.adoptionanimals.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service pour la gestion des utilisateurs
 */
@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
  
    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) {
            throw new RuntimeException("L'utilisateur ne peut pas être null");
        }
        
        if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty()) {
            throw new RuntimeException("L'email de l'utilisateur est obligatoire");
        }
        
        if (utilisateur.getNomComplet() == null || utilisateur.getNomComplet().trim().isEmpty()) {
            throw new RuntimeException("Le nom complet de l'utilisateur est obligatoire");
        }
        
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà: " + utilisateur.getEmail());
        }
        
        // Définir la date d'inscription si elle n'est pas définie
        if (utilisateur.getDateInscription() == null) {
            utilisateur.setDateInscription(LocalDateTime.now());
        }
        
        return utilisateurRepository.save(utilisateur);
    }
    
    /**
     * 7. Tâche automatique tous les mardis 
     * Utilise @Scheduled (tous les 20s chaque mardi) et JPQL pour afficher les utilisateurs 
     * ayant au moins 2 adoptions contenant des animaux SAINS.
     */
    @Scheduled(cron = "*/20 * * * * TUE")
    public List<Utilisateur> trouverUtilisateursAvecAdoptionMultiEtat() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findUtilisateursAvecAdoptionMultiEtat();
        
        System.out.println("=== Tâche automatique - Mardi ===");
        System.out.println("Utilisateurs avec au moins 2 adoptions contenant des animaux SAINS:");
        for (Utilisateur utilisateur : utilisateurs) {
            System.out.println("- " + utilisateur.getNomComplet() + " (" + utilisateur.getEmail() + ")");
        }
        System.out.println("Total trouvé: " + utilisateurs.size());
        
        return utilisateurs;
    }
    
    /**
     * Récupérer tous les utilisateurs
     */
    public List<Utilisateur> obtenirTousLesUtilisateurs() {
        return utilisateurRepository.findAll();
    }
    
    /**
     * Supprime un utilisateur par son identifiant
     */
    public void supprimerUtilisateur(Long id) {
        if (id == null) {
            throw new RuntimeException("L'identifiant de l'utilisateur ne peut pas être null");
        }
        
        if (!utilisateurRepository.existsById(id)) {
            throw new RuntimeException("Aucun utilisateur trouvé avec l'identifiant: " + id);
        }
        
        utilisateurRepository.deleteById(id);
    }
}
