package com.alexlbz.collecothque.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexlbz.collecothque.Model.Adapter.EtagereAdapter;
import com.alexlbz.collecothque.Model.AppDatabase;
import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class ShelfActivity extends AppCompatActivity {

    private AppDatabase db;
    private Bibliotheque bibliotheque;
    public final Context context = this;

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
        this.mTextShelfTitle.setText(String.format(getString(R.string.shelf_list_title), this.bibliotheque.getName()));
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
        final View view = getLayoutInflater().inflate(R.layout.dialog_shelf, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter une étagère")
                .setMessage("Veuillez saisir le nom de l'étagère à ajouter dans la bibliothèque")
                .setView(view)
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addShelf(""+((EditText) view.findViewById(R.id.editTextNameShelf)).getText());
                    }
                })
                .setNegativeButton("Annuler", null)
                .create().show();
    }

    private void addShelf(String shelfName) {
        if(shelfName.length() > 0){
            this.db.etageresDao().insert(new Etagere(shelfName, this.bibliotheque.getId()));
            Toast.makeText(this, "L'étagère à était ajouter à " + this.bibliotheque.getName(), Toast.LENGTH_SHORT).show();
            refrechShelfList();
        }
    }

    /**
     * Affiche un AlertDialog permettant de modifier le nom de la bibliothéque
     */
    private void changeNameLibrary() {
        final View view = getLayoutInflater().inflate(R.layout.dialog_library, null);
        final EditText editName = view.findViewById(R.id.editTextNameLibrary);
        editName.setText(this.bibliotheque.getName());

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Changer le nom de la bibliothèque")
                .setMessage("Veuillez saisir le nouveau nom de bibliothèque ci-dessous :")
                .setView(view)
                .setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = ""+editName.getText();
                        if(name.length() > 0){
                            bibliotheque.setName(name);
                            db.bibliothequeDao().update(bibliotheque);
                            refrechShelfList();
                        }
                    }
                })
                .setNegativeButton("Retour", null)
                .create().show();
    }

    /**
     * Affiche un AlertDialog confirmant la suppression de la bibliothéque
     */
    private void deleteLibrary() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Supprimer la bibliothèque " + this.bibliotheque.getName() + " ?")
                .setMessage("Cela supprimera tout les livres, collections et étagères contenu dans cette bibliothèque !")
                .setPositiveButton("Confirmer la suppression", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.bibliothequeDao().delete(bibliotheque);
                        toastDelete();
                        finish();
                    }
                })
                .setNegativeButton("Retour", null)
                .create().show();
    }

    private void toastDelete(){
        Toast.makeText(this, getString(R.string.toast_delete_library), Toast.LENGTH_SHORT).show();
    }

    /**
     * Permet d'afficher tous les livre de la bibliothéque
     */
    private void showAllBook() {
        Intent intent = new Intent(ShelfActivity.this, BookListActivity.class);
        intent.putExtra(MainActivity.INTENT_EXTRA_LIBRARY, this.bibliotheque);
        startActivity(intent);
    }

    /**
     * Permet de faire apparaitre le menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.library_menu, menu);
        return true;
    }

    /**
     * Permet d'atribuer les items du menu aux fonction
     */
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_name_library:
                changeNameLibrary();
                break;
            case R.id.item_delete_library:
                deleteLibrary();
                break;
            case R.id.item_show_all_book:
                showAllBook();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refrechShelfList();
    }

}