package com.alexlbz.collecothque.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alexlbz.collecothque.Model.AppDatabase;
import com.alexlbz.collecothque.Model.Entity.Collection;
import com.alexlbz.collecothque.Model.Entity.Etagere;
import com.alexlbz.collecothque.Model.Entity.Livre;
import com.alexlbz.collecothque.Model.RequestDatabase;
import com.alexlbz.collecothque.R;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class BookActivity extends AppCompatActivity {
    private TextView mTextBookName;
    private ImageView mImageBook;
    private Button mBtnAddBook;

    private final static Integer LAYOUT_MAIN = 0;
    private final static Integer LAYOUT_LOAD = 1;
    private final static Integer LAYOUT_ERROR = 2;

    private RequestDatabase request;
    private AppDatabase db;
    private Livre livre;
    private Etagere etagere;
    private Collection collection;

    private static final Integer VOLLEY_DATA_BOOK = 1;
    private static final Integer VOLLEY_DATA_BOOK_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        init();
    }

    private void init() {
        this.mTextBookName = findViewById(R.id.textBookName);
        this.mImageBook = findViewById(R.id.imageBook);
        this.mBtnAddBook = findViewById(R.id.btnAddBook);

        this.request = new RequestDatabase(this) {
            @Override
            public void errorRequest(VolleyError error, Integer requestId) {
                error(error, requestId);
            }

            @Override
            public void resultRequest(Object o, Integer requestId) {
                result(o, requestId);
            }
        };
        this.db = AppDatabase.getInstance(this);

        // Si un code isbn est passé dans l'intent, on recherche le livre sinon on affiche le livre déjà existany
        if(getIntent().getStringExtra(BookListActivity.INTENT_EXTRA_ISBN) != null){
            rechercheLivre(getIntent().getStringExtra(BookListActivity.INTENT_EXTRA_ISBN));
            this.etagere = (Etagere) getIntent().getSerializableExtra(ShelfActivity.INTENT_EXTRA_SHELF);
            this.collection = (Collection) getIntent().getSerializableExtra(BookListActivity.INTENT_EXTRA_COLLECTION);
        }else if(getIntent().getSerializableExtra(BookListActivity.INTENT_EXTRA_BOOK) != null){
            this.livre = (Livre) getIntent().getSerializableExtra(BookListActivity.INTENT_EXTRA_BOOK);
            this.mBtnAddBook.setVisibility(View.GONE);
            bookDisplay();
        }

        this.mBtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookLibrary();
            }
        });
    }

    /**
     * Ajoute le livre dans la bibliothèque
     */
    private void addBookLibrary() {
        this.db.livreDao().insert(this.livre);
        Toast.makeText(this, "Le livre " + this.livre.getTitre() + " à bien était ajouté", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * Actualise les données affiché à l'écran
     */
    private void bookDisplay(){
        request.recupImage("https://covers.openlibrary.org/b/id/" + livre.getImage() + ".jpg", VOLLEY_DATA_BOOK_IMAGE);
        this.mTextBookName.setText(livre.getTitre());
        ((TextView) findViewById(R.id.text_info_publisher)).setText(verifNull(livre.getEditeur()));
        ((TextView) findViewById(R.id.text_info_paruption)).setText(verifNull(livre.getDateParution()));
        ((TextView) findViewById(R.id.text_info_nb_page)).setText(verifNull(String.valueOf(livre.getNbDePage())));
        ((TextView) findViewById(R.id.text_info_isbn)).setText(verifNull(livre.getIsbn()));
        layoutDisplay(LAYOUT_MAIN);
    }

    /**
     * Si la chaine vos null retourne "Non disponible" sinon retourne la chaine inchangée
     * @param text la chaine à tester
     * @return la chaine
     */
    private String verifNull(String text){
        if(text.equals("null")){
            return "Donnée non diponible";
        }else{
            return text;
        }
    }

    /**
     * Recherche un livre sur internet selon l'isbn en paramètre
     * @param isbn isbn du livre à recherché
     */
    private void rechercheLivre(String isbn) {
        this.request.recupJson("https://openlibrary.org/isbn/" + isbn + ".json", VOLLEY_DATA_BOOK);
        layoutDisplay(LAYOUT_LOAD);
    }

    /**
     * Récupère les données du livre trouvé sur internet
     * @param bookData le JSONArray téléchargé de livre
     */
    private void openBookNetwork(JSONObject bookData){
        try {
            String titre, editeur, resume, dataParution, image, isbn13;
            titre = editeur = resume = dataParution = image = isbn13 = null;
            Integer nbDePage = null;

            if(bookData.has("title")) {
                titre = bookData.getString("title");
            }
            if(bookData.has("publishers")){
                editeur = bookData.getJSONArray("publishers").getString(0);
            }
            if(bookData.has("publish_date")) {
                dataParution = bookData.getString("publish_date");
            }
            if(bookData.has("number_of_pages")){
                nbDePage = Integer.parseInt(bookData.getString("number_of_pages"));
            }
            if(bookData.has("covers")) {
                image = bookData.getJSONArray("covers").getString(0);
            }
            if(bookData.has("isbn_13")) {
                isbn13 = bookData.getJSONArray("isbn_13").getString(0);
            }

            this.livre = new Livre(isbn13, titre, null, editeur, dataParution, nbDePage, image, this.collection.getId(), this.etagere.getId());
            bookDisplay();
        }
        catch (Exception e){
            Log.e("ERREUR JSON", e.getMessage());
        }
    }

    /**
     * Permet d'afficher le bon LinearLayout selon l'état de l'activité
     * @param numeroLayout id du layout
     */
    private void layoutDisplay(Integer numeroLayout){
        LinearLayout[] layouts = {findViewById(R.id.layoutBookMain), findViewById(R.id.layoutBookLoading), findViewById(R.id.layoutBookError)};
        for(LinearLayout l : layouts){
            l.setVisibility(View.GONE);
        }
        layouts[numeroLayout].setVisibility(View.VISIBLE);
    }

    /**
     * Récupère les données depuis internet avec Volley
     * @param o données téléchargé
     * @param requestId id de la requête
     */
    private void result(Object o, Integer requestId) {
        if(requestId.equals(VOLLEY_DATA_BOOK)){
            openBookNetwork((JSONObject) o);
        }
        if(requestId.equals(VOLLEY_DATA_BOOK_IMAGE)){
            this.mImageBook.setImageBitmap((Bitmap) o);
        }
    }

    /**
     * En cas d'érreur accés réseau avec Volley
     * @param error Erreur retourné par le serveur
     * @param requestId id de la requête
     */
    private void error(VolleyError error, Integer requestId) {
        if(requestId.equals(VOLLEY_DATA_BOOK)){
            Log.e("ERREUR VOLLEY", ""+error.getMessage());
            layoutDisplay(LAYOUT_ERROR);
        }
    }

}