package com.employee.employeemanagement.service.impl.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.data.domain.Page;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.employee.employeemanagement.logic.api.Employeemanagement;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.logic.api.to.EmployeeSearchCriteriaTo;
import com.employee.employeemanagement.service.api.rest.EmployeemanagementRestService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The service implementation for REST calls in order to execute the logic of component {@link Employeemanagement}.
 */
@Named("EmployeemanagementRestService")
public class EmployeemanagementRestServiceImpl implements EmployeemanagementRestService, MessageProcessor {

  @Inject
  private Employeemanagement employeemanagement;

  @Override
  public EmployeeEto getEmployee(long id) {

    return this.employeemanagement.findEmployee(id);
  }

  @Override
  public EmployeeEto saveEmployee(EmployeeEto employee) {

    return this.employeemanagement.saveEmployee(employee);
  }

  @Override
  public void deleteEmployee(long id) {

    this.employeemanagement.deleteEmployee(id);
  }

  @Override
  public Page<EmployeeEto> findEmployees(EmployeeSearchCriteriaTo searchCriteriaTo) {

    return this.employeemanagement.findEmployees(searchCriteriaTo);
  }

  @Override
  public void sendEmployeeToKafka(EmployeeEto message) {

    System.out.println("Inside Service Impl");
    this.employeemanagement.sendEmployeeToKafka(message);

  }

  @Override
  public String hello() {

    return "hello";
  }

  @Override
  public void processMessage(ProducerRecord<Object, Object> message) {

    EmployeeEto convertedValue = null;
    try {

      convertedValue = new ObjectMapper().readValue(message.value().toString(), EmployeeEto.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.employeemanagement.saveEmployee(convertedValue);

  }
}