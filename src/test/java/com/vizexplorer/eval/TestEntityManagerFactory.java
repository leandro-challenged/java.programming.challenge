package com.vizexplorer.eval;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestEntityManagerFactory {

  private static EntityManagerFactory emf;
  
  static 
  {
    Map<String, String> properties = new HashMap<>();
    properties.put("javax.persistence.jdbc.url", "jdbc:h2:mem:challenge");
    emf = Persistence.createEntityManagerFactory("persistence", properties);
  }
  
  public static EntityManager createEntityManager() {
    return emf.createEntityManager();
  }
}
