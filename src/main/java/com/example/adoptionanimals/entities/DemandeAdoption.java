package com.example.adoptionanimals.entities;

import com.example.adoptionanimals.enums.StatutAdoption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entité représentant une demande d'adoption d'animaux
 */
@Entity
@Table(name = "demande_adoption")
public class DemandeAdoption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adoption")
    private Long idAdoption;
    
    @NotBlank(message = "Le code de la demande est obligatoire")
    @Column(nullable = false, unique = true)
    private String code;
    
    @NotNull(message = "La date de la demande est obligatoire")
    @Column(name = "date_demande", nullable = false)
    private LocalDateTime dateDemande;
    
    @Column(columnDefinition = "TEXT")
    private String commentaire;
    
    @NotNull(message = "Le statut est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutAdoption statut;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    @JsonBackReference("utilisateur-demandes")
    private Utilisateur utilisateur;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "demande_animal",
        joinColumns = @JoinColumn(name = "demande_id"),
        inverseJoinColumns = @JoinColumn(name = "animal_id")
    )
    private List<Animal> animaux;
    
    // Constructeurs
    public DemandeAdoption() {
        this.dateDemande = LocalDateTime.now();
        this.statut = StatutAdoption.DEMANDEE;
    }
    
    public DemandeAdoption(String code, String commentaire, Utilisateur utilisateur, List<Animal> animaux) {
        this();
        this.code = code;
        this.commentaire = commentaire;
        this.utilisateur = utilisateur;
        this.animaux = animaux;
    }
    
    // Getters et Setters
    public Long getIdAdoption() {
        return idAdoption;
    }
    
    public void setIdAdoption(Long idAdoption) {
        this.idAdoption = idAdoption;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public LocalDateTime getDateDemande() {
        return dateDemande;
    }
    
    public void setDateDemande(LocalDateTime dateDemande) {
        this.dateDemande = dateDemande;
    }
    
    public String getCommentaire() {
        return commentaire;
    }
    
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public StatutAdoption getStatut() {
        return statut;
    }
    
    public void setStatut(StatutAdoption statut) {
        this.statut = statut;
    }
    
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public List<Animal> getAnimaux() {
        return animaux;
    }
    
    public void setAnimaux(List<Animal> animaux) {
        this.animaux = animaux;
    }
    
    @Override
    public String toString() {
        return "DemandeAdoption{" +
                "idAdoption=" + idAdoption +
                ", code='" + code + '\'' +
                ", dateDemande=" + dateDemande +
                ", commentaire='" + commentaire + '\'' +
                ", statut=" + statut +
                '}';
    }
}
