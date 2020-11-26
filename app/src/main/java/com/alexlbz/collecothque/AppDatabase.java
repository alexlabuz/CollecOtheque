package com.alexlbz.collecothque;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alexlbz.collecothque.Model.Bibliotheque;
import com.alexlbz.collecothque.Model.BibliothequeDao;
import com.alexlbz.collecothque.Model.Utilisateur;
import com.alexlbz.collecothque.Model.UtilisateurDao;

@Database(entities = {Utilisateur.class, Bibliotheque.class}, exportSchema = false, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "collecotheque";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
        }
        return instance;
    }

    public abstract UtilisateurDao utilisateurDao();
    public abstract BibliothequeDao bibliothequeDao();
}
