package org.dspace.app.itemimport;

import java.util.Date;
import org.dspace.core.Utils;

/**
 * Contains utility functions to help parse the content file for embargos. Was
 * refactored into this class to clean up code and make unit testing easier.
 */
public class EmbargoHelper
{
    private String line;

    private String embargoMarker;

    private boolean embargoExists;

    private int eMarkerIndex;

    private int eEndIndex;

    /**
     * Constructor
     * 
     * @param line
     *            - The current line being parsed
     */
    public EmbargoHelper(String line)
    {
        this.line = line;
        embargoMarker = "\tembargo:";
        embargoExists = false;
        eEndIndex = 0;
        eMarkerIndex = 0;
    }

    /**
     * @return The index of the beginning of the embargoMarker
     */
    public int getEMarkerIndex()
    {
        return eMarkerIndex;
    }

    /**
     * @return The index of the end of the embargoMarker
     */
    public int getEEndIndex()
    {
        return eEndIndex;
    }

    /**
     * @return The date to embargo until
     */
    public Date getEmbargoDate()
    {
        String embargoDate = "";

        if (embargoExists)
        {
            embargoDate = line.substring(eMarkerIndex + embargoMarker.length(),
                    eEndIndex).trim();
        }

        return Utils.parseISO8601Date(embargoDate);
    }

    /**
     * Checks if the current line contains an embargo
     * 
     * @return True if the current line contains an embargo, false otherwise
     */
    public boolean checkForEmbargo()
    {
        eMarkerIndex = line.indexOf(embargoMarker);
        eEndIndex = 0;

        if (eMarkerIndex > 0)
        {
            eEndIndex = line.indexOf("\t", eMarkerIndex + 1);
            if (eEndIndex == -1)
            {
                eEndIndex = line.length();
            }

            embargoExists = true;
        }
        else
        {
            embargoExists = false;
        }

        return embargoExists;
    }
}
