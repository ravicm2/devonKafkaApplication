package com.employee.employeemanagement.service.impl.kafka;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.producer.ProducerRecord;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.employee.employeemanagement.logic.api.Employeemanagement;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ravicm
 *
 */
// TODO Review: This class should be typed with Message<T>/ConsumerRecord<T> T is the Payload type (EmployeeEto.class)

// TODO Review-> REPLY: Both cannot be done because . as i said the MessageRetryTemplate class calls internally this
// method which
// already take ProducerRecord as parameter which is used for enqueRetry to post it again . There will be multiple
// conversion at the module if we change it to consumerRecord.

// ProducerRecord is kept in the idea of where the user can do whatever they want while processing instead of just
// sending EmployeeType alone.
// @Transactional
@Named
public class SaveEmployeMessageProcessor implements MessageProcessor {

  @Inject
  private Employeemanagement employeemanagement;

  @Override
  public void processMessage(ProducerRecord<Object, Object> message) {

    EmployeeEto convertedValue = null;
    try {
      convertedValue = new ObjectMapper().readValue(message.value().toString(), EmployeeEto.class);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.employeemanagement.saveEmployee(convertedValue);
  }

}
