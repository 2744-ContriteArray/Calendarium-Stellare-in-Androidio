package com.ContriteArray.calendariumstellareAndr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


}
