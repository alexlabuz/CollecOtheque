package com.alexlbz.collecothque.Model.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexlbz.collecothque.Model.Entity.Livre;

import java.util.List;

@Dao
public interface LivreDao {

    @Insert
    void insert(Livre livre);

    @Delete
    void delete(Livre livre);

    @Update
    void update(Livre livre);

    @Query("SELECT * FROM Livre WHERE idEtagere = :idEtagere")
    List<Livre> getByEtagere(Integer idEtagere);

}
