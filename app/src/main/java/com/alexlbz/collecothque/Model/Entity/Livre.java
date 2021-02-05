package com.alexlbz.collecothque.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Collection.class,
        parentColumns = "id",
        childColumns = "idCollection",
        onDelete = CASCADE,
        onUpdate = CASCADE))
public class Livre implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "ISBN")
    private String isbn;

    @ColumnInfo(name = "titre")
    private String titre;

    @ColumnInfo(name = "resume")
    private String resume;

    @ColumnInfo(name = "editeur")
    private String editeur;

    @ColumnInfo(name = "data_parution")
    private String dateParution;

    @ColumnInfo(name = "nb_de_page")
    private Integer nbDePage;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "idCollection")
    private Integer idCollection;

    public Livre(String isbn, String titre, String resume, String editeur, String dateParution, Integer nbDePage, String image, Integer idCollection) {
        this.isbn = isbn;
        this.titre = titre;
        this.resume = resume;
        this.editeur = editeur;
        this.dateParution = dateParution;
        this.nbDePage = nbDePage;
        this.image = image;
        this.idCollection = idCollection;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getDateParution() {
        return dateParution;
    }

    public void setDateParution(String dateParution) {
        this.dateParution = dateParution;
    }

    public Integer getNbDePage() {
        return nbDePage;
    }

    public void setNbDePage(Integer nbDePage) {
        this.nbDePage = nbDePage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getIdCollection() {
        return idCollection;
    }

    public void setIdCollection(Integer idCollection) {
        this.idCollection = idCollection;
    }
}
