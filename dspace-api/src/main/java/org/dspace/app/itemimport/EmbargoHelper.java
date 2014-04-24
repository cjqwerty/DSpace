package org.dspace.app.itemimport;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

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
     * @throws ParseException
     */
    public Date getEmbargoDate() throws ParseException
    {
        String embargoDate = "";

        if (embargoExists)
        {
            embargoDate = line.substring(eMarkerIndex + embargoMarker.length(),
                    eEndIndex).trim();
        }
        
        return DateUtils.parseDate(embargoDate, new String[] { "yyyy-MM-dd",
                "yyyy-MM", "yyyy" });
    }

    /**
     * Checks if the current line contains an embargo
     * 
     * @return True if the current line contains an embargo, false otherwise
     * @throws ParseException
     */
    public boolean checkForEmbargo() throws ParseException
    {
        if (line.contains(" embargo:"))
        {
            throw new ParseException("Contents file must be tab delimited",
                    line.indexOf(" embargo"));
        }
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
