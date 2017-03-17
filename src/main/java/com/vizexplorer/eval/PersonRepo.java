package com.vizexplorer.eval;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

public class PersonRepo 
{
  private EntityManager em;

  public PersonRepo(EntityManager em) 
  {
    this.em = em;
  }

  public Person get(String id) 
  {
    Person found = em.find(Person.class, id);
    
    if (found == null)
    {
      throw new EntityNotFoundException("It was not found a person with id " + id);
    }
    return found;
  }

  public void create(Person person) 
  {
    em.persist(person);
  }

  public Person update(Person person) 
  {
    return em.merge(person);
  }
  
  public void delete(Person person) 
  {
    em.remove(person);
  }
}
