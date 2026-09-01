package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.lang.Math;
import java.time.*;

public class stardate
{
    private List<String> datum = new ArrayList<String>();

    // ZERO DAYS
    private final LocalDateTime zeroDayGreg =
            LocalDateTime.of(1970, 8, 13, 0, 0, 0);
    private final LocalDateTime zeroDayJules =
            LocalDateTime.of(1970, 7, 31, 0, 0, 0);

    // Setter for datum
    public void setDatum(ArrayList<String> when)
    {
        try {
            for (int i = 0; i < 6; i++) {
                if(this.datum.get(i).equals("."))
                {
                    continue;
                }
                else {
                    this.datum.set(i, when.get(i));
                }
            }
        }catch (IndexOutOfBoundsException e)
        {
            MainActivity.debugPrintln(("ERROR IN setDatum(): "+e.getMessage()));
            MainActivity.debugPrintln(("STACK TRACE: "+e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }



    // Purpose: convert decimal integers to hexadecimal strings
    private static String decToHex(int dec)
    {
        String hexa = "";
        ArrayList<String> buff = new ArrayList<String>();
        String[] numLett = new String[] {"A", "B", "C", "D", "E", "F"};

        // math time
        while(dec > 0)
        {
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

            // amend dec to reflect dec/16 without remainder as floats
            dec = Math.floorDiv(dec, 16);
        }

        // assemble the collected remainders into the hex number
        for(int i = buff.size()-1; i == 0; i--)
        {
            hexa = hexa.concat(buff.get(i));
        }

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

    /// TODO:
    /// [] Review calculations and formulae
    ///     - could it be a datatype issue?
    /// [] Verify accuracy
    /// [] Repeat review until refined
    /// [] Review formatting
    ///     - only 4 decimal places
    public ArrayList<String> calcStardate()
    {
        ArrayList<String> when = new ArrayList<>();
        // THROWS INDEX OUT OF BOUNDS ERROR, INDEX: 3, SIZE: 0
        //when.add(3, ".");

        // Debug printout
        MainActivity.debugPrintln("Now in calcDateJ()");

        // Get right fucking now and convert to Julian
        LocalDateTime rightFuckinNow = LocalDateTime.now().minusDays((long) 13.0075);
        // Get duration difference between zero day and rn
        Duration delta = Duration.between(zeroDayGreg, rightFuckinNow);
        // Convert to Julian
        //delta.minusDays((long) 13.0075);

// CALCULATIONS
        // Calculate Julian years
        int Y = (int) (Math.floorDiv((int) delta.toDays(), (int) 365.25));
        MainActivity.debugPrintln(("Years in duration " +
                "(found using Math.floorDiv((int) delta.toDays(), (int) 365.25): "+Y));

        int H = (int) Math.floor(delta.toHours());
        MainActivity.debugPrintln(("H = floor("+delta.toHours()+" = "+ H));

        // Calculate stellar year and convert to hex
        int StellarYear = (int) Math.floorDiv((int) (H/30 - Y*0.25), 360);
        MainActivity.debugPrintln(("Stellar year = floorDiv((int) ("+ H +
                "/30 - " + Y + "0.25), 360) = " + StellarYear));
        when.add(decToHex(StellarYear));

        // Convert days
        int StellarDay = (int) Math.floorMod((int) (H/30 - Y*0.25), 360);
        MainActivity.debugPrintln("Stellar day = (int) Math.floorMod((int) (H/30 - Y*0.25), 360) " +
                "= "+StellarDay);
        when.add(String.valueOf(StellarDay));

        // Convert hours
        int StellarHour = H%30;
        when.add(String.valueOf(StellarHour));
        when.add(".");

        // Format stellar minutes
        if(delta.toMinutes() < 10)
        {
            when.add(4, ("0"+String.valueOf(delta.toMinutes())));
        }
        else{
            when.add(4, String.valueOf(delta.toMinutes()));
        }

        // Format stellar Seconds
        if(delta.toSeconds() < 10)
        {
            when.add(5, ("0"+String.valueOf(delta.toSeconds())));
        }
        else{
            when.add(5, String.valueOf(delta.toSeconds()));
        }

        return when;
    }

    public stardate()
    {
        // Initialize datum
        for (int i=0; i<=5; i++)
        {
            if(i==3)
            {
                this.datum.add(".");
            }
            else
            {
                this.datum.add("00");
            }
        }

        //LocalDateTime rightNowGreg = LocalDateTime.now();
        this.setDatum(this.calcStardate());
    }
}
