package com.alexlbz.collecothque.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alexlbz.collecothque.Model.Dao.BibliothequeDao;
import com.alexlbz.collecothque.Model.Dao.CollectionDao;
import com.alexlbz.collecothque.Model.Dao.EtageresDao;
import com.alexlbz.collecothque.Model.Dao.LivreDao;
import com.alexlbz.collecothque.Model.Dao.UtilisateurDao;
import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Collection;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.Model.Entity.Livre;
import com.alexlbz.collecothque.Model.Entity.Utilisateur;

@Database(entities = {Utilisateur.class, Bibliotheque.class, Etagere.class, Collection.class, Livre.class}, exportSchema = false, version = 4)
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
    public abstract EtageresDao etageresDao();
    public abstract CollectionDao collectionDao();
    public abstract LivreDao livreDao();
}
