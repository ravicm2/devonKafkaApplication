package com.employee.employeemanagement.common.api;

import com.employee.general.common.api.ApplicationEntity;

public interface Employee extends ApplicationEntity {

  /**
   * @return nameId
   */

  public String getName();

  /**
   * @param name setter for name attribute
   */

  public void setName(String name);

  /**
   * @return ageId
   */

  public Integer getAge();

  /**
   *
   * 
   * @param age setter for
   * 
   *        age attribute
   */

  public void setAge(Integer age);

  /**
   * @return locationId
   */

  public String getLocation();

  /**
   * @param location setter for location attribute
   */

  public void setLocation(String location);

  /**
   * @return validEmployeeId
   */

  public Boolean getValidEmployee();

  /**
   * @param validEmployee setter for validEmployee attribute
   */

  public void setValidEmployee(Boolean validEmployee);

}
