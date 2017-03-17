package com.vizexplorer.eval;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;

import org.junit.BeforeClass;
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
    PrintStream defaultOutput = System.out;
    try
    {
      System.setOut(new PrintStream(outContent));

      String [] args = new String[]{"CREATE", "Biff", "Male", "19950110"};
      App.main(args);

      assertTrue(outContent.toString().startsWith("Person created: id:"));
      
      args = new String[]{"CREATE", "", "", "bad date"};
      App.main(args);

    }
    finally
    {
      System.setOut(defaultOutput);
    }
  }
  
  @Test
  public void createPerson() throws ParseException
  {
    EntityManager em = initDb();
    String response = new App(em).performRequest("CREATE", "Biff", "Male", "19950110");
    
    em.clear();
    List<Person> persons = em.createQuery("select p from Person p", Person.class).getResultList();
    
    assertEquals(1, persons.size());
    assertEquals("Biff", persons.get(0).getName());
    assertEquals("Male", persons.get(0).getGender());
    assertEquals(new SimpleDateFormat("yyyyMMdd").parse("19950110"), persons.get(0).getBirthDate());
    
    String responseExpected = String.format("Person created: id:%s, %s, %s, %s", persons.get(0).getId(), "Biff", "Male", "January 10, 1995");

    assertEquals(responseExpected, response);
  }
  
  @Test
  public void retrievePerson() throws ParseException 
  {
    EntityManager em = initDb();
    Person person = createBiff();
    String expected = String.format("Person found: id:%s, %s, %s, %s", person.getId(), person.getName(), person.getGender(), format(person.getBirthDate()));
    
    String actual = new App(em).performRequest("RETRIEVE", person.getId());
    
    assertEquals(expected, actual);
  }
  
  @Test
  public void updatePerson() throws ParseException 
  {
    EntityManager em = initDb();
    Person person = createBiff();
    String response = new App(em).performRequest("UPDATE", person.getId(), "Jane", "Female", "19950210");
    
    person = em.find(Person.class, person.getId());
    
    assertEquals("Jane", person.getName());
    assertEquals("Female", person.getGender());
    assertEquals(new SimpleDateFormat("yyyyMMdd").parse("19950210"), person.getBirthDate());
    
    String responseExpected = String.format("Person updated: id:%s, %s, %s, %s", person.getId(), "Jane", "Female", "February 10, 1995");

    assertEquals(responseExpected, response);
  }
  
  @Test
  public void deletePerson() throws ParseException 
  {
    EntityManager em = initDb();
    Person person = createBiff();
    String response = new App(em).performRequest("DELETE", person.getId());

    em.clear();
    List<Person> persons = em.createQuery("select p from Person p", Person.class).getResultList();
    
    assertEquals(0, persons.size());
    
    String responseExpected = String.format("Person deleted: id:%s, %s, %s, %s", person.getId(), person.getName(), person.getGender(), format(person.getBirthDate()));

    assertEquals(responseExpected, response);
  }
  
  @BeforeClass
  public static void configureDb()
  {
    String memoryDatabaseUrl = "jdbc:h2:mem:challenge";
    LocalEntityManagerFactory.configureDbUrl(memoryDatabaseUrl);
  }
  
  private Person createBiff() throws ParseException 
  {
    Person person = new Person("Biff", "Male", new SimpleDateFormat("yyyyMMdd").parse("19950110"));
    
    EntityManager em = LocalEntityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.persist(person);
    em.getTransaction().commit();
    
    return person;
  }

  private String format(Date date) {
    return DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(date);
  }
  
  private EntityManager initDb() {
    EntityManager em = LocalEntityManagerFactory.createEntityManager();
    em.getTransaction().begin();
    em.createQuery("delete from Person").executeUpdate();
    em.getTransaction().commit();
    return em;
  }
}
