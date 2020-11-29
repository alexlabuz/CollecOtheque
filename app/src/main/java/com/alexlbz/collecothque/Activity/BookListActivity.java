package com.alexlbz.collecothque.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alexlbz.collecothque.AppDatabase;
import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    private AppDatabase db;
    private Bibliotheque bibliotheque;
    private Etagere etagere;

    private TextView mTextBookListTitle;
    private RecyclerView mRecyclerBook;
    private FloatingActionButton mBtnAddBook;


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
    }

    private void clicAddBtn() {
        final View view = getLayoutInflater().inflate(R.layout.add_book, null);

        // Créer des collections de test
        ArrayList<String> itemsSpinner = new ArrayList<String>();
        itemsSpinner.add("item1");
        itemsSpinner.add("item2");
        itemsSpinner.add("item3");
        itemsSpinner.add("item4");

        // Et les insére dans le spinner d'ajout de livre
        Spinner spinner = view.findViewById(R.id.spinnerCollectionAddBook);
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

}