package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
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
                    [] take new stardate and convert to greg
                        [] calcGregDate() is complete
                    [*] assign to todayGreg
             */
            this.todayGreg = this.calcGregDate();
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
                    digit = Integer.getInteger(String.valueOf(hexaD.charAt(i)));
                }
                catch (NullPointerException e){
                    digit = 0;
                    System.out.println("ERROR: NULLPOINTER AT LINE 153");
                    System.out.println(("Variable hexaD = "+hexaD));
                }
            }

            // Convert the digit to decimal and add it to the final int
            dec += (int) (digit*(Math.pow(16, power)));
            power -= 1;
        }

        return dec;
    }


    public LocalDateTime conv2Greg(){
        LocalDateTime result; //final result
        int year; //int for the year to be assigned to result
        int month; //int for the month to be assigned to result
        int day; //int for the day to be assigned to result
        int hour; //int for the hour to be assigned to result
        int minute; //int for the minute to be assigned to result
        int second; //int for the second to be assigned to result
        /*
        Reverse calcStardate()

        TODO
            [*] assign seconds
            [*] assign minutes
            [] Research
                [] Euclidean algorithm for inverting modulo operations
                    - a*s + t*b = 1
                    - a*s ≡ 1 mod b
                    - x*a*s ≡ c*s mod b
                    - x*1 ≡ c*s mod b
                    - x ≡ c*s mod b
                        - https://math.stackexchange.com/questions/684550/how-to-reverse-modulo-of-a-multiplication
                    - Might not be possible
                        - https://stackoverflow.com/questions/53191604/how-do-i-reverse-the-modulus-operator
            [] reverse hour calculation
            [] reverse day calculations
            [] calculate year
            []
         */

        second = Integer.parseInt(this.datum.get(5));
        minute = Integer.parseInt(this.datum.get(4));

        // YEAR CALCULATION
        int yearGap = hexToDec(this.datum.get(0));
        //LocalDateTime rightNow = this.zeroDayJules;
        //rightNow.minusYears(yearGap);
        year = this.zeroDayJules.getYear()+yearGap;

        // Day/month calculation
        /*
        - take days from this.datum
        - calculate from 30 hour days down to 24 hour days
        - temp LocalDateTime zeroDayJules clone
        - add calculated days
        - assign day to clone
        - clone.getDays()
        - clone.getMonth()

         */
        int dayGap = Integer.parseInt(this.datum.get(2));
        // calculate from 30 hour days into 24 hour days
        // assign result to dayGap

        LocalDateTime zeroClone = this.zeroDayJules;
        zeroClone.plusDays((long) Math.floor(dayGap+13.0075)); // convert to gregorian
        month = zeroClone.getMonthValue();
        day = zeroClone.getDayOfMonth();

        // hour calculation
        /*
        - reverse hour calculation to get total hours
        - modulo 24 to adjust for 24 hour periods
        - bob's your uncle
         */


        //result = LocalDateTime.of(year,month,day,hour,minute,second);
        return LocalDateTime.now();
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

    public ArrayList<String> calcStardate(boolean now)
    {
        ArrayList<String> when = new ArrayList<>();

        LocalDateTime rightFuckinNow;

        if(now) {
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

    public LocalDateTime calcGregDate(){
        return this.zeroDayGreg;
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
