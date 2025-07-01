package com.example.adoptionanimals.entities;

import com.example.adoptionanimals.enums.Etat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Entité représentant un animal disponible pour adoption
 */
@Entity
@Table(name = "animal")
public class Animal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_animal")
    private Long idAnimal;
    
    @NotBlank(message = "Le nom de l'animal est obligatoire")
    @Column(nullable = false)
    private String nom;
    
    @NotBlank(message = "L'espèce est obligatoire")
    @Column(nullable = false)
    private String espece;
    
    @NotNull(message = "L'état de l'animal est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etat etat;
    
    @ManyToMany(mappedBy = "animaux", fetch = FetchType.LAZY)
    @JsonBackReference("demande-animaux")
    private List<DemandeAdoption> demandesAdoption;
    
    // Constructeurs
    public Animal() {}
    
    public Animal(String nom, String espece, Etat etat) {
        this.nom = nom;
        this.espece = espece;
        this.etat = etat;
    }
    
    // Getters et Setters
    public Long getIdAnimal() {
        return idAnimal;
    }
    
    public void setIdAnimal(Long idAnimal) {
        this.idAnimal = idAnimal;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getEspece() {
        return espece;
    }
    
    public void setEspece(String espece) {
        this.espece = espece;
    }
    
    public Etat getEtat() {
        return etat;
    }
    
    public void setEtat(Etat etat) {
        this.etat = etat;
    }
    
    public List<DemandeAdoption> getDemandesAdoption() {
        return demandesAdoption;
    }
    
    public void setDemandesAdoption(List<DemandeAdoption> demandesAdoption) {
        this.demandesAdoption = demandesAdoption;
    }
    
    @Override
    public String toString() {
        return "Animal{" +
                "idAnimal=" + idAnimal +
                ", nom='" + nom + '\'' +
                ", espece='" + espece + '\'' +
                ", etat=" + etat +
                '}';
    }
}
