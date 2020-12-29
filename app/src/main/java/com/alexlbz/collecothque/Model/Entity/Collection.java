package com.alexlbz.collecothque.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Etagere.class,
        parentColumns = "id",
        childColumns = "idEtagere",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Collection implements Serializable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "libelle")
    private String libelle;

    @ColumnInfo(name = "couleur")
    private Integer couleur;

    @ColumnInfo(name = "idEtagere")
    private Integer idEtagere;

    public Collection(String libelle, Integer couleur, Integer idEtagere) {
        this.libelle = libelle;
        this.couleur = couleur;
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

    public Integer getCouleur() {
        return couleur;
    }

    public void setCouleur(Integer couleur) {
        this.couleur = couleur;
    }

    public Integer getIdEtagere() {
        return idEtagere;
    }

    public void setIdEtagere(Integer idEtagere) {
        this.idEtagere = idEtagere;
    }
}
