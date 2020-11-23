package com.alexlbz.collecothque.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Utilisateur.class}, exportSchema = false, version = 1)
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
}
