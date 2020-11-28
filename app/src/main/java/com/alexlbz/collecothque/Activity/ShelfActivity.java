package com.alexlbz.collecothque.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alexlbz.collecothque.AppDatabase;
import com.alexlbz.collecothque.Model.Adapter.EtagereAdapter;
import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Etageres;
import com.alexlbz.collecothque.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class BookActivity extends AppCompatActivity {

    private AppDatabase db;
    private Bibliotheque bibliotheque;

    private static String INTENT_EXTRA_SHELF = "INTENT_EXTRA_SHELF";

    private TextView mTextShelfTitle;
    private RecyclerView mRecyclerShelf;
    private FloatingActionButton mBtnAddShelf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf);
        init();
    }

    private void init() {
        this.db = AppDatabase.getInstance(this);
        this.mTextShelfTitle = findViewById(R.id.textShelfTitle);
        this.mRecyclerShelf = findViewById(R.id.recyclerShelf);
        this.mBtnAddShelf = findViewById(R.id.btnAddShelf);

        if(getIntent() != null){
            this.bibliotheque = (Bibliotheque) getIntent().getSerializableExtra(MainActivity.INTENT_EXTRA_LIBRARY);
        }

        this.mTextShelfTitle.setText(String.format(getString(R.string.shelf_title), this.bibliotheque.getName()));
        this.mBtnAddShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicAddBtn();
            }
        });

        refrechShelfList();
    }

    private void refrechShelfList(){
        List<Etageres> etagereList = this.db.etageresDao().getByLibrary(this.bibliotheque.getId());
        EtagereAdapter adapter = new EtagereAdapter(etagereList) {
            @Override
            public void onClick(View view) {
                openShelf(view);
            }
        };
        this.mRecyclerShelf.setAdapter(adapter);
        this.mRecyclerShelf.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openShelf(View view) {
        // Récupère l'étagère selectionné dans la liste (En tag dans le CardView)
        Etageres etageres  = (Etageres) view.getTag();

        Intent intent = new Intent(BookActivity.this, CollectionActivity.class);
        intent.putExtra(MainActivity.INTENT_EXTRA_LIBRARY, this.bibliotheque);
        intent.putExtra(BookActivity.INTENT_EXTRA_SHELF, (Serializable) etageres);
        startActivity(intent);
    }

    private void clicAddBtn() {
        final View view = getLayoutInflater().inflate(R.layout.add_shelf, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une étagère")
                .setMessage("Veuillez saisir le nom de l'étagère à ajouter dans la bibliothèque")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addBook(""+((EditText) view.findViewById(R.id.editTextAddBook)).getText(), Integer.parseInt(""+((EditText) view.findViewById(R.id.editColorAddBook)).getText()));
                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void addBook(String shelfName, Integer shelfColor) {
        this.db.etageresDao().insert(new Etageres(shelfName, shelfColor, this.bibliotheque.getId()));
        Toast.makeText(this, "L'étagère à était ajouter à " + this.bibliotheque.getName(), Toast.LENGTH_SHORT).show();
        refrechShelfList();
    }

}