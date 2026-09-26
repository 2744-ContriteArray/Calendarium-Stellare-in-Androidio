package com.ContriteArray.calendariumstellareAndr;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutStardates extends AppCompatActivity {

    protected TextView aboutSect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_stardates);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
/*
        // initialize textview
        aboutSect = findViewById(R.id.about_sect);

        // import txt file and parse contents
        InputStream is = getResources().openRawResource(R.raw.about_text);
        BufferedReader leitheoir = new BufferedReader(new InputStreamReader(is));
        StringBuilder teachtaireacht = new StringBuilder();
        String Piece = null;

        try{
            while ((Piece = leitheoir.readLine()) != null)
                teachtaireacht.append(Piece);
        } catch(IOException e) {
            e.printStackTrace();
        }
        Piece = teachtaireacht.toString();


        // assign contents to textview
        aboutSect.setText(Piece);*/
    }
}