package com.employee.employeemanagement.logic.impl;

import javax.inject.Named;

import org.slf4j.LoggerFactory;

import com.devonfw.module.kafka.common.messaging.api.client.converter.MessageConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

/**
 * TODO SSP: Can be removed
 */
@Named
public class MessageConverterImpl implements MessageConverter {

  private static final Logger LOG = (Logger) LoggerFactory.getLogger(MessageConverterImpl.class);

  @SuppressWarnings("unchecked")
  @Override
  public <T> T convertMessage(T message) {

    String value = null;
    try {
      value = new ObjectMapper().writer().writeValueAsString(message);

    } catch (JsonProcessingException e) {
      LOG.error(e.getMessage());
    }

    return (T) value;
  }

}
