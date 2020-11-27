package com.alexlbz.collecothque.Model.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexlbz.collecothque.Model.Entity.Bibliotheque;

import java.util.List;

@Dao
public interface BibliothequeDao {

    @Query("SELECT * FROM Bibliotheque")
    List<Bibliotheque> getAll();

    @Query("SELECT * FROM Bibliotheque WHERE id = :id")
    Bibliotheque get(Integer id);

    @Insert
    void insert(Bibliotheque bibliotheque);
}
