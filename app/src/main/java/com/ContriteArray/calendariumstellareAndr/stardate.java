package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.time.*;
import java.util.Objects;

public class stardate
{
    private ArrayList<String> datum;
    private LocalDateTime todayGreg;

    // ZERO DAYS
    private final LocalDateTime zeroDayGreg =
            LocalDateTime.of(1970, 8, 13, 0, 0, 0);
    private final LocalDateTime zeroDayJules =
            LocalDateTime.of(1970, 7, 31, 0, 0, 0);
    private Period between;

    // Setter for datum
    public void setDatum(ArrayList<String> when)
    {
        //MainActivity.debugPrintln(("In setDatum - param \"when\" = "+when));
        try {
            this.datum.clear();
            this.datum.addAll(when);
            this.todayGreg = this.conv2Greg();
        }catch (IndexOutOfBoundsException e)
        {
            System.out.println(("ERROR IN setDatum(): "+e.getMessage()));
            System.out.println(("STACK TRACE: "+e.getCause()));
//            throw new RuntimeException(e);
        }
    }

    public LocalDateTime getGreg()
    {
        return this.todayGreg;
    }

    public void setGreg(LocalDateTime when)
    {
        this.todayGreg = when;
        this.setDatum(this.calcStardate(false));// Adjust stardate held in this.datum
    }

    public ArrayList<String> getStardate(){
        return this.datum;
    }

            // CALCULATION STEPS

    // VARIABLES
    long[] SF = {60, 3600, 108000, 38880000}; // Unit factors for stardates
    long[] GF = {60, 3600, 86400, 31557600}; // Unit factors for Gregorian units

    private String charDelete(String thing){
        StringBuilder tinker = new StringBuilder();
        tinker.append(thing);// Load parseable into tinker
        tinker.deleteCharAt(0);// Remove " "
        thing = tinker.toString();// Assign refined string to parseable

        System.out.println(("Finished loop to remove \"\". parseable = "+ thing +"\n\n"));

        return thing;
    }

    // METHODS/FUNCTIONS
    private static String decToHex(int dec)// Convert decimal integers to hexadecimal strings
    {
        //MainActivity.debugPrintln("Now in decToHex()");
        String hexa = "";
        ArrayList<String> buff = new ArrayList<>();

        /*if(buff.isEmpty()){
            MainActivity.debugPrintln("buff was just constructed and found empty");
        }
        else{
            MainActivity.debugPrintln("buff was just constructed and found not empty");
            MainActivity.debugPrintln(("buff is of size "+buff.size()));
        }*/
        String[] numLett = new String[] {"A", "B", "C", "D", "E", "F"};
        //MainActivity.debugPrintln(("param dec evaluates to "+dec));

        // math time
        int iter = 0;
        while(dec > 0)
        {
            iter++;
            //MainActivity.debugPrintln(("WHILE LOOP ITERATION #"+iter));
            // if the remainder > 9, it's a letter not 10-16
            if((dec%16) >= 10)// && ((dec%16) <= 15))
            {
                buff.add(numLett[(dec%16)-10]);
            }
            else
            {
                // remainder <= 9
                buff.add(String.valueOf(dec%16));
            }
            //MainActivity.debugPrintln(("dec%16 = "+dec%16));
            //MainActivity.debugPrintln(("buff = "+buff));

            // amend dec to reflect dec/16 without remainder as floats
            //MainActivity.debugPrintln(("Math.floorDiv(dec, 16) = "+Math.floorDiv(dec,16)));
            dec = Math.floorDiv(dec, 16);
        }
        /*MainActivity.debugPrintln("/clear");
        MainActivity.debugPrintln("ABOUT TO ENTER FOR LOOP");
        MainActivity.debugPrintln(("buff.size() returns "+buff.size()));*/
        // assemble the collected remainders into the hex number
        for(int i = buff.size()-1; i >= 0; i--)
        {
            hexa = hexa.concat(buff.get(i));
        }
//        MainActivity.debugPrintln("EXITED FOR LOOP");
//        MainActivity.debugPrintln(("hexa = "+hexa));

        return hexa;
    }

