package com.employee.employeemanagement.logic.api.to;

import com.devonfw.module.basic.common.api.query.StringSearchConfigTo;
import com.employee.general.common.api.to.AbstractSearchCriteriaTo;

/**
 * {@link SearchCriteriaTo} to find instances of {@link com.employee.employeemanagement.common.api.Employee}s.
 */
public class EmployeeSearchCriteriaTo extends AbstractSearchCriteriaTo {

  private static final long serialVersionUID = 1L;

  private String name;

  private Integer age;

  private String location;

  private Boolean validEmployee;

  private StringSearchConfigTo nameOption;

  private StringSearchConfigTo locationOption;

  /**
   * @return nameId
   */

  public String getName() {

    return name;
  }

  /**
   * @param name setter for name attribute
   */

  public void setName(String name) {

    this.name = name;
  }

  /**
   * @return ageId
   */

  public Integer getAge() {

    return age;
  }

  /**
   * @param age setter for age attribute
   */

  public void setAge(Integer age) {

    this.age = age;
  }

  /**
   * @return locationId
   */

  public String getLocation() {

    return location;
  }

  /**
   * @param location setter for location attribute
   */

  public void setLocation(String location) {

    this.location = location;
  }

  /**
   * @return validEmployeeId
   */

  public Boolean getValidEmployee() {

    return validEmployee;
  }

  /**
   * @param validEmployee setter for validEmployee attribute
   */

  public void setValidEmployee(Boolean validEmployee) {

    this.validEmployee = validEmployee;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getName() name}.
   */
  public StringSearchConfigTo getNameOption() {

    return this.nameOption;
  }

  /**
   * @param nameOption new value of {@link #getNameOption()}.
   */
  public void setNameOption(StringSearchConfigTo nameOption) {

    this.nameOption = nameOption;
  }

  /**
   * @return the {@link StringSearchConfigTo} used to search for {@link #getLocation() location}.
   */
  public StringSearchConfigTo getLocationOption() {

    return this.locationOption;
  }

  /**
   * @param locationOption new value of {@link #getLocationOption()}.
   */
  public void setLocationOption(StringSearchConfigTo locationOption) {

    this.locationOption = locationOption;
  }

}
