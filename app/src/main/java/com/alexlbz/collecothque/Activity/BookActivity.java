package com.alexlbz.collecothque.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexlbz.collecothque.Model.Entity.Livre;
import com.alexlbz.collecothque.Model.RequestDatabase;
import com.alexlbz.collecothque.R;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.StringTokenizer;

public class BookActivity extends AppCompatActivity {

    private TextView mTextBookName;
    private ImageView mImageBook;
    private TextView mTextBookResume;
    private TextView mTextBookInfo;

    private final static Integer LAYOUT_MAIN = 0;
    private final static Integer LAYOUT_LOAD = 1;
    private final static Integer LAYOUT_ERROR = 2;

    private RequestDatabase request;
    private Livre livre;

    private static final Integer VOLLEY_DATA_BOOK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        init();
    }

    private void init() {
        this.mTextBookName = findViewById(R.id.textBookName);
        this.mImageBook = findViewById(R.id.imageBook);
        this.mTextBookResume = findViewById(R.id.textBookResume);
        this.mTextBookInfo = findViewById(R.id.textBookInfo);

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

        if(getIntent().getStringExtra(BookListActivity.INTENT_EXTRA_ISBN) != null){
            rechercheLivre(getIntent().getStringExtra(BookListActivity.INTENT_EXTRA_ISBN));
        }
    }


    private void bookDisplay(){
        this.mTextBookName.setText(livre.getTitre());
        this.mTextBookResume.setText(livre.getResume());
        this.mTextBookInfo.setText(String.format(getString(R.string.book_info), livre.getEditeur(), livre.getDateParution(), ""+livre.getNbDePage(), livre.getIsbn()));
        layoutDisplay(LAYOUT_MAIN);
    }

    private void rechercheLivre(String isbn) {
        this.request.recupJson("https://openlibrary.org/isbn/" + isbn + ".json", VOLLEY_DATA_BOOK);
        layoutDisplay(LAYOUT_LOAD);
    }

    private void openBookNetwork(JSONObject bookData){

        try {
            String titre, editeur, resume, dataParution, image, isbn13;
            titre = editeur = resume = dataParution = image = isbn13 = null;
            Integer nbDePage = null;

            if(bookData.has("title")) {
                titre = bookData.getString("title");
            }
            if(bookData.has("")) {
                //resume = null;
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
            if(bookData.has("")) {
                //image = null;
            }
            if(bookData.has("isbn_13")) {
                isbn13 = bookData.getJSONArray("isbn_13").getString(0);
            }

            this.livre = new Livre(isbn13, titre, resume, editeur, dataParution, nbDePage, image, null, null);
            bookDisplay();
        }
        catch (Exception e){
            Log.e("ERREUR JSON", e.getMessage());
        }

    }

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
    }

    /**
     * En cas d'érreur accés réseau avec Volley
     * @param error Erreur retourné par le serveur
     * @param requestId id de la requête
     */
    private void error(VolleyError error, Integer requestId) {
        Log.e("ERREUR VOLLEY", ""+error.getMessage());
        layoutDisplay(LAYOUT_ERROR);
    }
}