package com.alexlbz.collecothque.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alexlbz.collecothque.Model.Adapter.CollectionAdapter;
import com.alexlbz.collecothque.Model.AppDatabase;
import com.alexlbz.collecothque.Model.Entity.Collection;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.Model.RequestDatabase;
import com.alexlbz.collecothque.R;

import java.util.List;

public class CollectionManagementActivity extends AppCompatActivity {

    private AppDatabase db;
    private Etagere etagere;

    private RecyclerView mRecyclerCollection;
    private TextView mTextDescCollectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_management);
        init();
    }

    private void init() {
        if(getIntent() != null){
            this.etagere = (Etagere) getIntent().getSerializableExtra(ShelfActivity.INTENT_EXTRA_SHELF);
        }
        this.db = AppDatabase.getInstance(this);

        this.mRecyclerCollection = findViewById(R.id.recyclerCollection);
        this.mTextDescCollectionList = findViewById(R.id.textDescCollectionList);
        this.mTextDescCollectionList.setText(String.format(getString(R.string.collection_description), etagere.getLibelle()));

        refrechCollectionList();
    }

    private void refrechCollectionList(){
        List<Collection> list = this.db.collectionDao().selectByEtagere(this.etagere.getId());

        CollectionAdapter adapter = new CollectionAdapter(list) {
            @Override
            public void click(Integer action, Collection collection) {
                if(action.equals(CollectionAdapter.DELETE_COLLECTION)){
                    deleteCollection(collection);
                }else if(action.equals(CollectionAdapter.UPDATE_COLLECTION)){
                    changeNameCollection(collection);
                }
            }
        };

        this.mRecyclerCollection.setAdapter(adapter);
        this.mRecyclerCollection.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Fonction qui ouvre un AlertDialog demandent de changer le nom d'une collection
     * @param collection collection à modifier
     */
    private void changeNameCollection(final Collection collection) {
        final View view = getLayoutInflater().inflate(R.layout.dialog_collection, null);

        // Préremplie les champs "nom" et "couleur" de la collection
        ((EditText) view.findViewById(R.id.editTextNameCollection)).setText(collection.getLibelle());
        ((EditText) view.findViewById(R.id.editTextColorCollection)).setText(""+collection.getCouleur());

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Modifier le nom de " + collection.getLibelle())
                .setMessage("Saisissez le nouveau nom et/ou la nouvelle couleur de la collection :")
                .setView(view)
                .setNegativeButton("Retour", null)
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        collection.setLibelle(""+((EditText) view.findViewById(R.id.editTextNameCollection)).getText());
                        collection.setCouleur(Integer.parseInt(""+((EditText) view.findViewById(R.id.editTextColorCollection)).getText()));
                        db.collectionDao().update(collection);
                        refrechCollectionList();
                    }
                }).create().show();
    }

    /**
     * Fonction qui ouvre un AlertDialog demandent de supprimer la collection
     * @param collection collection à supprimer
     */
    private void deleteCollection(final Collection collection){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Supprimer la collection " + collection.getLibelle() + " ?")
                .setMessage("Tous les livres de cette collection serons supprimés !")
                .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.collectionDao().delete(collection);
                        refrechCollectionList();
                    }
                })
                .setNegativeButton("Retour", null).create().show();
    }

}