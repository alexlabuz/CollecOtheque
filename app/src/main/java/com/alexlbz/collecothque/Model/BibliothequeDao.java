package com.alexlbz.collecothque.Model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BibliothequeDao {

    @Query("SELECT * FROM Bibliotheque")
    List<Bibliotheque> getAll();

    @Insert
    void insert(Bibliotheque bibliotheque);
}
