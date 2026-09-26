package com.ContriteArray.calendariumstellareAndr;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.time.*;
import java.util.ArrayList;

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

    // Declare error message TextViews
    TextView strdError;
    TextView gregError;

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

        strdError = (TextView) findViewById(R.id.strdError);
        strdError.setVisibility(View.INVISIBLE);
        gregError = (TextView) findViewById(R.id.gregError);
        gregError.setVisibility(View.INVISIBLE);

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

        final int CHARAMOUNT = 12; // proper character amount in stardate format
        // Current stardate years are 2 digits and won't be 3 for several years, but this constant
        // will be changed as necessary

        return false;
    }

    private ArrayList<String> strdFormat(){
        // cut from onStart()
        final int MAXYEARDIGITS = 1; // Amount of digits in a staryear
        String buffer = String.valueOf(strdtInput.getText());
        if(starCheck(buffer)) {
            // valid stardate, convert to gregorian
                 /*
                    TODO
                        [] Convert buffer to ArrayList<String>
                            - iterate through buffer, using iter numbers to determine units
                            [] Test and debug
                  */
            ArrayList<String> bufferList = new ArrayList<>();
            String year = "";
            String day = "";
            String hour = "";
            String minute = "";
            String second = "";
            // assemble buffer into arraylist (bufferList)
            {
                for (int i = 0; i < 9; i++) {
                    if (i < MAXYEARDIGITS) {
                        year += buffer.charAt(i);
                        continue;
                    }
                    bufferList.add(year);

                    if (i < 5) {
                        day += buffer.charAt(i);
                        continue;
                    }
                    bufferList.add(day);

                    if (i < 7) {
                        hour += buffer.charAt(i);
                        continue;
                    }
                    bufferList.add(hour);
                }

                bufferList.add(String.valueOf(buffer.charAt(7))); // add '.'

                for (int i = 8; i < buffer.length(); i++) {
                    if (i < 10) {
                        minute += buffer.charAt(i);
                        continue;
                    }
                    bufferList.add(minute);

                    if (i == 10) {
                        second += buffer.charAt(i);
                        continue;
                    } else {
                        second += buffer.charAt(i);
                        bufferList.add(second);
                    }
                }
            }
            return bufferList;
        }
        else{
            System.out.println("INVALID STARDATE IN COPYSTRDTBUTTON ONCLICK");
            strdError.setVisibility(View.VISIBLE);
            ArrayList<String> buffError = new ArrayList<>();
            buffError.add("ERROR");
            return buffError;
        }
    }

    private static boolean gregCheck(String date){
        return false;
    }

    @Override
    protected void onStart(){

        super.onStart();



        // On Click Events
        this.copyStrdtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try{
                 // set datum in pulsar and set gregorian date to equivalent
                 pulsar.setDatum(strdFormat());

                 // Get gregorian date from pulsar
                 // LocalDateTime tempGreg =
                 // Apply to gregInput text
                 //gregInput.setText();
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
                try{
                    final int MAXYEARDIGITS = 1; // Amount of digits in a staryear
                    String buffer = String.valueOf(strdtInput.getText());
                    if(starCheck(buffer)) {
                        // valid stardate, convert to gregorian
                 /*
                    TODO
                        [] Convert buffer to ArrayList<String>
                            - iterate through buffer, using iter numbers to determine units
                            [] Test and debug
                  */
                        ArrayList<String> bufferList = new ArrayList<>();
                        String year = "";
                        String day = "";
                        String hour = "";
                        String minute = "";
                        String second = "";
                        // assemble buffer into arraylist (bufferList)
                        {
                            for (int i = 0; i < 9; i++) {
                                if (i < MAXYEARDIGITS) {
                                    year += buffer.charAt(i);
                                    continue;
                                }
                                bufferList.add(year);

                                if (i < 5) {
                                    day += buffer.charAt(i);
                                    continue;
                                }
                                bufferList.add(day);

                                if (i < 7) {
                                    hour += buffer.charAt(i);
                                    continue;
                                }
                                bufferList.add(hour);
                            }

                            bufferList.add(String.valueOf(buffer.charAt(7))); // add '.'

                            for (int i = 8; i < buffer.length(); i++) {
                                if (i < 10) {
                                    minute += buffer.charAt(i);
                                    continue;
                                }
                                bufferList.add(minute);

                                if (i == 10) {
                                    second += buffer.charAt(i);
                                    continue;
                                } else {
                                    second += buffer.charAt(i);
                                    bufferList.add(second);
                                }
                            }
                        }
                        // set datum in pulsar and set gregorian date to equivalent
                        pulsar.setDatum(bufferList);

                        // Get gregorian date from pulsar
                        // LocalDateTime tempGreg =
                        // Apply to gregInput text
                        //gregInput.setText();
                    }
                    else{
                        System.out.println("INVALID STARDATE IN COPYSTRDTBUTTON ONCLICK");
                        strdError.setVisibility(View.VISIBLE);
                        return;
                    }
                }catch(NullPointerException e){
                    System.out.println("NULL POINTER EXCEPTION IN CON2GREG ONCLICK");
                    return;
                }
            }
        });
    }
}