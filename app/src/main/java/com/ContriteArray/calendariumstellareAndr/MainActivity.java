package com.ContriteArray.calendariumstellareAndr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.badge.BadgeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public static TextView debugHermes;
    Button refresher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        refresher = (Button) findViewById(R.id.refresher);


    }

    public static void debugPrintln(String message) {
        if(Objects.equals(message, "/clear")){
            debugHermes.setText(" ");
        }
        else {
            debugHermes.append((LocalDateTime.now().getHour() + ":" +
                    LocalDateTime.now().getMinute() + " " + message + "\n"));
//        debugHermes.setText(message);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        TextView Today_Is = (TextView) findViewById(R.id.Today_is);
        TextView StarNow = (TextView) findViewById(R.id.NowDate);
        debugHermes = (TextView) findViewById(R.id.debugHermes);
        debugHermes.setText("");

        stardate star_today = new stardate();
        // get stardate
        ArrayList<String> unformatTime = star_today.calcStardate();

        // format the stardate into a string
        String Stime = "";

        for(String digit: unformatTime)
        {
            Stime = Stime.concat(digit);
        }

        StarNow.setText(Stime);

        refresher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stardate theDate = new stardate();
                ArrayList<String> rawTime = theDate.calcStardate();

                String Stime = "";

                for(String digit: rawTime){
                    Stime = Stime.concat(digit);
                }

                StarNow.setText(Stime);
            }
        });
    }

}