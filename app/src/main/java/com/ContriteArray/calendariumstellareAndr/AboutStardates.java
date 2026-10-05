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
    protected ImageButton BackB;

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
        BackB = (ImageButton) findViewById(R.id.BackB);
    }

    @Override
    protected void onStart(){
        super.onStart();

        BackB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //scrollingThing.scrollTo(0,0);
                scrollingThing.fullScroll(View.FOCUS_UP);

                System.out.println(("aboutSys = "+aboutSys));
                if(aboutSys){
                    int formuID = getResources().getIdentifier("about_formulae_imagery", "mipmap", getPackageName());
                    System.out.println(("formuID = "+formuID));
                    aboutSect.setImageResource(formuID);
                    aboutSys = false;
                }
                else{
                    int formuID = getResources().getIdentifier("about_text_imagery", "mipmap", getPackageName());
                    System.out.println(("formuID = "+formuID));
                    aboutSect.setImageResource(formuID);
                    aboutSys = true;
                }
            }
        });

    }
}