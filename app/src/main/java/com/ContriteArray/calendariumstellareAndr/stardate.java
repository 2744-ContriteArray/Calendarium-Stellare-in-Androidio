package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.Math;
import java.time.*;

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
            //MainActivity.debugPrintln(("datum = "+this.datum));
            /*
                TODO:
                    [*] take new stardate and convert to greg
                        [*] conv2Greg() is complete
                    [*] assign to todayGreg
             */
            this.todayGreg = this.conv2Greg();
        }catch (IndexOutOfBoundsException e)
        {
            System.out.println(("ERROR IN setDatum(): "+e.getMessage()));
            System.out.println(("STACK TRACE: "+e.getStackTrace()));
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

    // Purpose: convert decimal integers to hexadecimal strings
    private static String decToHex(int dec)
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

    // Purpose: convert hexadecimal strings into decimal integers
    private static int hexToDec(String hexaD)
    {
        // PROCESS
        // Multiply each digit of hex number by 16 raised to the power of its position
        //      starting from 0
        // Add up the results of these operations
        // ex. A.4 = (A*16^0) + (4*16^-1) = (10*1) + (4*0.0625) = 10.25

        // create the dictionary for letter digits
        // .put() operations braced for easy organizing and collapsibility
        HashMap<String, Integer> Letters = new HashMap<String, Integer>();
        {
            Letters.put("A", 10);
            Letters.put("B", 11);
            Letters.put("C", 12);
            Letters.put("D", 13);
            Letters.put("E", 14);
            Letters.put("F", 15);
        }
        int power = hexaD.length()-1;
        int dec = 0;

        for(int i=0; i<hexaD.length(); i++)
        {
            int digit;

            // handle the letters and cast from String to Integer
            if(Letters.containsKey(String.valueOf(hexaD.charAt(i))))
            {
                try {
                    digit = Letters.get(String.valueOf(hexaD.charAt(i)));
                }
                catch (NullPointerException e) {
                    System.out.println(("ERROR: "+e.getMessage()));
                    throw new RuntimeException(e);
                }
            }
            else
            {
                try{
                    digit = Integer.getInteger(String.valueOf(hexaD.charAt(i))); // Throwing nullpointers
                }
                catch (NullPointerException e){
                    digit = 0;
                    System.out.println("ERROR: NULLPOINTER AT LINE 154");
                    System.out.println(("Variable hexaD = "+hexaD));
                    System.out.println(("i = "+i));
                }
            }

            // Convert the digit to decimal and add it to the final int
            dec += (int) (digit*(Math.pow(16, power)));
            power -= 1;
        }

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

    public LocalDateTime conv2Greg(){
        LocalDateTime result; //final result
        int year; //int for the year to be assigned to result
        int month = 1; //int for the month to be assigned to result
        int day; //int for the day to be assigned to result
        int hour; //int for the hour to be assigned to result
        int minute; //int for the minute to be assigned to result
        int second; //int for the second to be assigned to result
        int dayOfMonth = 1; // calendar day
        long S_t = 0;

        int remains = 0;
        // UNIT FACTORS
        long SYfactor = 38880000;
        long F_y = 31557600;
        long F_d = 86400;
        int F_h = 3600;
        int F_m = 60;

        /*
        Reverse calcStardate()

        TODO
            [*] Unit Factors
                [*] Julian
                    [*] Year
                    [*] Day
                    [*] Hour
                    [*] Minute
                [*] Stellar
                    [*] Year
            [*] Get Stellar Units from this.datum
                [*] Year
                [*] Convert to Decimal
            [*] Get S_t by StellarYear*SYfactor
            [*] Calculate Julian date via division and modulo
                [*] int remains
                [*] Year
                [*] Month
                [*] Day of year
                [*] Day of month
                [*] Hour
                [*] Minute
                [*] Seconds
            [] Test
                [*] Debug printouts
                [*] Reevaluate maths
                [*] Make adjustments where needed
                    - Do we need a method for reversing euclidDiv()?
                    - Are we doing the math correctly?
                - For debug test, use stardate 2D16320.4400, it should evaluate to 8/13/2026 @ 14:44:00
         */

        // S_t CALCULATION
        System.out.println("ENTERING S_t CALCULATION");
        int yearGap = hexToDec(this.datum.get(0));
        S_t = yearGap * SYfactor;
        System.out.println(("year rendered to decimal = "+yearGap));
        System.out.println(("S_t = "+S_t+"\n\n\n"));

        // CALCULATIONS
        int[] euclid;
            // YEAR
        euclid = euclidDiv(S_t, F_y);
        year = euclid[0];
        remains = euclid[1];
            // DAY OF YEAR (NOT OF MONTH)
        euclid = euclidDiv(remains, F_d);
        day = euclid[0];
        remains = euclid[1];
            // HOUR
        euclid = euclidDiv(remains, F_h);
        hour = euclid[0];
        remains = euclid[1];
            // MINUTE
        euclid = euclidDiv(remains, F_m);
        minute = euclid[0];
        remains = euclid[1];
            // SECOND
        second = remains;
        // No remainder needed for seconds

        // Convert to gregorian
        day += 13;
        if(day > 365){
            day =- 365;
            year++;
        }

        // MONTH CALCULATION
        int[] MonthLen = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(year%4==0){
            MonthLen[1] = 29;
        }
        int DayBuff = day;
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


        result = LocalDateTime.of(year,month,dayOfMonth,hour,minute,second);
        return result;
    }

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

    private String timeForm(int time){
        String finish;
        if(time<10){
            finish = "0"+String.valueOf(time);
        }else{
            finish = String.valueOf(time);
        }

        return finish;
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


    /*
        TODO:
            - OVERHAUL TO STARDATE FORMULAE
            [*] Copy essentials from existing method
                [*] Manage if(now) operation
                [*] Calculate Julian from Greg
                [*] ArrayList<String> when
            [*] Setup new essentials
                [*] Hashmap for stellar unit factors
                    [*] Declare
                    [*] Assign
                [*] Hashmap for remainders
                    [*] Declare
            [*] Get S_t (total seconds elapsed since zero day)
                [*] Declare Duration delta as Duration.between(zeroDayJules,rightFuckinNow)
                [*] S_t = delta.getSeconds()
            [*] Calculations
                [*] Implement Euclidean division instead of regular, float-yielding division
                [*] Calculate StellarYear
                [*] Calculate StellarDay
                [*] Calculate StellarHour
                [*] Calculate StellarMin
                [*] Calculate StellarSec
            [*] Formatting
                [*] Create buff string = ""
                [*] Value Adjustments
                    [*] Seconds
                        [*] If seconds >= 60
                            [*] Correct seconds
                            [*] minutes++
                    [*] Minutes
                        [*] If minutes >= 60
                            [*] Correct minutes
                            [*] Hours++
                    [*] Hours
                        [*] If hours >= 30
                            [*] Correct hours
                            [*] Days++
                    [*] Days
                        [*] If days > 360
                            [*] Correct days
                            [*] years++
                [*] String creation and Array assignments
                    [*] Years
                        [*] Convert years to Hex
                        [*] Add StellarYear hex value string to `when`
                    [*] Days
                        [*] Call timeForm() on StellarDay, assign to buff
                        [*] Add buff string value to `when`
                    [*] Hours
                        [*] Call timeForm() on StellarHour, assign to buff
                        [*] Add buff string value to `when`
                    [*] Point
                        [*] Add "." to `when`
                    [*] Minutes
                        [*] Call timeForm() on StellarMin, assign to buff
                        [*] Add buff string value to `when`
                    [*] Seconds
                        [*] Call timeForm() on StellarSec, assign to buff
                        [*] Add buff string value to `when`
                - `when` should have 6 indices afterwards
            [*] Return `when`
            [*] TESTS
                - Seconds not updating on MainActivity, showing '00'
                [*] Add debug printouts go leor
                [*] Run and evaluate
                - Repeat until problem identified
     */
    public ArrayList<String> calcStardate(boolean now){
        ArrayList<String> when = new ArrayList<>();

        LocalDateTime rightFuckinNow;
        // Unit factors
        HashMap<String, Integer> Factors = new HashMap<>();
        // Assignments
        Factors.put("year",38880000);
        Factors.put("day", 108000);
        Factors.put("hour", 3600);
        Factors.put("minute", 60);

        // Remainders
        int remainder1 = 0; // R_n
        //int remainder2 = 0; // R_n+1
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
            // VARIABLE DECLARATIONS
        int StellarYear;
        int StellarDay;
        int StellarHour;
        int StellarMin;
        int StellarSec;
        int[] euclid = {0,0};

        System.out.println("Entering try-catch for calculations (line 584)");
        try {
            // YEARS
            euclid = euclidDiv(S_t, Factors.get("year"));
            StellarYear = euclid[0];
            System.out.println(("StellarYear = "+StellarYear));
            remainder1 = euclid[1];
            System.out.println(("Remainder for year = "+remainder1+"\n\n"));
            // DAYS
            euclid = euclidDiv(remainder1, Factors.get("day"));
            StellarDay = euclid[0];
            remainder1 = euclid[1];
            System.out.println(("StellarDay = "+StellarDay));
            System.out.println(("Remainder for day = "+remainder1+"\n\n"));
            // HOURS
            euclid = euclidDiv(remainder1, Factors.get("hour"));
            StellarHour = euclid[0];
            remainder1 = euclid[1];
            System.out.println(("StellarHour = "+StellarHour));
            System.out.println(("Remainder for hour = "+remainder1+"\n\n"));
            // MINUTES
            euclid = euclidDiv(remainder1, Factors.get("minute"));
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
        // 12 and not 13 because Period.between() is second date EXclusive so treat it as already subtracted

        this.setDatum(this.calcStardate(true));
    }
}
