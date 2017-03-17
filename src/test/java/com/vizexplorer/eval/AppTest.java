package com.vizexplorer.eval;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;

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

      assertTrue(outContent.toString().startsWith("Person instance created: com.vizexplorer.eval.Person@"));

      args = new String[]{"", "", "", "bad date"};
      App.main(args);

    }
    finally
    {
      System.setOut(null);
    }
  }
  
  @Test
  public void createPerson()
  {
    EntityManager em = TestEntityManagerFactory.createEntityManager();
    App app = new App(em);
    
//    String response = app.performRequest("CREATE", "Biff", "Male", "19950110");
    
    em.clear();
    List<Person> persons = em.createQuery("select p from Person p", Person.class).getResultList();
    
    assertEquals(1, persons.size());
//    assertEquals()
  }
}
