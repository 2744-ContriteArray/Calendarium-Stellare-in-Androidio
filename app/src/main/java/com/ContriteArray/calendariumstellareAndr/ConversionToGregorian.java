package com.ContriteArray.calendariumstellareAndr;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.HashSet;

import android.content.ClipboardManager;
import android.content.ClipData;

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
            [*] Check events for content input to fields
                - is it possible? do they throw events like that?
                - if so:
                [*] strdtInput
                    [*] Check character count. if == 7, append '.'
                    [*] Character count limit of 12
                [*] gregInput
                    [*] Check character counts, add '/' between units
            [] string check functions
                [] stardate
                    [] check validity according to format - no misplaced hex!
                    [*] throw error message if incomplete or misformatted
                [] Gregorian
                    [] check validity according to format
                    [] throw error message if incomplete or misformatted
            [] Functionality for copyStrdtButton
                [*] check for null string (try-catch)
                [*] copy strdtInput text onto system clipboard
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

        copyStrdtButton = (ImageButton) findViewById(R.id.copyStrdtButton);
        copyGregButton = (ImageButton) findViewById(R.id.copyGregButton);
        con2Star = (ImageButton) findViewById(R.id.con2Star);
        con2Greg = (ImageButton) findViewById(R.id.con2Greg);

        strdtInput = (EditText) findViewById(R.id.strdtInput);
        gregInput = (EditText) findViewById(R.id.gregInput);

        // set textchange listeners
        strdtInput.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = strdtInput.getText().toString();
                var txtLen = strdtInput.getText().length();

                if(txtLen == 7){
                    strdtInput.setText(new StringBuilder(text).insert(text.length(), ".").toString());
                    strdtInput.setSelection(strdtInput.getText().length());
                }
            }});
        gregInput.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = gregInput.getText().toString();
                var txtLen = gregInput.getText().length();

                // DATE STRING FORMAT: MM/DD/YYYY HH:MM:SS
                // Insert slashes
                if(txtLen == 2 || txtLen == 5){
                    gregInput.setText(new StringBuilder(text).insert(text.length(), "/").toString());
                    gregInput.setSelection(gregInput.getText().length());
                }
                // Insert space
                if(txtLen == 10){
                    gregInput.setText(new StringBuilder(text).insert(text.length(), " ").toString());
                    gregInput.setSelection(gregInput.getText().length());
                }
                // Insert colons
                if(txtLen == 12 || txtLen == 15 || txtLen == 18){
                    gregInput.setText(new StringBuilder(text).insert(text.length(), ":").toString());
                    gregInput.setSelection(gregInput.getText().length());
                }
            }});

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
        final int YEARAMOUNT = 2;
        // Current stardate years are 2 digits and won't be 3 for several years, but this constant
        // will be changed as necessary
        HashSet<Character> letters = new HashSet<Character>();
        letters.add('A');
        letters.add('B');
        letters.add('C');
        letters.add('D');
        letters.add('E');
        letters.add('F');

        try {
            for (int i = 0; i < date.length(); i++) {
                if (i > YEARAMOUNT && letters.contains(date.charAt(i))) {
                    System.out.println("starCheck() fails");
                    return false;
                }
            }

            System.out.println("starCheck() evaluates to true");
            return true;
        }catch(NullPointerException e){
            System.out.println("starCheck() EVALUATES TO FALSE - DATE THROWS NULLPOINTER \n\n\n\n");
            return false;
        }
    }

    private ArrayList<String> strdFormat(){
        // cut from onStart()
        System.out.println("ENTERED strdFormat() METHOD \n\n\n\n\n");
        final int MAXYEARDIGITS = 1; // Amount of digits in a staryear
        System.out.println(("EditText contains text: "+strdtInput.getText().toString()+"\n\n\n\n"));
        try {
            String buffer = String.valueOf(strdtInput.getText().toString());

            if (starCheck(buffer)) {
                // valid stardate, convert to gregorian
                 /*
                    TODO
                        [*] Convert buffer to ArrayList<String>
                            - iterate through buffer, using iter numbers to determine units
                            [*] Test and debug
                        [] Run checks for improper use of hexadecimals
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
            } else {
                //System.out.println("INVALID STARDATE IN COPYSTRDTBUTTON ONCLICK");
                strdError.setVisibility(View.VISIBLE);
                ArrayList<String> buffError = new ArrayList<>();
                buffError.add("ERROR");
                return buffError;
            }
        }catch(NullPointerException e){
            System.out.println("NULLPOINTER EXCEPTION IN STRDFORMAT() - BUFFER WAS NULL \n\n\n\n");
            ArrayList<String> buffError = new ArrayList<>();
            buffError.add("ERROR");
            return buffError;
        }catch(StringIndexOutOfBoundsException I){
            System.out.println("INDEX OUT OF BOUNDS EXCEPTION IN STRDFORMAT() - BUFFER WAS EMPTY \n\n\n\n");
            ArrayList<String> buffError = new ArrayList<>();
            buffError.add("ERROR");
            return buffError;
        }
    }

    private static LocalDateTime gregFormat(){
        return LocalDateTime.of(1970,8,13,0,0,0);
    }

    @Override
    protected void onStart(){

        super.onStart();
        // On Click Events
        copyStrdtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try{
                 // set datum in pulsar and set gregorian date to equivalent
                 ArrayList<String> buffer = strdFormat();

                 if(buffer.contains("ERROR")){
                     strdError.setVisibility(View.VISIBLE);
                     return;
                 }

                 // Get stardate from buffer and cast
                 CharSequence StarChar = (CharSequence) buffer.toString();
                 // Declare/initialize clipboard manager, ClipData, and then copy text
                 ClipboardManager clipMng = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                 ClipData paperClip = ClipData.newPlainText(StarChar.toString(), StarChar);
                 clipMng.setPrimaryClip(paperClip);

             }catch(NullPointerException e){
                 System.out.println("NULL POINTER EXCEPTION IN COPYSTRDTBUTTON ONCLICK");
                 strdError.setVisibility(View.VISIBLE);
                 return;
             }
            }
        });
        copyGregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String buffer = String.valueOf(gregInput.getText());


                    ClipboardManager clipMng = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData paperclip = ClipData.newPlainText("fuck", "fuck");

                }catch(NullPointerException e){
                    System.out.println("NULL POINTER EXCEPTION IN COPYGREGBUTTON ONCLICK");
                    return;
                }
            }
        });
        con2Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        con2Greg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    ArrayList<String> buffer = strdFormat();

                    if(buffer.contains("ERROR")){
                        return;
                    }

                    pulsar.setDatum(buffer);
                }catch(NullPointerException e){
                    System.out.println("NULL POINTER EXCEPTION IN CON2GREG ONCLICK");
                    return;
                }
            }
        });
    }
}