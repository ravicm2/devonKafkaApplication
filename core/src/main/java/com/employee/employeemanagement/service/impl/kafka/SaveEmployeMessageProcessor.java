package com.employee.employeemanagement.service.impl.kafka;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.employee.employeemanagement.logic.api.Employeemanagement;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

/**
 * @author ravicm
 *
 */
@Named
public class SaveEmployeMessageProcessor implements MessageProcessor {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(SaveEmployeMessageProcessor.class);

  @Inject
  private Employeemanagement employeemanagement;

  @Override
  public void processMessage(ConsumerRecord<Object, Object> message) {

    try {
      convertAndSaveEmployee(message);
    } catch (IOException e) {
      LOG.error("Error while processing message. The error thrown is {}", e);
    }
  }

  private void convertAndSaveEmployee(ConsumerRecord<Object, Object> message)
      throws IOException, JsonParseException, JsonMappingException {

    EmployeeEto convertedValue = new ObjectMapper().readValue(message.value().toString(), EmployeeEto.class);
    this.employeemanagement.saveEmployee(convertedValue);
  }

}
