package com.employee.employeemanagement.logic.impl;

import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.devonfw.module.kafka.common.messaging.api.client.parser.MessageParser;
import com.employee.employeemanagement.logic.base.usecase.AbstractEmployeeUc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

// TODO SSP: can be removed
/**
 * @author ravicm
 *
 */
@Named
public class MessageParserImpl extends AbstractEmployeeUc implements MessageParser {

  @Override
  public <T> T parseMessage(ConsumerRecord<?, ?> consumerRecord, Class<T> payloadClassName) throws Exception {

    return new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .convertValue(consumerRecord.value(), payloadClassName);
  }

}
