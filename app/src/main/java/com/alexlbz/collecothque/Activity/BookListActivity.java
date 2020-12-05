package com.alexlbz.collecothque.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alexlbz.collecothque.Model.AppDatabase;
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

    public final static String INTENT_EXTRA_ISBN = "INTENT_EXTRA_ISBN";
    public final static String INTENT_EXTRA_COLLECTION = "INTENT_EXTRA_COLLECTION";

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
                clicAddBookBtn();
            }
        });

        this.mBtnAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicAddCollectionBtn();
            }
        });
    }

    private void clicAddBookBtn() {
        final View view = getLayoutInflater().inflate(R.layout.add_book, null);

        // Récupère le spinner (menu déroulant)
        final Spinner spinner = view.findViewById(R.id.spinnerCollectionAddBook);
        // Récupère et stocke les collection dans une liste
        final List<Collection> collectionList = this.db.collectionDao().selectByEtagere(this.etagere.getId());

        // Créer une une seconde liste (type string) qui contiendra les noms des collections
        ArrayList<String> labelSpinnerList = new ArrayList<String>();
        for(Collection c : collectionList){
            labelSpinnerList.add(c.getLibelle());
        }

        // Ajoute les noms de collection dans le spinner
        ArrayAdapter<String> itemsSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labelSpinnerList);
        spinner.setAdapter(itemsSpinnerAdapter);

        ((EditText) view.findViewById(R.id.editIsbnAddBook)).setText("9782203001206");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un livre")
                .setMessage("Veuillez saisir le code ISBN du livre")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String isbn = ""+((EditText) view.findViewById(R.id.editIsbnAddBook)).getText();
                        Collection collection = collectionList.get(spinner.getSelectedItemPosition());
                        addBook(isbn, collection);
                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void addBook(String isbn, Collection collection) {
        Intent intent = new Intent(BookListActivity.this, BookActivity.class);
        intent.putExtra(INTENT_EXTRA_ISBN, isbn);
        intent.putExtra(INTENT_EXTRA_COLLECTION, collection);
        startActivity(intent);
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