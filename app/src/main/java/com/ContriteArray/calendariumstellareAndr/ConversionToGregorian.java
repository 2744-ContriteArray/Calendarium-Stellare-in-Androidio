package com.ContriteArray.calendariumstellareAndr;

import android.content.Context;
import android.media.Image;
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
import java.util.Arrays;
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
    ImageButton backButton;

    // Results TextViews
    TextView result1;
    TextView result2;

    /*
        TODO
            [*] Check events for content input to fields
            [*] string check functions
                [*] stardate
                    [*] check validity according to format - no misplaced hex!
                    [*] throw error message if incomplete or misformatted
                [*] Gregorian
                    [*] check validity according to format
                    [*] throw error message if incomplete or misformatted
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
        result1 = (TextView) findViewById(R.id.StarResult);
        result2 = (TextView) findViewById(R.id.gregResult);

        copyStrdtButton = (ImageButton) findViewById(R.id.copyStrdtButton);
        copyGregButton = (ImageButton) findViewById(R.id.copyGregButton);
        con2Star = (ImageButton) findViewById(R.id.con2Star);
        con2Greg = (ImageButton) findViewById(R.id.con2Greg);
        backButton = (ImageButton) findViewById(R.id.backButton);

        strdtInput = (EditText) findViewById(R.id.strdtInput);
        gregInput = (EditText) findViewById(R.id.gregInput);

        // set textchange listeners
        strdtInput.addTextChangedListener(new TextWatcher()
        {
            /*
                TODO
                    [] handling for backspace
                        - Currently can't backspace over inserted format chars from onTextChanged()
                        - How do?
                    [] Test
             */
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

                if(txtLen == 7 && !(before>=count)){
                    strdtInput.setText(new StringBuilder(text).insert(text.length(), ".").toString());
                    strdtInput.setSelection(strdtInput.getText().length());
                }
            }});
        gregInput.addTextChangedListener(new TextWatcher()
        {
            /*
                TODO
                    [] handling for backspace
                        - Currently can't backspace over inserted format chars from onTextChanged()
                        - How do?
                    [] Test
             */
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
                if(txtLen == 13 || txtLen == 16){
                    gregInput.setText(new StringBuilder(text).insert(text.length(), ":").toString());
                    gregInput.setSelection(gregInput.getText().length());
                }
            }});

        pulsar = new stardate();
        Greg = LocalDateTime.of(1970,8,13,0,0,0);
    }

    private static boolean gregCheck(String date){
        int Month = 0;
        int Day = 0;
        int Hour = 0;
        int Minute = 0;
        int Sec = 0;

        /*
            TODO
                - Is it wiser to split the string for this check as in gregFormat()?
                [*] Rework to use String.split()
                    [*] Delimiter string "[/:\\s]"
                    [*] Convert to ints
                    [*] Value checks
                    [] Test
                - Could also be fruitful to use DateTimeException to do the work for us
                [] Rework to create LocalDateTime obj and try-catch for DateTimeException
                    [] try{
                    [] Delimiter string "[/:\\s]"
                    [] Convert to ints
                    [] LocalDateTime.of()
                    [] catch(DateTimeException e){
                    [] Test
         */
        String delim = "[/:\\s]";
        String[] arrayBuff = date.split(delim);
        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for(String iter: arrayBuff){
            numbers.add(Integer.parseInt(iter));
        }

        // Check the numbers
        if(numbers.get(0) > 12 || numbers.get(0) <= 0){// Month check
            System.out.println("INVALID MONTH - OUT OF BOUNDS!");
            System.out.println(("Month = "+numbers.get(0)));
            return false;
        }
        if(numbers.get(1) > 31 || numbers.get(1) <= 0){ // Day check
            System.out.println("INVALID DAY - OUT OF BOUNDS!");
            System.out.println(("Day = "+numbers.get(1)));
            return false;
        }
        int len = numbers.size();
        if(len<3){ // if only calendar date and not clock
            return true;
        }

        if(len>3 && (numbers.get(3) > 23 || numbers.get(3) < 0)){
            System.out.println("INVALID HOUR - OUT OF BOUNDS!");
            System.out.println(("Hour = "+numbers.get(3)));
            return false;
        }
        if(len>4 && (numbers.get(4) > 59 || numbers.get(4) <0)){
            System.out.println("INVALID MINUTES - OUT OF BOUNDS!");
            System.out.println(("Minutes = " + numbers.get(4)));
            return false;
        }
        if(len>5 && (numbers.get(5) > 59 || numbers.get(5) < 0)){
            System.out.println("INVALID SECONDS - OUT OF BOUNDS!");
            System.out.println(("Seconds = "+numbers.get(5)));
            return false;
        }

        /*
        for(int i = 0; i < date.length(); i++){
            int buff = (int) date.charAt(i);
            System.out.println(("buff = "+buff));
            switch(i){
                case 0:
                    Month = buff*10;

                case 1:
                    Month += buff;
                    if(Month>12 || Month <= 0){
                        System.out.println("INVALID MONTH - OUT OF BOUNDS");
                        System.out.println(("MONTH = "+Month));
                        return false;
                    }
                // Days
                case 3:
                    Day = buff*10;

                case 4:
                    Day = buff;
                    if(Day>31 || Day<=0){
                        System.out.println("INVALID DAY - OUT OF BOUNDS");
                        return false;
                    }

                // Hours
                case 11:
                    Hour = buff*10;
                case 12:
                    Hour += buff;
                    if(Hour>23 || Hour<0){
                        System.out.println("INVALID HOUR - OUT OF BOUNDS");
                        return false;
                    }
                // Minutes
                case 14:
                    Minute = buff*10;
                case 15:
                    Minute += buff;
                    if(Minute<0 || Minute>59){
                        System.out.println("INVALID MINUTES - OUT OF BOUNDS");
                        return false;
                    }
                // Seconds
                case 17:
                    Sec = buff*10;
                case 18:
                    Sec += buff;
                    if(Sec > 59){
                        System.out.println("INVALID SECONDS - OUT OF BOUNDS");
                        return false;
                    }
            }

            return true;
        }*/

        return true;
    }

    private static boolean starCheck(String date){

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
                // Check for misplaced hex
                if (i > YEARAMOUNT && letters.contains(date.charAt(i))) {
                    System.out.println("starCheck() fails");
                    System.out.println("Reason for failure: MISPLACED HEX DIGIT \n\n\n\n\n");
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

    private String strdFormat(){
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
                return buffer;
            } else {
                //System.out.println("INVALID STARDATE IN COPYSTRDTBUTTON ONCLICK");
                strdError.setVisibility(View.VISIBLE);
                return "ERROR";
                //ArrayList<String> buffError = new ArrayList<>();
                //buffError.add("ERROR");
                //return buffError;
            }
        }catch(NullPointerException e){
            System.out.println("NULLPOINTER EXCEPTION IN STRDFORMAT() - BUFFER WAS NULL \n\n\n\n");
            //ArrayList<String> buffError = new ArrayList<>();
            //buffError.add("ERROR");
            return "ERROR";
        }catch(StringIndexOutOfBoundsException I){
            System.out.println("INDEX OUT OF BOUNDS EXCEPTION IN STRDFORMAT() - BUFFER WAS EMPTY \n\n\n\n");
            //ArrayList<String> buffError = new ArrayList<>();
            //buffError.add("ERROR");
            return "ERROR";
        }
    }

    private static LocalDateTime gregFormat(String date){
        System.out.println("Now in gregFormat()");
        // create buffer for formatting later before constructing LocalDateTime obj
        ArrayList<Integer> buffer = new ArrayList<Integer>();

        // Split `date` into substrings and add to buffer
        String delim = "[/:\\s]"; // delimiter characters with which to split the string
        String[] splitted = date.split(delim);
        System.out.println(("splitted = "+ Arrays.toString(splitted)));
        for(String iter:splitted){
            System.out.println(("iter = "+iter));
            buffer.add(Integer.parseInt(iter));
        }
        try {
            // if units are missing
            int len = splitted.length;
            System.out.println(("splitted length = " + len));
            switch (len) {
                case 3: // All clock units missing
                    buffer.add(0);
                    buffer.add(0);
                    buffer.add(0);
                case 4: // Minutes and Seconds missing
                    buffer.add(0);
                    buffer.add(0);
                case 5: // Seconds missing
                    buffer.add(0);
            }
            System.out.println("intended length of ArrayList buffer is 6 indices");
            System.out.println(("Actual amount of indices: " + buffer.size() + "\n\n\n\n\n"));
            return LocalDateTime.of(buffer.get(2), buffer.get(0), buffer.get(1), buffer.get(3), buffer.get(4), buffer.get(5));
        }catch(DateTimeException e){
            System.out.println("DATE TIME EXCEPTION!");
            System.out.println(e.getMessage());
            System.out.println("\n\n\n\n");
            return LocalDateTime.of(0,0,0,0,0);
        }
    }

    @Override
    protected void onStart(){

        super.onStart();
        // On Click Events
        copyStrdtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             try{
                 String buffer = result1.getText().toString();

                 System.out.println(("buffer = "+buffer));

                 // Declare/initialize clipboard manager, ClipData, and then copy text
                 ClipboardManager clipMng = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                 ClipData paperClip = ClipData.newPlainText(buffer, buffer);
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
                    String buffer = String.valueOf(result2.getText());


                    ClipboardManager clipMng = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData paperclip = ClipData.newPlainText(buffer, buffer);

                }catch(NullPointerException e){
                    System.out.println("NULL POINTER EXCEPTION IN COPYGREGBUTTON ONCLICK");
                    return;
                }
            }
        });
        con2Star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    TODO
                        [*] Check for null string (try-catch)
                        [*] Check for validity according to format
                            [*] Test
                        If valid date:
                        [*] Pass string from gregInput to Pulsar.setGreg()
                            - setGreg() takes a LocalDateTime obj parameter
                            [*] Use string date to create equivalent LocalDateTime
                                [*] Test
                            [*] Call setter with received date
                        [*] Get pulsar.datum, stringify, and assign to strdtInput text
                 */
                try{
                    String buffer = gregInput.getText().toString();
                    System.out.println(("gregInput text = "+buffer+"\n\n\n"));
                    if(gregCheck(buffer)) {
                        LocalDateTime rightNow = gregFormat(buffer);

                        // Debug test
                        System.out.println(("Back in onclick\nYear = " + rightNow.getYear()));
                        System.out.println(("Month = " + rightNow.getMonthValue()));
                        System.out.println(("Day = " + rightNow.getDayOfMonth()));
                        System.out.println(("Hour = " + rightNow.getHour()));
                        System.out.println(("Minute = " + rightNow.getMinute()));
                        System.out.println(("Second = " + rightNow.getSecond() + "\n\n\n\n"));

                        // Set pulsar date
                        pulsar.setGreg(rightNow);
                        System.out.println("Finished called to setGreg(), moving on to building stardate string");
                        StringBuilder organize = new StringBuilder();
                        for (String iter : pulsar.getStardate()) {
                            organize.append(iter);
                        }
                        System.out.println(("Stardate string = " + organize.toString() + "\n\n\n\n"));
                        //strdtInput.setText(organize.toString());
                        result1.setText(organize.toString());
                        result2.setText(gregInput.getText().toString());
                    }else{
                        gregError.setVisibility(View.VISIBLE);
                        System.out.println("GREGCHECK() RETURNED FALSE\n\n\n");
                    }

                }catch(NullPointerException e){
                    System.out.println("NULLPOINTEREXCEPTION IN CON2STAR.ONCLICK()");
                    gregError.setVisibility(View.VISIBLE);
                    return;
                }
            }
        });
        con2Greg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String buffer = strdFormat();
                    ArrayList<String> formatted = new ArrayList<String>();

                    if(buffer.contains("ERROR")){
                        return;
                    }
                    String time = "";
                    for(int i = 0; i < buffer.length(); i++){
                        time = time.concat(String.valueOf(buffer.charAt(i)));
                        if(i==1 || i==4 || i==6 || i==7 || i==9 || i==11) {
                            formatted.add(time);
                            time = "";
                            continue;
                        }
                    }

                    pulsar.setDatum(formatted);
                    String gregNow = pulsar.getGreg().toString();
                    /*
                    TODO
                        - pulsar.getGreg().toString() returns format "1970-08-13T00:00
                        [*] Reformat constituent elements of LocalDateTime obj
                        [*] Stringify and assign to gregNow
                        [*] gregInput.setText()
                        - pulsar.getGreg() returns the gregorian zero day
                        [] (In stardate.java) finish conversion to gregorian maths
                            [] Figure out the algebra
                            [] Apply said algebra
                            [] Test it
                     */

                    System.out.println(("gregNow = "+gregNow+"\n\n\n\n\n"));

                }catch(NullPointerException e){
                    System.out.println("NULL POINTER EXCEPTION IN CON2GREG ONCLICK");
                    return;
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}