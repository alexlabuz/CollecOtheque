package com.alexlbz.collecothque.Model.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alexlbz.collecothque.Model.Entity.Collection;

import java.util.List;

@Dao
public interface CollectionDao {

    @Insert
    void insert(Collection collection);

    @Query("SELECT * FROM Collection WHERE idEtagere = :idEtagere")
    List<Collection> selectByEtagere(Integer idEtagere);

}
