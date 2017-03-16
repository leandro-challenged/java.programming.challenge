/**
 * Copyright Notice Component
 * Our software code should contain a copyright notice to prevent an infringer from claiming that it did not know the code was protected under copyright.  The notice should look like this:
 * Copyright © 2008 - 2016 NEW BIS SAFE LUXCO S.Á.R.L 
 * The copyright start date should represent the oldest elements in the code and the end date should represent the newest elements in the code.
 * 
 * Licensing Component
 * 
 * Under our standard VizExplorer license agreements we license our "Software".  "Software" has the definition below:
 * 
 * “Software” means: the VizExplorer client application and/or server application software (as applicable, and in executable form only) provided to Customer by Supplier or with Supplier’s written consent, and (i) all user guides, manuals and other user documentation relating to the Software (whether provided in hard-copy, electronically or on-line); (ii) all enhancements, modifications, updates, new releases that may, from time to time, be provided to Customer by Supplier or with Supplier’s written consent; and (iii) all additional VizExplorer software code (including, but not limited to, SQL interface code) but excluding all source code, that may, from time to time, be provided to Customer by Supplier or with Supplier’s written consent;
 * 
 * In addition our standard VizExplorer license agreement provides:
 * No license or subscription is granted for any source code and no license or right is granted to modify, adapt, create a derivative work, merge, or translate the Software without the prior written consent of Supplier.
 * Accordingly, VizExplorer does grant a license to the software in executable form, but does not grant a license for source code.
 * 
 * Intellectual Property Notice
 * Based on the above, I suggest the following generic Intellectual Property Notice:
 * 
 *  “The following Intellectual Property Notice applies to all software code below and replaces any similar notice contained in this software.
 * INTELLECTUAL PROPERTY NOTICE
 * Copyright © 2008 - 2016 NEW BIS SAFE LUXCO S.Á.R.L 
 * The VizExplorer software code below is a component of a VizExplorer software solution. 
 *  If the software code below is in non-source code executable form, then the code is licensed to you on the terms and conditions of VizExplorer’s standard End User License Agreement, a copy of which is located on VizExplorer’s website at: http://www.vizexplorer.com/license-agreements/.   By using the software code you agree that you have read and accepted the terms and conditions of VizExplorer’s standard End User License Agreement.
 * If the software code below is in source code form, no license or other permission is granted to you to use the software code."
 * 
 */
package com.vizexplorer.eval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * @author travis
 *
 */
public class PersonTest
{
  
  @Test
  public void testIdUniqueness()
  {
    Person p1 = new Person("Bart", "Male", new GregorianCalendar(1992, Calendar.MAY, 9).getTime());
    String id1 = p1.getId();
    
    Person p2 = new Person("Lisa", "Female", new GregorianCalendar(1976, Calendar.JUNE, 11).getTime());
    String id2 = p2.getId();
    
    Person p3 = new Person("Pat", "Other", new GregorianCalendar(1988, Calendar.FEBRUARY, 22).getTime());
    String id3 = p3.getId();
    
    assertFalse(id1.isEmpty());
    assertFalse(id2.isEmpty());
    assertFalse(id3.isEmpty());
    assertFalse(id1.equals(id2));
    assertFalse(id2.equals(id3));
  }

  @Test
  public void testClone()
  {
    Person p1 = new Person("Canuck", "Male", new GregorianCalendar(1967, Calendar.JULY, 1).getTime());
    String id1 = p1.getId();
    
    Person p2 = p1.clone();

    assertFalse(id1.equals(p2.getId()));
    assertFalse(p1.equals(p2));
    assertEquals(p1.getName(), p2.getName());
    assertEquals(p1.getGender(), p2.getGender());
    assertEquals(p1.getBirthDate(), p2.getBirthDate());
  }

  @Test
  public void testUpdate()
  {
    Person p1 = new Person("Update", "Male", null);
    assertEquals(null, p1.getBirthDate());
    Date bd = new GregorianCalendar(1967, Calendar.JULY, 1).getTime();
    p1.setBirthDate(bd);
    assertEquals(bd, p1.getBirthDate());

    p1.setName("newname");
    assertEquals("newname", p1.getName());

    p1.setGender("Female");
    assertEquals("Female", p1.getGender());
  }
  
  @Test
  public void changingCloneShouldNotAffectOriginalEntity() 
  {
    Person originalPerson = new Person("Canuck", "Male", new GregorianCalendar(1967, Calendar.JULY, 1).getTime());
    long originalBirthDate = originalPerson.getBirthDate().getTime();
    
    Person clonedPerson = originalPerson.clone();
    
    Date newBirthDate = new GregorianCalendar(1967, Calendar.AUGUST, 1).getTime();
    clonedPerson.getBirthDate().setTime(newBirthDate.getTime());
    
    assertEquals(originalBirthDate, originalPerson.getBirthDate().getTime());
  }
}
