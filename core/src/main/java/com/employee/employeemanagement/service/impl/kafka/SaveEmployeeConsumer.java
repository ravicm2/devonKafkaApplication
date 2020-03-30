package com.employee.employeemanagement.service.impl.kafka;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageRetryOperations;

/**
 * @author ravicm
 *
 */
@Named
public class SaveEmployeeConsumer {

  private static final Logger LOG = LoggerFactory.getLogger(SaveEmployeeConsumer.class);

  @Inject
  private MessageRetryOperations messageRetryOperations;

  @Inject
  private MessageProcessor saveEmployeeMessageProcessor;

  /**
   * @param consumerRecord
   * @param acknowledgment
   * @throws Exception
   */
  @KafkaListener(topics = "${messaging.kafka.health.topicsToCheck}", groupId = "${messaging.kafka.consumer.groupId}", containerFactory = "kafkaListenerContainerFactory")
  public void consumer(ConsumerRecord<Object, Object> consumerRecord, Acknowledgment acknowledgment) {

    try {
      processMessageAndAcknowledgeListener(consumerRecord, acknowledgment);
    } catch (Exception e) {
      LOG.error("The Error while processing message: {} ", e);
    }
  }

  private void processMessageAndAcknowledgeListener(ConsumerRecord<Object, Object> consumerRecord,
      Acknowledgment acknowledgment) {

    this.messageRetryOperations.processMessageWithRetry(consumerRecord, this.saveEmployeeMessageProcessor);

    // Acknowledge the listener.
    acknowledgment.acknowledge();
  }
}
