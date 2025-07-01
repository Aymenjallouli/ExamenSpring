package com.example.adoptionanimals.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité représentant un utilisateur du système d'adoption
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Le nom complet est obligatoire")
    @Column(name = "nom_complet", nullable = false)
    private String nomComplet;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotNull(message = "La date d'inscription est obligatoire")
    @Column(name = "date_inscription", nullable = false)
    private LocalDateTime dateInscription;
    
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("utilisateur-demandes")
    private List<DemandeAdoption> demandesAdoption;
    
    // Constructeurs
    public Utilisateur() {
        this.dateInscription = LocalDateTime.now();
    }
    
    public Utilisateur(String nomComplet, String email) {
        this();
        this.nomComplet = nomComplet;
        this.email = email;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNomComplet() {
        return nomComplet;
    }
    
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public LocalDateTime getDateInscription() {
        return dateInscription;
    }
    
    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }
    
    public List<DemandeAdoption> getDemandesAdoption() {
        return demandesAdoption;
    }
    
    public void setDemandesAdoption(List<DemandeAdoption> demandesAdoption) {
        this.demandesAdoption = demandesAdoption;
    }
    
    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nomComplet='" + nomComplet + '\'' +
                ", email='" + email + '\'' +
                ", dateInscription=" + dateInscription +
                '}';
    }
}