    // Convert hexadecimal strings into decimal integers
    private int hexToDec(String hexaD)
    {
        // PROCESS
        // Multiply each digit of hex number by 16 raised to the power of its position
        //      starting from 0
        // Add up the results of these operations
        // ex. A.4 = (A*16^0) + (4*16^-1) = (10*1) + (4*0.0625) = 10.25

        // create the dictionary for letter digits
        // .put() operations braced for easy organizing and collapsibility
        int power = hexaD.length()-1;
        System.out.println(("hexaD is supposed equal 2D and thus 2 chars.\nActual length: "+hexaD.length()));
        System.out.println(("power (hexaD.lenght()-1) = "+power));
        int dec = 0;
        /*
            TODO
                - Turn hexaD into a String[]
                - Use .split(""), it yields indices of " " and the char
                - Remove the " " in each index. how do?
                - SUPPOSED TO BE RETURNING 45. MOST RECENT TEST RETURNS 34
         */

        String[] parseable = hexaD.split("");
        System.out.println(("parseable declared and set. Value therein = "+ Arrays.toString(parseable)));
        System.out.println("ENTERING LOOP");
        for(int i = 0; i < parseable.length; i++){
            if(parseable[i].contains(" ")){
                parseable[i] = charDelete(parseable[i]);
            }
        }

        System.out.println(("ENTERING PARSE INTEGERS LOOP"));
        for(int i=0; i<parseable.length; i++)
        {
            int digit=0;
            System.out.println(("Iterator i = "+i));
            // handle the letters and cast from String to Integer
            /*if(Letters.containsKey(String.valueOf(parseable[i])))
            {
                try {
                    digit = Letters.get(parseable[i]);// Throws null
                }
                catch (NullPointerException e) {
                    System.out.println(("ERROR: "+e.getMessage()));
                    digit = 0;
                    //throw new RuntimeException(e);
                    System.out.println(e.getCause());
                }
            }
            else
            {
                try{
                    // INVALID MEANS OF EXTRACTING INTEGER, THROWS NULL EVERY TIME
                    digit = Integer.getInteger(parseable[i]);
                }
                catch (NullPointerException e){
                    digit = 0;
                    System.out.println("ERROR: NULLPOINTER AT LINE 154");
                    System.out.println(("Variable hexaD = "+hexaD));
                    System.out.println(("i = "+i));
                }
            }*/

            try {
                switch (parseable[i]) {
                    case "A":
                        digit = 10;
                    case "B":
                        digit = 11;
                    case "C":
                        digit = 12;
                    case "D":
                        digit = 13;
                    case "E":
                        digit = 14;
                    case "F":
                        digit = 15;
                    default:
                        digit = Integer.parseInt(parseable[i]);
                }
            }catch(Exception e){
                System.out.println("ERROR IN SWITCH STATEMENT IN HEXTODEC()");
                System.out.println(("i = "+i));
                System.out.println(e.getCause());
            }
            System.out.println("Finished number extraction conditionals");
            System.out.println(("variable int digit = "+digit));

            // Convert the digit to decimal and add it to the final int
            dec += (int) (digit*(Math.pow(16, power)));
            power -= 1;
        }
        System.out.println("LOOP FINISHED IN HEXTODEC()\n\n\n");
        System.out.println(("Return value = "+dec));
        return dec;
    }

    // Implement Euclidean division
    private int[] euclidDiv(long a, long b){
        int r = (int) a;
        int q = 0;

        while(r >= b){
            r = r - (int) b;
            q++;
        }

        return new int[]{q, r};
    }


