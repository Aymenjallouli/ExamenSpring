package com.example.adoptionanimals.repositories;

import com.example.adoptionanimals.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Utilisateur
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    
    /**
     * Trouve un utilisateur par son email
     * @param email l'email de l'utilisateur
     * @return l'utilisateur trouvé
     */
    Optional<Utilisateur> findByEmail(String email);
    
    /**
     * Vérifie si un email existe déjà
     * @param email l'email à vérifier
     * @return true si l'email existe
     */
    boolean existsByEmail(String email);
    
    /**
     * 7. Trouve les utilisateurs ayant au moins 2 adoptions contenant des animaux SAINS
     * Utilise JPQL pour la requête
     */
    @Query("SELECT DISTINCT u FROM Utilisateur u " +
           "JOIN u.demandesAdoption da " +
           "JOIN da.animaux a " +
           "WHERE a.etat = 'SAIN' " +
           "GROUP BY u.id " +
           "HAVING COUNT(DISTINCT da.id) >= 2")
    List<Utilisateur> findUtilisateursAvecAdoptionMultiEtat();
}
