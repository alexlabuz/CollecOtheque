package com.alexlbz.collecothque.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alexlbz.collecothque.AppDatabase;
import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Collection;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity {

    private AppDatabase db;
    private Bibliotheque bibliotheque;
    private Etagere etagere;

    private TextView mTextBookListTitle;
    private RecyclerView mRecyclerBook;
    private FloatingActionButton mBtnAddBook;
    private Button mBtnAddCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        init();
    }

    private void init() {
        this.mTextBookListTitle = findViewById(R.id.textBookListTitle);
        this.mRecyclerBook = findViewById(R.id.recyclerBook);
        this.mBtnAddBook = findViewById(R.id.btnAddBook);
        this.mBtnAddCollection = findViewById(R.id.btnAddCollection);

        this.db = AppDatabase.getInstance(this);

        if(getIntent() != null){
            this.bibliotheque = (Bibliotheque) getIntent().getSerializableExtra(MainActivity.INTENT_EXTRA_LIBRARY);
            this.etagere = (Etagere) getIntent().getSerializableExtra(ShelfActivity.INTENT_EXTRA_SHELF);
        }

        this.mTextBookListTitle.setText(String.format(getString(R.string.book_list_title), this.bibliotheque.getName(), this.etagere.getLibelle()));

        this.mBtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicAddBtn();
            }
        });

        this.mBtnAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicAddCollectionBtn();
            }
        });
    }

    private void clicAddBtn() {
        final View view = getLayoutInflater().inflate(R.layout.add_book, null);


        // Et les insére dans le spinner d'ajout de livre
        Spinner spinner = view.findViewById(R.id.spinnerCollectionAddBook);

        List<Collection> collectionList = this.db.collectionDao().selectByEtagere(this.etagere.getId());

        // Créer des collections de test
        ArrayList<String> itemsSpinner = new ArrayList<String>();
        for(Collection c : collectionList){
            itemsSpinner.add(c.getLibelle());
        }

        ArrayAdapter<String> itemsSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemsSpinner);
        spinner.setAdapter(itemsSpinnerAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un livre")
                .setMessage("Veuillez saisir le code ISBN du livre")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void clicAddCollectionBtn() {
        final View view = getLayoutInflater().inflate(R.layout.add_collection, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une collection")
                .setMessage("Veuillez saisir le nom de la collection à ajouter dans l'étagère")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addCollection(""+((EditText)view.findViewById(R.id.editTextAddCollection)).getText());
                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void addCollection(String s) {
        this.db.collectionDao().insert(new Collection(s, this.etagere.getId()));
    }


}