        // CALCULATING S_t
    // Recursive function to contain steps for deriving S_t from stardate as part of conv2Greg()
    // Takes an array (S_n) containing the compiled integers of the stardate, sans formatting
    private long conv2Gstep(long[] S_n, int iter){
        System.out.println(("conv2Gstep() iteration "+iter));

        if(iter >= SF.length || iter >= S_n.length){
            // If the iterator exceeds the amount of indices, then we've reached the end and
                // don't want to throw an out of range error! Time to unwind the recursion!
            System.out.println("RECURSION COMPLETE, RETURNING AND UNWINDING");
            return 0;
        }
        System.out.println(("S_n[iter] = "+S_n[iter]));
        System.out.println(("SF[iter] = "+SF[iter]));
        long product = S_n[iter] * SF[iter];
        System.out.println(("Product = "+product));

        return product+conv2Gstep(S_n, iter+1);
    }

    private long conv2Gstep(int[] S_n, int iter){
        // Polymorphic variant of above. Change in parameter types
        System.out.println(("conv2Gstep() iteration "+iter));
        if(iter >= SF.length || iter >= S_n.length){
            // If the iterator exceeds the amount of indices, then we've reached the end and
            // don't want to throw an out of range error! Time to unwind the recursion!
            System.out.println("RECURSION COMPLETE, RETURNING AND UNWINDING\n\n\n");
            return 0;
        }
        System.out.println(("S_n[iter] = "+S_n[iter]));
        System.out.println(("SF[iter] = "+SF[iter]));
        long product = S_n[iter] * SF[iter];
        System.out.println(("Product = "+product));

        return product+conv2Gstep(S_n, iter+1);
    }

    // Formatting the stardate strings
    private String timeForm(int index, int time) {
        String finish = "";

        if (index == 0) {
            if (time < 10) {
                finish = "00" + String.valueOf(time);
            } else if (time <= 99) {
                finish = "0" + String.valueOf(time);
            } else {
                finish = String.valueOf(time);
            }
        }else{
            if(time<10){
                finish = "0"+String.valueOf(time);
            }else{
                finish = String.valueOf(time);
            }
        }
        return finish;
    }

    // Format the Stardate strings
    private String timeForm(int time){
        String finish;
        if(time<10){
            finish = "0"+String.valueOf(time);
        }else{
            finish = String.valueOf(time);
        }

        return finish;
    }

    // Deformat the stardate from strings to ints
    private int[] Deformat(){
        int[] results = new int[5];
        results[0] = hexToDec(this.datum.get(0));
        System.out.println(("hexToDec() returns "+results[0]));
        //System.out.println(("year rendered to decimal = "+StellarYear));

        String[] parseable = new String[7];
        this.datum.toArray(parseable);

        for(int i = 1; i <= results.length; i++){
            if(Objects.equals(parseable[i], ".")){
                continue;
            }
            System.out.println("IN DEFORMAT()");

            /*
            TODO
                - ADD STEPS TO REMOVE PADDED ZEROES FROM STRINGS
                - ADD CHECKS FOR MULTIPLE PADDING DIGITS
                - ADD HANDLING FOR STRINGS THAT ARE AAAALL ZEROES. LIKE 00 SECONDS
             */

            for(int j = 0; j < 2; j++) {
                if(this.datum.get(i).equals("00")){
                    break;
                }else if (this.datum.get(i).startsWith("0")) {
                    parseable[i] = charDelete(this.datum.get(i));
                }else{
                    System.out.println(("String received = "+this.datum.get(i)));
                    //System.out.println(("Integer.parseInt(this.datum.get("+i+") = "
                    //        +Integer.parseInt(this.datum.get(i)+"\n\n")));
                    break;
                }
            }


            if(i >= 4 && !this.datum.get(i).equals("00")){
                results[i-1] = Integer.parseInt(this.datum.get(i));
            }else if(i >= 4) {
                results[i-1] = 0;
            }else{
                results[i] = Integer.parseInt(this.datum.get(i));
            }
        }

        System.out.println(("ABOUT TO RETURN TO CONV2GREG()\nresults = "+results.toString()));

        return results;
    }

    // Initial iteration of euc2Jules
    private int[] euc2JulesREC(long S_t){
        //int[] euclid = euclidDiv(S_t, GF[0]);
        int[] jules = new int[6];
        // Euclidean division
        //jules[0] = euclid[0];

        return euc2JulesREC(S_t, jules, 0);
    }

