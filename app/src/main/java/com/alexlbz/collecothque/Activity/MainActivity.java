package com.alexlbz.collecothque.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alexlbz.collecothque.Model.AppDatabase;
import com.alexlbz.collecothque.Model.Utilisateur;
import com.alexlbz.collecothque.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    public static final String DATABASE_NAME = "collecotheque";

    private EditText editAddUserName;
    private Button btnAddUser;
    private LinearLayout layoutListUserHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // Database initialisation
        //this.db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();

        this.db = AppDatabase.getInstance(this);

        // View element
        this.editAddUserName = findViewById(R.id.editAddUserName);
        this.btnAddUser = findViewById(R.id.btnAddUser);
        this.layoutListUserHome = findViewById(R.id.layoutListUserHome);

        this.btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicBtnAddUser();
            }
        });

        displayListUsers();
    }

    private void clicBtnAddUser() {
        db.utilisateurDao().insert(new Utilisateur(""+this.editAddUserName.getText(), null));
        displayListUsers();
    }

    @SuppressLint("SetTextI18n")
    private void displayListUsers(){
        this.layoutListUserHome.removeAllViews();
        List<Utilisateur> utilisateurList = db.utilisateurDao().getAll();
        for(Utilisateur u : utilisateurList){
            TextView textView = new TextView(this);
            textView.setText(u.getId() + " | " + u.getNom());
            this.layoutListUserHome.addView(textView);
        }
    }

}