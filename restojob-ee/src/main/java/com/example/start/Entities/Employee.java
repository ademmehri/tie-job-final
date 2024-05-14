package com.example.start.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String gouvernerat;
    private String num;
    private String sexe;
    private String date_nais;
    private String specialite;
    private String description;
    private String exp;
    private String cin;
    private String email;
    private String password;
    private boolean enable;
    private String cp;
    private String city;
    private String etat;
    private Boolean enabled;
    private TypePack pack;
    private Duree duree;
    private LocalDateTime d_inscrit=LocalDateTime.now();   
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
   @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user_id") ,
    inverseJoinColumns = @JoinColumn(name="role_id"))
   private List<Role> roles;
    
    @OneToMany(mappedBy = "emp")
    private List<File> listemp=new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    Set<Offre> offreemp;
    @JsonIgnore
    @OneToMany(mappedBy = "employeur")
    Set<Offre> offreempr;

 
 
}
