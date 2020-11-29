package com.alexlbz.collecothque.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Collection {
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "libelle")
    private String libelle;

    @ColumnInfo(name = "idEtagere")
    private Integer idEtagere;

    public Collection(String libelle, Integer idEtagere) {
        this.libelle = libelle;
        this.idEtagere = idEtagere;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getIdEtagere() {
        return idEtagere;
    }

    public void setIdEtagere(Integer idEtagere) {
        this.idEtagere = idEtagere;
    }
}
