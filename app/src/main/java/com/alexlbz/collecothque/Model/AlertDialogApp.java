package com.alexlbz.collecothque.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alexlbz.collecothque.Model.Entity.Bibliotheque;
import com.alexlbz.collecothque.Model.Entity.Collection;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.Model.Entity.Livre;
import com.alexlbz.collecothque.R;

import java.util.ArrayList;
import java.util.List;

public class AlertDialogApp {

    /**
     * Fonction qui gére le déplacement d'un livre dans une autre bibliothéque, étagére et collection.
     * Elle affiche un AlertDialog avec les 3 champs spinner pour la sélection du déplacement
     * @param context context de l'activité
     * @param appCompatActivity appCompact de l'activité
     * @param livre le livre à déplacer
     */
    public static void bookChangeLibraryDialog(final Context context, final AppCompatActivity appCompatActivity, final Livre livre){
        final AppDatabase database = AppDatabase.getInstance(context);
        View view = appCompatActivity.getLayoutInflater().inflate(R.layout.dialog_change_book_library, null);

        final Integer[] idEtagere = {null};
        final Integer[] idCollection = {null};

        Spinner mSpinnerLibrary = view.findViewById(R.id.spinnerChangeLibrary);
        final Spinner mSpinnerShelf = view.findViewById(R.id.spinnerChangeShelf);
        final Spinner mSpinnerCollection = view.findViewById(R.id.spinnerChangeCollection);

        // Récupère la liste de bibliothèque
        final List<Bibliotheque> bibliothequeList = database.bibliothequeDao().getAll();

        // Stocke les libelles et id des bibliothèque dans 2 listes différentes
        final ArrayList<String> nameLibraryList = new ArrayList<>();
        final ArrayList<Integer> idLibraryList = new ArrayList<>();
        for(Bibliotheque b : bibliothequeList){
            nameLibraryList.add(b.getName());
            idLibraryList.add(b.getId());
        }

        // Affiche les libelle de bibliothèque dans le spinner de bibliothéque
        ArrayAdapter<String> itemsLibrarySpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameLibraryList);
        mSpinnerLibrary.setAdapter(itemsLibrarySpinner);

        final ArrayList<Integer> idShelfList = new ArrayList<>();
        // Lors de la sélection d'une bibliothèque dans le spinner
        mSpinnerLibrary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Récupère l'id de la bibliothèque séléctionné grace à la liste d'id précédament créer
                List<Etagere> etagereList = database.etageresDao().getByLibrary(idLibraryList.get(i));

                // Stocke les libelles et id des étagère dans 2 listes différentes
                ArrayList<String> nameShelfList = new ArrayList<>();
                idShelfList.clear();
                for(Etagere e : etagereList){
                    nameShelfList.add(e.getLibelle());
                    idShelfList.add(e.getId());
                }

                // Si il n'y a pas d'étagère, on met l'id d'étagère sélectionné à null et on vide le spinner des collections
                if(etagereList.size() <= 0){
                    idEtagere[0] = null;
                    mSpinnerCollection.setAdapter(null);
                }

                // Affiche les libelle d'étagère dans le spinner d'étagère
                ArrayAdapter<String> itemsLibrarySpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameShelfList);
                mSpinnerShelf.setAdapter(itemsLibrarySpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        final ArrayList<Integer> idCollectionList = new ArrayList<>();
        // Lors de la séléction d'une étagère dans le spinner
        mSpinnerShelf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Récupère l'id de l'étagère selectionné grace à la liste d'id précédament crért
                List<Collection> collectionList = database.collectionDao().selectByEtagere(idShelfList.get(i));

                // Enregistre l'id d'étagère selectionné
                idEtagere[0] = idShelfList.get(i);

                // Stocke les libelles et id des collection dans 2 listes différentes
                ArrayList<String> nameCollectionList = new ArrayList<>();
                idCollectionList.clear();
                for(Collection c : collectionList){
                    nameCollectionList.add(c.getLibelle());
                    idCollectionList.add(c.getId());
                }

                // Si il n'y a pas de collection, on met l'id de collection sélectionné à null
                if(collectionList.size() <= 0){
                    idCollection[0] = null;
                }

                // Affiche les libelle de collection dans le spinner de collection
                ArrayAdapter<String> itemsCollectonSpinner = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, nameCollectionList);
                mSpinnerCollection.setAdapter(itemsCollectonSpinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        mSpinnerCollection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Enregistre l'id de collection selectionné grace à la liste d'id précédament créer
                idCollection[0] = idCollectionList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // Créer l'AlertDialog de déplacement de livre
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Déplacer le livre")
                .setMessage("Veuillez saisir l'endroit ou mettre le livre")
                .setView(view)
                .setPositiveButton("Déplacer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(idCollection[0] == null || idEtagere[0] == null){
                            // Si l'id d'étagère ou de collection séléctionné est introuvable
                            Toast.makeText(context, "Etagère ou collection inexistante.", Toast.LENGTH_SHORT).show();
                        }else{
                            // Déplacement du livre
                            livre.setIdCollection(idCollection[0]);
                            database.livreDao().update(livre);
                            Toast.makeText(context, "Le livre a bien était déplacé.", Toast.LENGTH_SHORT).show();
                            appCompatActivity.finish();
                        }
                    }
                })
                .setNegativeButton("Annuler", null);

        builder.create().show();
    }

}
