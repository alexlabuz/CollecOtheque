package com.alexlbz.collecothque.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexlbz.collecothque.AppDatabase;
import com.alexlbz.collecothque.BibliothequeAdapter;
import com.alexlbz.collecothque.Model.Bibliotheque;
import com.alexlbz.collecothque.Model.Utilisateur;
import com.alexlbz.collecothque.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private Context context = this;
    private Utilisateur utilisateur;
    public static final String DATABASE_NAME = "collecotheque";

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
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
    }

    private void clicAddBtn() {
        final View view = getLayoutInflater().inflate(R.layout.add_library, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une bibliothèque")
                .setMessage("Veuillez saisir le nom de la nouvelle base de données")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addBook(""+((EditText) view.findViewById(R.id.editTextAddBook)).getText());
                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void addBook(String bookName) {
        this.db.bibliothequeDao().insert(new Bibliotheque(bookName, 1));
        Toast.makeText(this, "La bibliothèque à était crée", Toast.LENGTH_SHORT).show();
        refrechLibraryList();
    }


}