package com.alexlbz.collecothque.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Etageres {
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "libelle")
    private String libelle;

    @ColumnInfo(name = "color")
    private Integer color;

    @ColumnInfo(name = "idLibrary")
    private Integer idLibrary;

    public Etageres(String libelle, Integer color, Integer idLibrary) {
        this.libelle = libelle;
        this.color = color;
        this.idLibrary = idLibrary;
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

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getIdLibrary() {
        return idLibrary;
    }

    public void setIdLibrary(Integer idLibrary) {
        this.idLibrary = idLibrary;
    }
}
