package com.vizexplorer.eval;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
  @Test(expected=ParseException.class)
  public void testMain() throws ParseException
  {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    try
    {
      System.setOut(new PrintStream(outContent));

      String [] args = new String[]{"", "Biff", "Male", "19950110"};
      App.main(args);

      assertTrue(outContent.toString().startsWith("Person instance created: Name: Biff; Gender: Male; Birth Date: January 10, 1995"));

      args = new String[]{"", "", "", "bad date"};
      App.main(args);

    }
    finally
    {
      System.setOut(null);
    }
  }
}
