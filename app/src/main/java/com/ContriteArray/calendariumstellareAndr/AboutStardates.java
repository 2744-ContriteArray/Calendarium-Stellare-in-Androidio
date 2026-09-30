package com.ContriteArray.calendariumstellareAndr;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Scroller;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutStardates extends AppCompatActivity {

    protected ImageView aboutSect;
    protected ImageButton textSwitcher;
    protected ScrollView scrollingThing;

    protected boolean aboutSys = true; // Are we showing the about text and not the formulae?

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
        // initialize textview
        aboutSect = (ImageView) findViewById(R.id.about_sect);
        textSwitcher = (ImageButton) findViewById(R.id.textSwitcher);
        scrollingThing = (ScrollView) findViewById(R.id.scrollingThing);
    }

    @Override
    protected void onStart(){
        super.onStart();

        textSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollingThing.scrollTo(0,0);

                if(aboutSys){
                    int formuID = getResources().getIdentifier("about_formulae_imagery", "drawable", getPackageName());
                    aboutSect.setImageResource(formuID);
                    aboutSys = false;
                }
                else{
                    int formuID = getResources().getIdentifier("about_text_imager", "drawable", getPackageName());
                    aboutSect.setImageResource(formuID);
                    aboutSys = true;
                }
            }
        });

    }
}