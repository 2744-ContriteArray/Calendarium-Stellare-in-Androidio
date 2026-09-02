package com.ContriteArray.calendariumstellareAndr;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private stardate Star_today;
    public static TextView debugHermes;

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




    }

    public static void debugPrintln(String message) {
        if(message=="/clear"){
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

        Star_today = new stardate();
        // get stardate
        ArrayList<String> unformatTime = Star_today.calcStardate();

        // format the stardate into a string
        String Stime = "";

        for(String digit: unformatTime)
        {
            Stime = Stime.concat(digit);
        }

        StarNow.setText(Stime);
    }

}