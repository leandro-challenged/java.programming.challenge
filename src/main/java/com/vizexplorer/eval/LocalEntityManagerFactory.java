package com.vizexplorer.eval;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LocalEntityManagerFactory {

  private static final String PERSISTENCE_UNIT = "persistence";
   static EntityManagerFactory emf;
  
  public static void configureDbUrl(String url)
  {
    Map<String, String> properties = new HashMap<>();
    properties.put("javax.persistence.jdbc.url", url);
    emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, properties);
  }
  
  public static EntityManager createEntityManager() 
  {
    if (emf == null || !emf.isOpen()) {
      emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
    }
    return emf.createEntityManager();
  }

  public static void close() {
    if (emf != null) {
      emf.close();
    }
  }
}
