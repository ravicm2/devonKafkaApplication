package com.employee.employeemanagement.service.impl.kafka;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import com.devonfw.module.kafka.common.messaging.api.client.parser.MessageParser;
import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageProcessor;
import com.devonfw.module.kafka.common.messaging.retry.api.client.MessageRetryOperations;

/**
 * @author ravicm
 *
 */
@Named
public class SaveEmployeeConsumer {

  @Inject
  private MessageParser messageParser;

  @Inject
  private MessageRetryOperations messageRetryOperations;

  @Inject
  private MessageProcessor messageProcessor;

  /**
   * @param consumerRecord
   * @throws Exception
   */
  @KafkaListener(topics = "${messaging.kafka.health.topicsToCheck}", groupId = "${messaging.kafka.consumer.groupId}")
  public void consumer(ConsumerRecord<Object, Object> consumerRecord) {

    System.out.println("the consumed message-> " + consumerRecord.toString());

    try {
      parseAndProcessMessageIfPresent(consumerRecord);
    } catch (Exception e) {
      System.out.println("exception " + e.getMessage());
    }
  }

  private void parseAndProcessMessageIfPresent(ConsumerRecord<Object, Object> consumerRecord) throws Exception {

    if (consumerRecord != null) {

      System.out.println("before parsing");

      String parsedKafkaRecordValue = this.messageParser.parseMessage(consumerRecord, String.class);

      System.out.println("after parsing");

      ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(consumerRecord.topic(),
          consumerRecord.partition(), consumerRecord.key(), parsedKafkaRecordValue, consumerRecord.headers());

      this.messageRetryOperations.processMessageWithRetry(producerRecord, this.messageProcessor);

      System.out.println("After processing");
    }
  }

}
