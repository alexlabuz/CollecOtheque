package com.alexlbz.collecothque.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bibliotheque {
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "idUser")
    private Integer idUtilisateur;

    public Bibliotheque(String name, Integer idUtilisateur) {
        this.name = name;
        this.idUtilisateur = idUtilisateur;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Integer idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
