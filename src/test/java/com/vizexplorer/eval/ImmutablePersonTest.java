package com.vizexplorer.eval;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class ImmutablePersonTest {

  @Test
  public void shouldNotAllowUpdateImmutablePerson() 
  {

    Person person = new Person("Bob", "Male", new GregorianCalendar(1967, Calendar.JULY, 1).getTime());
    
    Person immutablePerson = person.getImmutable();
    
    String error = "Should not allow update immutable person";
    try 
    {
      immutablePerson.setName("new name");
      fail(error);
    }
    catch(UnsupportedOperationException e)  { }

    try 
    {
      immutablePerson.setGender("Female");
      fail(error);
    }
    catch(UnsupportedOperationException e)  { }
    
    try 
    {
      immutablePerson.setBirthDate(new Date());
      fail(error);
    }
    catch(UnsupportedOperationException e)  { }
  }
  
  @Test
  public void changingAnImmutablePersonReferenceAttributeShouldAffectImmutableInstance() {
    
    long july = new GregorianCalendar(1967, Calendar.JULY, 1).getTime().getTime();
    long august = new GregorianCalendar(1967, Calendar.AUGUST, 1).getTime().getTime();
    
    Person immutablePerson = new Person("Bob", "Male", new Date(july)).getImmutable();
    
    immutablePerson.getBirthDate().setTime(august);
    
    assertEquals(july, immutablePerson.getBirthDate().getTime());
  }
}