    // Recursive method for steps to calculate Julian units from S_t
    private int[] euc2JulesREC(long S_t, int[] jules, int iter){
        /*
        TODO
            - Reorganize to increment DOWNWARD from jules.length-1
            - Year unit factor is GF[3], not GF[0]
         */
        if(iter == jules.length-1){
            // Prevent out of range error
            // Assign Seconds to jules and return
            // Recursion is done, time to unwind!
            jules[iter] = (int) S_t;
            return jules;
        }
        /*
        STEPS
            - send dividends to euclidDiv()
            - get back the quotient and remainder
            - assign quotient to jules[iter]
            - send remainder to next iter
         */
        // Send dividends to euclidDiv() and get quotient and remainder back
        int[] euclid = euclidDiv(S_t, GF[iter]);
        // Assign quotient to jules
        jules[iter] = euclid[0];
        // Send remainder to next iter
        return euc2JulesREC(euclid[1], jules, iter+1);
    }

    // CALCULATING DATES FROM S_t
    // Recursive method for the steps to calculate stardate from S_t
    private int[] euc2StarREC(long S_t, int[] stellaris, int iter){
        if(iter == stellaris.length-1){
            // Prevent out of range error
            // Assign seconds to stellaris and return
            // Recursion is done, time to unwind!
            stellaris[iter] = (int) S_t;
            return stellaris;
        }

        /*
        STEPS
            - Send dividends to euclidDiv()
            - Get back the quotient and remainder
            - Assign quotient to stellaris[iter]
            - Send remainder to next iter
         */
        // Send dividends to euclidDiv() and get results
        int[] euclid = euclidDiv(S_t, SF[iter]);
        // Assign quotient to stellaris
        stellaris[iter] = euclid[0];
        // Send remainder to next iter
        return euc2StarREC(euclid[1], stellaris, iter+1);
    }

        //  CONVERSION METHODS
    public LocalDateTime conv2Greg(){
        LocalDateTime result; //final result
        int month = 1; //int for the month to be assigned to result
        int day; //int for the day to be assigned to result
        int dayOfMonth = 1; // calendar day
        long S_t = 0;

        /*
        Reverse calcStardate()

        TODO
            [*] Get Stellar Units from this.datum
                [*] Year
                [*] Convert to Decimal
            [] Get S_t by calling conv2Gstep()
                [*] Created long[] obj from deformatted stardates
                [*] Make initial recursion call
            [] Calculate Julian date via division and modulo
            [] Test
                [] Debug printouts
                [] Reevaluate maths
                    - S_t isn't being calculated correctly
                    - Comes out to 505,440,000 when it should be 1,767,278,640
                    - Possibly need to do reverse calculations from seconds all the way up to years, adding results together
                [] Make adjustments where needed
                    - Do we need a method for reversing euclidDiv()?
                    - Are we doing the math correctly?
                - For debug test, use stardate 2D16320.4400, it should evaluate to 8/13/2026 @ 14:44:00
                    - S_t calculates to 1,827,360,000 (Should be 1,767,278,640)
                    - Year in decimal evaluates to 47, (SHOULD BE 45!!)
                    - Gregorian date evaluates to feb 1st (day 330), 57CE (???)
                    - Doesn't assign text to TextView?
         */
        // S_t calculations
        int[] stellaris = Deformat(); // Will hold unformatted stardate integers
        S_t = conv2Gstep(stellaris, 0);
        System.out.println("FINISHED UNWINDING");
        System.out.println(("S_t = "+S_t));

        // CALCULATIONS
        System.out.println("ENTERING JULIAN CALCULATIONS");
        int[] dates = euc2JulesREC(S_t);
        System.out.println(("int[] dates = "+Arrays.toString(dates)));
        /*
            // YEAR
        euclid = euclidDiv(S_t, F_y);
        year = euclid[0];
        remains = euclid[1];
        System.out.println(("year = "+year));
        System.out.println(("remains = "+remains+"\n"));
            // DAY OF YEAR (NOT OF MONTH)
        euclid = euclidDiv(remains, F_d);
        day = euclid[0];
        remains = euclid[1];
        System.out.println(("day = "+day));
        System.out.println(("remains = "+remains+"\n"));
            // HOUR
        euclid = euclidDiv(remains, F_h);
        hour = euclid[0];
        remains = euclid[1];
        System.out.println(("hour = "+hour));
        System.out.println(("remains = "+remains+"\n"));
            // MINUTE
        euclid = euclidDiv(remains, F_m);
        minute = euclid[0];
        remains = euclid[1];
        System.out.println(("minute = "+minute));
        System.out.println(("remains = "+remains+"\n"));
            // SECOND
        second = remains;
        System.out.println(("second = "+second+"\n\n\n"));
        // No remainder needed for seconds
        */

        // Convert to gregorian
        dates[1] += 13;
        if(dates[1] > 365){
            dates[1] =- 365;
            dates[0]++;
        }

        // MONTH CALCULATION
        int[] MonthLen = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(dates[0]%4==0){
            MonthLen[1] = 29;
        }
        int DayBuff = dates[1];
            // MONTH
        for(int i=0; i<12; i++){
            if(DayBuff <=31){
                month = i+1;
                if(DayBuff <=0){
                    DayBuff += MonthLen[i-1];
                }
                dayOfMonth = DayBuff;
                break;
            }else{
                DayBuff =- MonthLen[i];
            }
        }
        if(dayOfMonth <= 0){
            dayOfMonth = 1;
        }


        return LocalDateTime.of(dates[0],month,dayOfMonth,dates[2],dates[3],dates[4]);
    }

