package com.alexlbz.collecothque.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Bibliotheque.class,
        parentColumns = "id",
        childColumns = "idLibrary",
        onDelete = CASCADE))
public class Etagere implements Serializable {
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "libelle")
    private String libelle;

    @ColumnInfo(name = "idLibrary")
    private Integer idLibrary;

    public Etagere(String libelle, Integer idLibrary) {
        this.libelle = libelle;
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

    public Integer getIdLibrary() {
        return idLibrary;
    }

    public void setIdLibrary(Integer idLibrary) {
        this.idLibrary = idLibrary;
    }
}
