package com.alexlbz.collecothque.Model.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexlbz.collecothque.Model.Entity.Etagere;

import java.util.List;

@Dao
public interface EtageresDao {

    @Query("SELECT * FROM Etagere WHERE idLibrary = :idLibrary")
    List<Etagere> getByLibrary(Integer idLibrary);

    @Insert
    void insert(Etagere etageres);

    @Update
    void update(Etagere etagere);

    @Delete
    void delete(Etagere etagere);

}
