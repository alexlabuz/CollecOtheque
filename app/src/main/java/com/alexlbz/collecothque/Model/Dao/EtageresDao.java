package com.alexlbz.collecothque.Model.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexlbz.collecothque.Model.Entity.Etageres;

import java.util.List;

@Dao
public interface EtageresDao {

    @Query("SELECT * FROM Etageres WHERE idLibrary = :idLibrary")
    List<Etageres> getByLibrary(Integer idLibrary);

    @Insert
    void insert(Etageres etageres);

}
