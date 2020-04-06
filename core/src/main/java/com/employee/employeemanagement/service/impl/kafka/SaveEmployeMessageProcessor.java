package com.employee.employeemanagement.service.impl.kafka;

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

    // TODO SSp: In real world one have to decide for which errors a retry make sense and for which not
    // We do not have to stress this very much for the example, but suggestion would be to retry for errors during
    // json parsing and nothing else
    // so we catch JsonParseException and JsonMappingException here and nothing else.

    // Reply: ObjectMapper().readValue() expects IOException to be handled .

    // Reply SSp: Ok the problem is, that if catch and consume IOException here, IOExceptions from the logic layer
    // will also not go into the retry logic.
    // I proposed a good change to better handle this below.

    EmployeeEto convertedValue = null;
    try {
      convertedValue = new ObjectMapper().readValue(message.value().toString(), EmployeeEto.class);
    } catch (Exception e) {
      // Since we catch all Exceptions here no retries will be done for errors during conversion.
      LOG.warn("Message conversion failed. Message will be ignored.", e);
    }
    if (convertedValue != null) {
      this.employeemanagement.saveEmployee(convertedValue);
    }
  }

}
