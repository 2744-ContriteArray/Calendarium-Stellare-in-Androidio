package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.lang.Math;

public class stardate
{
    /// TODO
    /// [] learn timekeeping
    ///     - What library is equivalent to datetime?
    ///     - How does it work?
    /// [] global variables
    ///     [] datum
    ///         - private string list
    ///         - 6 cells
    ///         - datum[3] = "."
    ///     [] zeroDayJules
    ///         - private date obj
    ///         - 08/13/1970
    private List<String> datum = new ArrayList<String>();

    /// TODO
    /// [] define datum
    /// [] polymorphism
    ///     [] multiple constructor versions w/ default params
    ///         [] with stardate param
    ///         [] with gregorian param
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
    }

    /// TODO:
    /// [] while dec > 0: do math
    ///     [] if the remainder > 9, it's a letter
    ///     [] else it's < 9 and an int
    /// [] reassign dec to floor(dec/16)
    /// [] for i in range(len(buff)-1, 0, -1): hexa += str(buff[i])
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

}
