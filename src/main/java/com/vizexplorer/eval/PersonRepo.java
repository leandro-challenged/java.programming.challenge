package com.vizexplorer.eval;

import javax.persistence.EntityManager;

public class PersonRepo {

  private EntityManager em;

  public PersonRepo(EntityManager em) {
    this.em = em;
  }

  public Person find(String id) {
    return em.find(Person.class, id);
  }

  public void create(Person person) {
    em.persist(person);
  }

  public Person update(Person person) {
    return em.merge(person);
  }
  
  public void delete(Person person) {
    em.remove(person);
  }
}