    @Deprecated
    public ArrayList<String> calcStardateDEPR()
    {
        ArrayList<String> when = new ArrayList<>();

        LocalDateTime rightFuckinNow;

        if(true) {
            // Get right fucking now and convert to Julian
            rightFuckinNow = LocalDateTime.now().minusDays((long) 13.0075);
            this.todayGreg = rightFuckinNow;
        }
        else{
            rightFuckinNow = this.todayGreg.minusDays((long) 13.0075);
        }
        // Get duration difference between zero day and rn
        Duration delta = Duration.between(zeroDayJules, rightFuckinNow);

// CALCULATIONS
        // Calculate Julian years
        int Y = (int) (Math.floorDiv((int) delta.toDays(), (int) 365.25));

        int H = (int) Math.floor(delta.toHours());

        // Calculate stellar year and convert to hex
        int StellarYear = (int) Math.floorDiv((int) (H/30 - Y*0.25), 360);
        when.add(decToHex(StellarYear));

        // Convert days
        int Y2 = this.between.getYears();

        var StellarDay = (int) Math.floor(H / 30 - Y2 * 0.25)%360;

        // Format Stellar Days
        String buff = timeForm(1,StellarDay);
        when.add(buff);
        System.out.println(("when has "+when.size()+" indices on line 308 after adding StellarDay\n\n\n"));
        /*
        if(StellarDay < 10)
        {
            when.add(("00"+String.valueOf(StellarDay)));
        } else if ((StellarDay >= 10) && (StellarDay <= 99)) {
            when.add(("0"+String.valueOf(StellarDay)));
        } else {
            when.add(String.valueOf(StellarDay));
        }*/

        // Convert hours
        int StellarHour = (H-1)%30;

        // Format stellar hours
        buff = timeForm(StellarHour);
        when.add(buff);
        System.out.println(("After adding StellarHour, when has "+when.size()+" indices"));
        /*
        if(StellarHour < 10){
            when.add(("0"+StellarHour));
        }
        else {
            when.add(String.valueOf(StellarHour));
        }
        */
        //when.add("00");
        when.add(".");

        /*
                    CURRENT BEHAVIOR
        - Calculates current time in EST
        - Ex. running at 4:27 pm it shows the stellar minute as 27
        - Doesn't increment up when seconds digits reach 60 (possibly due to 12 second discrepancy)
        - what the fuck
        - Time discrepancy between versions: 49m11s
            EST: 4:48 pm
            Python: 2D17204.3722
            Java: 2D17203.4811
         */

        // Calculate Minutes
        int StellarMin = (int) Math.floorMod((delta.toSeconds()/60),60);
        System.out.println(("Line 352: StellarMin = "+StellarMin));
        when.add(timeForm(StellarMin));

        //Calculate Seconds
        long StellarSec = ((delta.toSeconds()) % 60);
        System.out.println(("Line 357: StellarSec = "+StellarSec));
        when.add(timeForm((int) StellarSec));

        // Compensate for lagtime
        StellarSec += 11;
        System.out.println(("Line 362: StellarSec after lag compensation = "+StellarSec));
        System.out.println(("Minutes index of when = "+when.get(4)));
        System.out.println(("Seconds index of when = "+when.get(5)+"\n"));
        System.out.println("BEGINNING FORMATTING PROCEDURES\n");

        if(StellarSec >= 60){
            StellarSec -= 60;
            StellarMin++;

            buff = timeForm((int) StellarSec);
            System.out.println(("Line 372: buff(StellarSec) = "+buff));
            try {
                when.set(5, buff);
            }catch(IndexOutOfBoundsException e){
                System.out.println("INDEXOUTOFBOUNDSEXCEPTION ON LINE 364\n\n\n\n");
                when.add(buff);
            }
        }
        StellarMin += 49;
        if(StellarMin >= 60){
            StellarHour++;
            StellarMin -= 60;

            buff = timeForm(StellarMin);
            when.set(4,buff);
        }
        if(StellarHour >= 30){
            StellarDay++;
            StellarHour -= 30;

            buff = timeForm(2, StellarHour);
            when.set(2,buff);
        }
        if(StellarDay>360){ // Compiler flags this as "always false" but clearly that mightn't be accurate
            // given that it can be incremented in above conditional *after* modulo is done when calculating initial value
            StellarDay -= 360;
            StellarYear++;

            buff = timeForm(1,StellarDay);
            when.set(1,buff);

            buff = decToHex(StellarYear);
            when.set(0, buff);
        }

//        when.add("~~");
        // Format stellar minutes
        /*
        if(StellarMin < 10)
        {
            when.add("0"+StellarMin);
        }
        else{
            when.add(String.valueOf(StellarMin));
        }

        // Format stellar Seconds
        if(StellarSec < 10)
        {
            when.add("0"+StellarSec);
        }
        else{
            when.add(String.valueOf(StellarSec));
        }
        */
        //when.add("0000");
        return when;
    }


