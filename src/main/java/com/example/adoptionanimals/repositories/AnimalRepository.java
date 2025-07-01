package com.example.adoptionanimals.repositories;

import com.example.adoptionanimals.entities.Animal;
import com.example.adoptionanimals.enums.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour l'entité Animal
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    
    /**
     * Trouve tous les animaux par espèce
     * @param espece l'espèce recherchée
     * @return la liste des animaux de cette espèce
     */
    List<Animal> findByEspece(String espece);
    
    /**
     * Trouve tous les animaux par état
     * @param etat l'état recherché
     * @return la liste des animaux dans cet état
     */
    List<Animal> findByEtat(Etat etat);
    
    /**
     * Trouve un animal par nom
     * @param nom le nom de l'animal
     * @return l'animal trouvé ou null
     */
    Animal findByNom(String nom);
    
    /**
     * Trouve tous les animaux par nom (insensible à la casse)
     * @param nom le nom recherché
     * @return la liste des animaux avec ce nom
     */
    List<Animal> findByNomContainingIgnoreCase(String nom);
    
    /**
     * 4. Rechercher animaux par état et date de demande
     * Retourne tous les animaux ayant une demande envoyée après la date donnée, 
     * avec état spécifique.
     */
    @Query("SELECT DISTINCT a FROM Animal a " +
           "JOIN a.demandesAdoption da " +
           "WHERE a.etat = :etat " +
           "AND DATE(da.dateDemande) > :dateD")
    List<Animal> findAnimauxParEtatEtDate(@Param("etat") Etat etat, @Param("dateD") LocalDate dateD);
    
    /**
     * 6. Vérifier si un animal blessé a une adoption demandée/validée
     * Retourne true si l'animal blessé a au moins une demande DEMANDEE ou VALIDEE
     */
    @Query("SELECT CASE WHEN COUNT(da) > 0 THEN true ELSE false END " +
           "FROM Animal a " +
           "JOIN a.demandesAdoption da " +
           "WHERE a.nom = :nomAnimal " +
           "AND a.etat = 'BLESSE' " +
           "AND (da.statut = 'DEMANDEE' OR da.statut = 'VALIDEE')")
    Boolean isAnimalAdopte(@Param("nomAnimal") String nomAnimal);
}
