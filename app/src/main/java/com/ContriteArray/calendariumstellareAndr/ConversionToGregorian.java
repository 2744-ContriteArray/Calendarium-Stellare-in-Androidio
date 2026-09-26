package com.ContriteArray.calendariumstellareAndr;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.time.*;

public class ConversionToGregorian extends AppCompatActivity {

    //private final String DEFAULT_STARDATE = "0000000.0000";
    //private final LocalDateTime DEFAULT_GREG = LocalDateTime.of(1970,8,13,0,0,0);

    protected stardate pulsar; // A stardate object for use in all calculations herein
    protected LocalDateTime Greg; // A LocalDateTime object for use in all calculations herein

    // Declare text input fields for activity
    EditText strdtInput;
    EditText gregInput;

    // Declare buttons
    ImageButton copyStrdtButton;
    ImageButton copyGregButton;
    ImageButton con2Star;
    ImageButton con2Greg;

    /*
        TODO
            [] Check events for content input to fields
                - is it possible? do they throw events like that?
                - if so:
                [] strdtInput
                    [] Check character count. if == 7, append '.'
                    [] Character count limit of 12
                [] gregInput
                    [] Check character counts, add '/' between units
            [] string check functions
                [] stardate
                    [] check validity according to format
                    [] throw error message if incomplete or misformatted
                [] Gregorian
                    [] check validity according to format
                    [] throw error message if incomplete or misformatted
            [] Functionality for copyStrdtButton
                [*] check for null string (try-catch)
                [] copy strdtInput text onto system clipboard
            [] Functionality for copyGregButton
                [*] check for null string (try-catch)
                [] copy date text onto system clipboard
            [] Functionality for con2Star
                [] Check for null string (try-catch)
                [] Check for validity according to format
                If valid stardate:
                [] Assign string from strdtInput to Pulsar.datum
                [] Get todayGreg and assign to gregInput text
            [] Functionality for con2Greg
                [] Check for null string (try-catch)
                [] Check for validity according to format
                if valid date:
                [] Assign string from gregInput to Pulsar.todayGreg
                [] Get stardate and assign to strdtInput text
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_conversion_to_gregorian);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pulsar = new stardate();
        Greg = LocalDateTime.of(1970,8,13,0,0,0);
    }

    private static boolean starCheck(String date){
        /*
            TODO
                [] check validity of input according to format
                [] if valid, return true
                [] else throw error message, return false
         */
        return true;
    }

    private static boolean gregCheck(String date){
        return true;
    }

    @Override
    protected void onStart(){

        super.onStart();



        // On Click Events
        this.copyStrdtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try{
                 String buffer = String.valueOf(strdtInput.getText());

             }catch(NullPointerException e){
                 System.out.println("NULL POINTER EXCEPTION IN COPYSTRDTBUTTON ONCLICK");
                 return;
             }
            }
        });
        this.copyGregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String buffer = String.valueOf(gregInput.getText());

                }catch(NullPointerException e){
                    System.out.println("NULL POINTER EXCEPTION IN COPYGREGBUTTON ONCLICK");
                    return;
                }
            }
        });
        this.con2Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        this.con2Greg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}