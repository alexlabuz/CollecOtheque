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

import com.alexlbz.collecothque.Model.AppDatabase;
import com.alexlbz.collecothque.Model.Adapter.EtagereAdapter;
import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class ShelfActivity extends AppCompatActivity {

    private AppDatabase db;
    private Bibliotheque bibliotheque;

    public static String INTENT_EXTRA_SHELF = "INTENT_EXTRA_SHELF";

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

        this.mTextShelfTitle.setText(String.format(getString(R.string.shelf_list_title), this.bibliotheque.getName()));
        this.mBtnAddShelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicAddBtn();
            }
        });

        refrechShelfList();
    }

    private void refrechShelfList(){
        List<Etagere> etagereList = this.db.etageresDao().getByLibrary(this.bibliotheque.getId());
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
        Etagere etageres  = (Etagere) view.getTag();

        Intent intent = new Intent(ShelfActivity.this, BookListActivity.class);
        intent.putExtra(MainActivity.INTENT_EXTRA_LIBRARY, this.bibliotheque);
        intent.putExtra(ShelfActivity.INTENT_EXTRA_SHELF, (Serializable) etageres);
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
                        addShelf(""+((EditText) view.findViewById(R.id.editTextAddShelf)).getText(), Integer.parseInt(""+((EditText) view.findViewById(R.id.editColorAddShelf)).getText()));
                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void addShelf(String shelfName, Integer shelfColor) {
        if(shelfName.length() > 0 && shelfColor != null){
            this.db.etageresDao().insert(new Etagere(shelfName, shelfColor, this.bibliotheque.getId()));
            Toast.makeText(this, "L'étagère à était ajouter à " + this.bibliotheque.getName(), Toast.LENGTH_SHORT).show();
            refrechShelfList();
        }
    }

}