package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;
import java.lang.Math;
import java.time.*;

public class stardate
{
    /// TODO
    /// [*] learn timekeeping
    ///     - What library is equivalent to datetime?
    ///     - How does it work?
    /// [] global variables
    ///     [] datum
    ///         - private string list
    ///         - 6 cells
    ///         - datum[3] = "."
    ///     [*] zeroDayJules
    ///         - private final LocalDateTime obj
    ///     [*] zeroDayGreg

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
            System.out.println(("ERROR IN setDatum(): "+e.getMessage()));
            System.out.println(("STACK TRACE: "+e.getStackTrace()));
            throw new RuntimeException(e);
        }
    }

    /// TODO
    /// [] define datum
    /// [] create and set gregorian now variable
    /// [] call setter for datum
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

        LocalDateTime rightNowGreg = LocalDateTime.now();
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
                    System.out.println(("ERROR: "+e.getMessage()));
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
                    System.out.println("ERROR: NULLPOINTER AT LINE 162");
                    System.out.println(("Variable hexaD = "+hexaD));
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
        ArrayList<String> when = new ArrayList<String>(6);
        when.add(3, ".");

        // Debug printout
        System.out.println("Now in calcDateJ()");
        // Get duration difference between zero day and rn
        Duration delta = Duration.between(zeroDayGreg, LocalDateTime.now());
        // Convert to Julian
        delta.minusDays((long) 13.0075);

// CALCULATIONS
        // Calculate Julian years
        int Y = (int) (Math.floor(delta.toDays()/365.25));
        System.out.println(("Years in duration: "+Y));

        int H = (int) Math.floor((delta.toDays()*24) + delta.toHours());
        System.out.println(("H = floor("+delta.toDays()+"*24) + ("+delta.toHours()+" = "+ H));

        // Calculate stellar year and convert to hex
        int StellarYear = (int) Math.floor((H/30 - Y*0.25)/360);
        System.out.println(("Stellar year = floor(("+ H + "/30 - " + Y + "0.25)/360) = " + StellarYear));
        when.add(0, decToHex(StellarYear));

        // Convert days
        int StellarDay = (int) Math.floor(H/30 - Y*0.25)%360;
        when.add(1, String.valueOf(StellarDay));


        // Placeholder assignments
        when.add(2, "00");
        when.add(4, "00");
        when.add(5, "00");

        return when;
    }

}
