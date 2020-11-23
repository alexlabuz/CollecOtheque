package com.alexlbz.collecothque.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UtilisateurDao {

    @Query("SELECT * FROM utilisateur")
    List<Utilisateur> getAll();

    @Insert
    void insertAll(Utilisateur... utilisateurs);

    @Insert
    void insert(Utilisateur utilisateur);

}
