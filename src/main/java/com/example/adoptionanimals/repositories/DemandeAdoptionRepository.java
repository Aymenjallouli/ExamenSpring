package com.example.adoptionanimals.repositories;

import com.example.adoptionanimals.entities.DemandeAdoption;
import com.example.adoptionanimals.entities.Utilisateur;
import com.example.adoptionanimals.entities.Animal;
import com.example.adoptionanimals.enums.StatutAdoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité DemandeAdoption
 */
@Repository
public interface DemandeAdoptionRepository extends JpaRepository<DemandeAdoption, Long> {
    
    /**
     * Trouve une demande par son code
     * @param code le code de la demande
     * @return la demande trouvée
     */
    Optional<DemandeAdoption> findByCode(String code);
    
    /**
     * Trouve toutes les demandes d'un utilisateur
     * @param utilisateur l'utilisateur
     * @return la liste des demandes de cet utilisateur
     */
    List<DemandeAdoption> findByUtilisateur(Utilisateur utilisateur);
    
    /**
     * Trouve toutes les demandes par statut
     * @param statut le statut recherché
     * @return la liste des demandes avec ce statut
     */
    List<DemandeAdoption> findByStatut(StatutAdoption statut);
    
    /**
     * Trouve toutes les demandes d'un utilisateur par statut
     * @param utilisateur l'utilisateur
     * @param statut le statut
     * @return la liste des demandes
     */
    List<DemandeAdoption> findByUtilisateurAndStatut(Utilisateur utilisateur, StatutAdoption statut);
    
    /**
     * Vérifie si un code de demande existe déjà
     * @param code le code à vérifier
     * @return true si le code existe
     */
    boolean existsByCode(String code);
    
    /**
     * Compte le nombre de demandes par statut
     * @param statut le statut
     * @return le nombre de demandes
     */
    @Query("SELECT COUNT(d) FROM DemandeAdoption d WHERE d.statut = :statut")
    long countByStatut(@Param("statut") StatutAdoption statut);
    
    /**
     * Trouve les demandes d'adoption contenant un animal spécifique avec des statuts donnés
     */
    List<DemandeAdoption> findByAnimauxContainingAndStatutIn(Animal animal, List<StatutAdoption> statuts);
}
