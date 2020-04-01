package com.employee.employeemanagement.service.impl.kafka;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.employee.employeemanagement.logic.api.Employeemanagement;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
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
      // TODO SSp: In real world one have to decide for which errors a retry make sense and for which not
      // We do not have to stress this very much for the example, but suggestion would be to retry for errors during
      // json parsing and nothing else
      // so we catch JsonParseException and JsonMappingException here and nothing else.

      // Reply: ObjectMapper().readValue() expects IOException to be handled .
    } catch (IOException e) {
      LOG.error("Error while processing message. The error thrown is {}", e);
    }
  }

  private void convertAndSaveEmployee(ConsumerRecord<Object, Object> message) throws IOException {

    EmployeeEto convertedValue = new ObjectMapper().readValue(message.value().toString(), EmployeeEto.class);
    this.employeemanagement.saveEmployee(convertedValue);
  }

}
