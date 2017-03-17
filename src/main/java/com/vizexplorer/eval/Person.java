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

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 *
 */
public class Person
{
  private String id;
  private String name;
  private String gender;
  private Date birthDate;

  /**
   * 
   * @param name
   * @param gender
   * @param birthDate
   */
  public Person(String name, String gender, Date birthDate)
  {
    id = (UUID.randomUUID().toString());
    this.name = name;
    this.gender = gender;
    this.birthDate = birthDate;
  }

  public String getId()
  {
    return id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getGender()
  {
    return gender;
  }
  public void setGender(String gender)
  {
    this.gender = gender;
  }
  public Date getBirthDate()
  {
    return birthDate;
  }
  public void setBirthDate(Date birthDate)
  {
    this.birthDate = birthDate;
  }

  public Person clone()
  {
    return new Person(name, gender, new Date(birthDate.getTime()));
  }
  
  @Override
  public String toString() {
    return String.format("Name: %s; Gender: %s; Birth Date: %s", name, gender, DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(birthDate));
  }

  public Person getImmutable() {
    return new ImmutablePerson(name, gender, birthDate);
  }
}
