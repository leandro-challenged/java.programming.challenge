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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

/**
 *
 */
public class App 
{
  private EntityManager em;

  public App(EntityManager em) 
  {
    this.em = em;
  }
  
  public static void main( String[] args ) throws ParseException
  {
    silentConsole();
    
    EntityManager em = LocalEntityManagerFactory.createEntityManager();
    try 
    {
      App app = new App(em);
      String response = app.performRequest(args);
      System.out.println(response);
    }
    finally
    {
      LocalEntityManagerFactory.close();
    }
  }

  public String performRequest(String... args) throws ParseException 
  {
      String operation = args.length > 0 ? args[0] : "HELP";
      
      em.getTransaction().begin();
  
      String result = operations.get(operation).perform(em, args);
      
      em.getTransaction().commit();
  
      return result;
  }
  
  private static Map<String, Operation> operations = new HashMap<>();
  
  static {
    operations.put("CREATE", new OperationCreate());
    operations.put("RETRIEVE", new OperationRetrieve());
    operations.put("UPDATE", new OperationUpdate());
    operations.put("DELETE", new OperationDelete());
    operations.put("HELP", new OperationHelp());
  }
  
  private static void silentConsole() {
    java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
  }
}

interface Operation
{
  String perform(EntityManager em, String... args) throws ParseException ;
}

class OperationCreate implements Operation
{
  @Override
  public String perform(EntityManager em, String... args) throws ParseException 
  {
    PersonDto attributes = Parser.parseCommandArguments(Arrays.copyOfRange(args, 1, args.length));
  
    Person person = new Person(attributes.name, attributes.gender, attributes.birthDate);
    new PersonRepo(em).create(person);
    
    return "Person created: " + Formatter.format(person);
  }
}

class OperationRetrieve implements Operation
{
  @Override
  public String perform(EntityManager em, String... args) throws ParseException 
  {
    String id = args[1];
  
    Person person = new PersonRepo(em).get(id);

    return "Person found: " + Formatter.format(person);
  }
}

class OperationUpdate implements Operation
{
  @Override
  public String perform(EntityManager em, String... args) throws ParseException 
  {
    String id = args[1];
    
    PersonDto newAttributes = Parser.parseCommandArguments(Arrays.copyOfRange(args, 2, args.length));
    
    PersonRepo repo = new PersonRepo(em);
    
    Person person = repo.get(id);

    person.setName(newAttributes.name);
    person.setGender(newAttributes.gender);
    person.setBirthDate(newAttributes.birthDate);
    
    repo.update(person);
    
    return "Person updated: " + Formatter.format(person);
  }
}

class OperationDelete implements Operation
{
  @Override
  public String perform(EntityManager em, String... args) throws ParseException 
  {
    String id = args[1];
    PersonRepo repo = new PersonRepo(em);
    Person person = repo.get(id);
    repo.delete(person);
    
    return "Person deleted: " + Formatter.format(person);
  }
}

class OperationHelp implements Operation
{
  @Override
  public String perform(EntityManager em, String... args) throws ParseException {
    
    InputStream in = getClass().getResourceAsStream("/help.txt"); 
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

    StringBuilder help = new StringBuilder();
    List<Object> lines = reader.lines().collect(Collectors.toList());
    
    for(Object line : lines)
    {
      help.append(line).append(System.getProperty("line.separator"));
    }
    return help.toString();
  }
}

class Formatter
{
  public static String format(Person person)
  {
    String formattedBirthDate = person.getBirthDate() == null ? null : DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(person.getBirthDate());
    return String.format("id:%s, %s, %s, %s", person.getId(), person.getName(), person.getGender(), formattedBirthDate);
  }
}

class PersonDto
{
  String name;
  String gender;
  Date birthDate;
}

class Parser
{
  static PersonDto parseCommandArguments(String[] args) throws ParseException
  {
    PersonDto result = new PersonDto();
    
    result.name = getValue(args[0]);
    result.gender = getValue(args[1]);
    result.birthDate = getValue(args[2]) == null ? null : new SimpleDateFormat("yyyyMMdd").parse(args[2]);
    
    return result;
  }
  
  private static String getValue(String arg)
  {
    return "null".equals(arg) ? null : arg;
  }
}