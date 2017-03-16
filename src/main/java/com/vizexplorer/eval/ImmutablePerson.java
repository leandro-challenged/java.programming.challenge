package com.vizexplorer.eval;

import java.util.Date;

public class ImmutablePerson extends Person {

  public ImmutablePerson(String name, String gender, Date birthDate)
  {
    super(name, gender, birthDate);
  }
  
  @Override
  public Date getBirthDate() 
  {
    return new Date(super.getBirthDate().getTime());
  }
  
  @Override
  public void setBirthDate(Date birthDate) 
  {
    error();
  }
  
  @Override
  public void setGender(String gender) 
  {
    error();
  }
  
  @Override
  public void setName(String name) 
  {
    error();
  }
  
  private void error() {
    throw new UnsupportedOperationException();
  }
}
