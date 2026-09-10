package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.lang.Math;
import java.time.*;

public class stardate
{
    private ArrayList<String> datum;

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
            this.datum.addAll(when);
            //MainActivity.debugPrintln(("datum = "+this.datum));
        }catch (IndexOutOfBoundsException e)
        {
            MainActivity.debugPrintln(("ERROR IN setDatum(): "+e.getMessage()));
            MainActivity.debugPrintln(("STACK TRACE: "+e.getStackTrace()));
//            throw new RuntimeException(e);
        }
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
                    MainActivity.debugPrintln(("ERROR: "+e.getMessage()));
                    throw new RuntimeException(e);
                }
            }
            else
            {
                try{
                    digit = Integer.getInteger(String.valueOf(hexaD.charAt(i)));
                }
                catch (NullPointerException e){
                    digit = 0;
                    MainActivity.debugPrintln("ERROR: NULLPOINTER AT LINE 162");
                    MainActivity.debugPrintln(("Variable hexaD = "+hexaD));
                }
            }

            // Convert the digit to decimal and add it to the final int
            dec += (int) (digit*(Math.pow(16, power)));
            power -= 1;
        }

        return dec;
    }

    public ArrayList<String> calcStardate()
    {
        ArrayList<String> when = new ArrayList<>();

        // Debug printout
        MainActivity.debugPrintln("/clear");
        MainActivity.debugPrintln("Now in calcStardate()");

        // Get right fucking now and convert to Julian
        LocalDateTime rightFuckinNow = LocalDateTime.now().minusDays((long) 13.0075);
        // Get duration difference between zero day and rn
        Duration delta = Duration.between(zeroDayJules, rightFuckinNow);

// CALCULATIONS
        // Calculate Julian years
        int Y = (int) (Math.floorDiv((int) delta.toDays(), (int) 365.25));
        //MainActivity.debugPrintln(("Y = "+Y));
        //MainActivity.debugPrintln(("Years in duration " +
        //        "(found using Math.floorDiv((int) delta.toDays(), (int) 365.25): "+Y));

        int H = (int) Math.floor(delta.toHours());
        MainActivity.debugPrintln(("H = "+ H));

        // Calculate stellar year and convert to hex
        int StellarYear = (int) Math.floorDiv((int) (H/30 - Y*0.25), 360);
        when.add(decToHex(StellarYear));

        /*
        TODO:
         [~] Review calculations and formulae
             - Comb through operations and verify step-by-step
             - Problem detected with decToHex(StellarYear) not returning proper
             - Debug printout in-app reads as blank
             - Could be returning an empty string or could be some other issue
             - Need further review to diagnose and treat
             - Verify accuracy
             - Repeat review until refined and repeat for every calculation
             [*] Years
             [~] Days
                - Possibly fixed. requires close observation in case of relapse
                - Has been "fixed" before only to break yet again
             [~] Hours
             [] Minutes
             [*] Seconds
         [~] Convert calculations from Duration obj operations to Period obj
            [~] Days
            [~] Hours
            [] Minutes
            [*] Seconds
         [*] Review formatting
             - only 4 decimal places
        */

        //MainActivity.debugPrintln("/clear");
        MainActivity.debugPrintln("NOW EVALUATING STELLAR DAY CALCULATIONS");
        // Convert days
        int Y2 = this.between.getYears();
        MainActivity.debugPrintln(("this.between.getYears() = "+this.between.getYears()));

        //var H2 = (int) (this.between.getDays()*24 + delta.toHours()%24);
        //MainActivity.debugPrintln(("H = "+ between.getDays()+"* 24 + "+delta.toHours()+"%24 = "+H2));
        //MainActivity.debugPrintln(("between.getDays() = "+this.between.getDays()));
        var StellarDay = (int) Math.floor(H / 30 - Y2 * 0.25)%360;
        //int StellarDay = (int) delta.toSeconds()%60;
        MainActivity.debugPrintln("Stellar day = (int) Math.floor(H/30 - Y*0.25)%360 " +
                "= "+StellarDay);

        // Format Stellar Days
        if(StellarDay < 10)
        {
            when.add(("00"+String.valueOf(StellarDay)));
        } else if ((StellarDay >= 10) && (StellarDay <= 99)) {
            when.add(("0"+String.valueOf(StellarDay)));
        } else {
            when.add(String.valueOf(StellarDay));
        }

        // Convert hours
        MainActivity.debugPrintln("/clear");
        MainActivity.debugPrintln("NOW EVALUATING HOUR CALCULATIONS");
        int StellarHour = (H-1)%30;
        MainActivity.debugPrintln((H+"/30 = " + H/30));
        MainActivity.debugPrintln(("StellarHour = "+H+"%30 = "+StellarHour));

        // Format stellar hours
        if(StellarHour < 10){
            when.add(("0"+StellarHour));
        }
        else {
            when.add(String.valueOf(StellarHour));
        }
        //when.add("00");
        when.add(".");

        MainActivity.debugPrintln("/clear");
        MainActivity.debugPrintln("CALCULATING MINUTES NOW");
        MainActivity.debugPrintln(("delta.toMinutes() = "+delta.toMinutes()));
        MainActivity.debugPrintln(("delta.toSeconds() = "+delta.toSeconds()));

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
        MainActivity.debugPrintln(("StellarMin = floorMod(delta.toMinutes()/60, 60) \n= floorMod("+
                delta.toSeconds()/60+", 60) = "+StellarMin));

        //Calculate Seconds
        long StellarSec = ((delta.toSeconds()) % 60);

//        when.add("~~");
        // Format stellar minutes
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

        //when.add("0000");
        return when;
    }

    public stardate()
    {
        this.datum = new ArrayList<String>();
        MainActivity.debugPrintln(("datum.isEmpty() evaluates to "+this.datum.isEmpty()));

        // Initialize Period object "between"
        LocalDate rightNowGreg = LocalDate.now();
        LocalDate startingGreg = LocalDate.of(1970, 8, 13);
        this.between = Period.between(startingGreg, rightNowGreg);
        this.between.minusDays(12); // minus 12 days to convert to julian
        // 12 and not 13 because Period.between() is second date EXclusive so treat it as already subtracted

        this.setDatum(this.calcStardate());
    }
}
