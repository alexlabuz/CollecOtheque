package com.alexlbz.collecothque.Model.Entity;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;

public class Livre {
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "ISBN")
    private String isbn;

    @ColumnInfo(name = "titre")
    private String titre;

    @ColumnInfo(name = "resume")
    private String resume;

    @ColumnInfo(name = "data_parution")
    private Date dateParution;

    @ColumnInfo(name = "idCollection")
    private Integer idCollection;

    @ColumnInfo(name = "idEtagere")
    private Integer idEtagere;

    public Livre(String isbn, String titre, String resume, Date dateParution, Integer idCollection, Integer idEtagere) {
        this.isbn = isbn;
        this.titre = titre;
        this.resume = resume;
        this.dateParution = dateParution;
        this.idCollection = idCollection;
        this.idEtagere = idEtagere;
    }

}
