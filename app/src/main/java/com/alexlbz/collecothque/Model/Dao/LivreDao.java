package com.alexlbz.collecothque.Model.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexlbz.collecothque.Model.Entity.Livre;

import java.util.List;

@Dao
public interface LivreDao {

    @Insert
    void insert(Livre livre);

    @Query("SELECT * FROM Livre WHERE idEtagere = :idEtagere")
    List<Livre> getByEtagere(Integer idEtagere);

}
