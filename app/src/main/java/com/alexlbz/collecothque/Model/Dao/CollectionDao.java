package com.alexlbz.collecothque.Model.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alexlbz.collecothque.Model.Entity.Collection;

import java.util.List;

@Dao
public interface CollectionDao {

    @Insert
    void insert(Collection collection);

    @Update
    void update(Collection collection);

    @Delete
    void delete(Collection collection);

    @Query("SELECT * FROM Collection WHERE id = :id")
    Collection selectById(Integer id);

    @Query("SELECT * FROM Collection WHERE idEtagere = :idEtagere")
    List<Collection> selectByEtagere(Integer idEtagere);

}