    public ArrayList<String> calcStardate(boolean now){
        ArrayList<String> when = new ArrayList<>();

        LocalDateTime rightFuckinNow;
        // Total seconds elapsed
        long S_t;

        if(now){
            // Get right fucking now and convert to Julian
            rightFuckinNow = LocalDateTime.now().minusDays((long) 13.0075);
            this.todayGreg = rightFuckinNow;
        }
        else{
            rightFuckinNow = this.todayGreg.minusDays((long) 13.0075);
        }

        // Getting S_t value
        Duration delta = Duration.between(zeroDayJules, rightFuckinNow);
        S_t = delta.getSeconds();
        System.out.println(("LINE 571: S_t = "+S_t+"\n"));

        // CALCULATIONS
/*
        System.out.println("Declaring int[] stellaris and beginning recursion");
        int[] stellaris = euc2StarREC(S_t, new int[5], 0);
        System.out.println(("Recursion complete.\nstellaris = "+Arrays.toString(stellaris)));*/

        // VARIABLE DECLARATIONS
        int[] euclid;
        int StellarYear = 0;
        int StellarDay = 0;
        int StellarHour = 0;
        int StellarMin = 0;
        int StellarSec = 0;
        int remainder1 = 0;

        System.out.println("Entering try-catch for calculations (line 720)");
        try {
            // YEARS
            euclid = euclidDiv(S_t, SF[3]);
            StellarYear = euclid[0];
            System.out.println(("StellarYear = "+StellarYear));
            remainder1 = euclid[1];
            System.out.println(("Remainder for year = "+remainder1+"\n\n"));
            // DAYS
            euclid = euclidDiv(remainder1, SF[2]);
            StellarDay = euclid[0];
            remainder1 = euclid[1];
            System.out.println(("StellarDay = "+StellarDay));
            System.out.println(("Remainder for day = "+remainder1+"\n\n"));
            // HOURS
            euclid = euclidDiv(remainder1, SF[1]);
            StellarHour = euclid[0];
            remainder1 = euclid[1];
            System.out.println(("StellarHour = "+StellarHour));
            System.out.println(("Remainder for hour = "+remainder1+"\n\n"));
            // MINUTES
            euclid = euclidDiv(remainder1, SF[0]);
            StellarMin = euclid[0];
            remainder1 = euclid[1];
            System.out.println(("StellarMin = "+StellarMin));
            System.out.println(("Remainder for minutes = "+remainder1+"\n\n"));
            // SECONDS
            //euclid = euclidDiv(remainder1, Factors.get("minute"));
            StellarSec = remainder1;
            //System.out.println(("StellarSec = "+StellarSec));

            System.out.println("FINISHED WITH INITIAL CALCULATIONS\n\n\n\n");
            // No remains necessary for seconds
        }catch(NullPointerException e){
            System.out.println("NULL POINTER EXCEPTION IN CALCULATIONS, LINES 534-547\n\n\n");
            System.out.println(Arrays.toString(e.getStackTrace()));
            when.add("00");
            when.add("000");
            when.add("00");
            when.add(".");
            when.add("00");
            when.add("00");
            return when;
        }

        // VALUE ADJUSTMENTS
        System.out.println("ENTERING VALUE ADJUSTMENTS PHASE");
        /*    // SECONDS
        if(stellaris[4] >= 60){
            stellaris[4] =- 60;
            System.out.println(("StellarSec after adjustment = "+stellaris[5]+"\n\n\n\n"));
            stellaris[3]++;
        }
            // MINUTES
        if(stellaris[3] >= 60){
            stellaris[3] =- 60;
            stellaris[2]++;
        }
            // HOURS
        if(stellaris[2] >= 30){
            stellaris[2] =- 30;
            stellaris[1]++;
        }
            // DAYS
        if(stellaris[1] > 360){
            stellaris[1] =- 360;
            stellaris[0]++;
        }*/
        // SECONDS
        if(StellarSec >= 60){
            StellarSec =- 60;
            System.out.println(("StellarSec after adjustment = "+StellarSec+"\n\n\n\n"));
            StellarMin++;
        }
        // MINUTES
        if(StellarMin >= 60){
            StellarMin =- 60;
            StellarHour++;
        }
        // HOURS
        if(StellarHour >= 30){
            StellarHour =- 30;
            StellarDay++;
        }
        // DAYS
        if(StellarDay > 360){
            StellarDay =- 360;
            StellarYear++;
        }

        // FORMATTING STRINGS AND ADDING TO `WHEN`
        String buff = "";
            // YEARS
        when.add(decToHex(StellarYear));
            // DAYS
        buff = timeForm(1, StellarDay);
        when.add(buff);
            // HOURS
        buff = timeForm(StellarHour);
        when.add(buff);
            // POINT
        when.add(".");
            // MINUTES
        buff = timeForm(StellarMin);
        when.add(buff);
            // SECONDS
        buff = timeForm(StellarSec);
        when.add(buff);


        return when;
    }

    public stardate()
    {
        this.datum = new ArrayList<String>();

        // Initialize Period object "between"
        LocalDate rightNowGreg = LocalDate.now();
        LocalDate startingGreg = LocalDate.of(1970, 8, 13);
        this.between = Period.between(startingGreg, rightNowGreg);
        this.between.minusDays(12); // minus 12 days to convert to julian
        // 12 and not 13 because Period.between() is second date Exclusive so treat it as already subtracted

        this.setDatum(this.calcStardate(true));
    }
}
