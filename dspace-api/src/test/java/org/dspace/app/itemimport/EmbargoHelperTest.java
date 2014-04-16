package org.dspace.app.itemimport;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class EmbargoHelperTest
{

    @Test
    public void testCheckForEmbargoTrue()
    {
        String line = "Liu_Z_D_2012.pdf\tbundle:ORIGINAL\tembargo:2014-01-01";
        EmbargoHelper helper = new EmbargoHelper(line);
        assertTrue(helper.checkForEmbargo());
    }

    @Test
    public void testCheckForEmbargoFalse()
    {
        String line = "Liu_Z_D_2012.pdf\tbundle:ORIGINAL";
        EmbargoHelper helper = new EmbargoHelper(line);
        assertFalse(helper.checkForEmbargo());
    }

    @Test
    public void testGetEmbargoDate() throws ParseException
    {
        String line = "Liu_Z_D_2012.pdf\tbundle:ORIGINAL\tembargo:2014-01-01";
        EmbargoHelper helper = new EmbargoHelper(line);

        // checkForEmbargo() must be called first to
        // get indicies to use
        helper.checkForEmbargo();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2014-01-01");

        assertEquals(date, helper.getEmbargoDate());
    }

    @Test(expected = ParseException.class)
    public void testGetEmbargoDateNoEmbargo() throws ParseException
    {
        String line = "Liu_Z_D_2012.pdf\tbundle:ORIGINAL";
        EmbargoHelper helper = new EmbargoHelper(line);

        // checkForEmbargo() must be called first to
        // get indicies to use
        helper.checkForEmbargo();

        helper.getEmbargoDate();
    }

    @Test(expected = ParseException.class)
    public void testGetEmbargoDateNoDate() throws ParseException
    {
        String line = "Liu_Z_D_2012.pdf\tbundle:ORIGINAL\tembargo:";
        EmbargoHelper helper = new EmbargoHelper(line);

        // checkForEmbargo() must be called first to
        // get indicies to use
        helper.checkForEmbargo();

        helper.getEmbargoDate();
    }

    @Test(expected = ParseException.class)
    public void testGetEmbargoDateWrongFormat() throws ParseException
    {
        String line = "Liu_Z_D_2012.pdf\tbundle:ORIGINAL\tembargo:01/01/14";
        EmbargoHelper helper = new EmbargoHelper(line);

        // checkForEmbargo() must be called first to
        // get indicies to use
        helper.checkForEmbargo();

        helper.getEmbargoDate();
    }

}
