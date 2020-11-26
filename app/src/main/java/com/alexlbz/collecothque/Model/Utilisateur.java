package com.alexlbz.collecothque.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Utilisateur {
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "nom")
    private String nom;

    @ColumnInfo(name = "email")
    private String email;

    public Utilisateur(String nom, String email){
        this.nom = nom;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
