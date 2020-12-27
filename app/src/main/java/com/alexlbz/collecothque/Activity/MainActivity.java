package com.alexlbz.collecothque.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexlbz.collecothque.Model.AppDatabase;
import com.alexlbz.collecothque.Model.Adapter.BibliothequeAdapter;
import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Utilisateur;
import com.alexlbz.collecothque.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private Context context = this;
    private Utilisateur utilisateur;
    public static final String DATABASE_NAME = "collecotheque";
    public static final String INTENT_EXTRA_LIBRARY = "INTENT_EXTRA_ID_LIBRARY";

    private FloatingActionButton mBtnAddLibrary;
    private RecyclerView mRecyclerLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        this.mBtnAddLibrary = findViewById(R.id.btnAddLibrary);
        this.mRecyclerLibrary = findViewById(R.id.recyclerLibrary);

        this.db = AppDatabase.getInstance(this);
        this.utilisateur = new Utilisateur("AlexLbz", "alex.labuz@live.fr");

        if(db.utilisateurDao().getAll().size() <= 0){
            db.utilisateurDao().insert(this.utilisateur);
        }

        this.mBtnAddLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicAddBtn();
            }
        });

        refrechLibraryList();
    }

    private void refrechLibraryList(){
        List<Bibliotheque> bibliothequeList = this.db.bibliothequeDao().getAll();
        BibliothequeAdapter adapter = new BibliothequeAdapter(bibliothequeList) {
            @Override
            public void onClick(View view) {
                openLibrairy(view);
            }
        };
        this.mRecyclerLibrary.setAdapter(adapter);
        this.mRecyclerLibrary.setLayoutManager(new LinearLayoutManager(this));
    }

    private void openLibrairy(View view) {
        // Récupère la bibliotheque selectionné dans la liste (En tag dans le CardView)
        Bibliotheque bibliotheque = (Bibliotheque) view.getTag();

        // Et transmet la bibliotheque l'activité ShelfActivity
        Intent intent = new Intent(MainActivity.this, ShelfActivity.class);
        intent.putExtra(INTENT_EXTRA_LIBRARY, bibliotheque);
        startActivity(intent);
    }

    private void clicAddBtn() {
        final View view = getLayoutInflater().inflate(R.layout.add_library, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une bibliothèque")
                .setMessage("Veuillez saisir le nom de la nouvelle bibliothèque")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addLibrary(""+((EditText) view.findViewById(R.id.editTextAddLibrary)).getText());
                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void addLibrary(String bookName) {
        if(bookName.length() > 0){
            this.db.bibliothequeDao().insert(new Bibliotheque(bookName, this.utilisateur.getId()));
            Toast.makeText(this, "La bibliothèque à était crée", Toast.LENGTH_SHORT).show();
            refrechLibraryList();
        }
    }


}