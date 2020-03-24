package com.employee.employeemanagement.service.impl.kafka;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  private static final Logger LOG = LoggerFactory.getLogger(SaveEmployeeConsumer.class);

  @Inject
  // TODO SSp: It turns out that the messageparser itself is not required for devon4j-kafka, so we should remove it from
  // the library. Users of the modul should implement the parsing by themself.
  private MessageParser messageParser;

  @Inject
  private MessageRetryOperations messageRetryOperations;

  @Inject
  // TODO SSp: Please change to SaveEmployeeMessageProccesor, normally Interfaces are fine but in this case it is
  // important which implementation does the processing since it is part of the business logic.
  private MessageProcessor messageProcessor;

  /**
   * @param consumerRecord
   * @throws Exception
   */
  @KafkaListener(topics = "${messaging.kafka.health.topicsToCheck}", groupId = "${messaging.kafka.consumer.groupId}")
  public void consumer(ConsumerRecord<Object, Object> consumerRecord) {

    // TODO SSp: Please remove
    System.out.println("the consumed message-> " + consumerRecord.toString());

    try {
      parseAndProcessMessageIfPresent(consumerRecord);
    } catch (Exception e) {
      // TODO SSp: What is the "contract" of .messageRetryOperations.processMessageWithRetry? When will it throw an
      // exception? After the last retry fails?
      // TODO SSp: Please use logger
      System.out.println("exception " + e.getMessage());
    }
  }

  private void parseAndProcessMessageIfPresent(ConsumerRecord<Object, Object> consumerRecord) throws Exception {

    // TODO SSp: I don't think this can happen. So I would not check.
    if (consumerRecord != null) {

      // TODO SSp: please remove
      System.out.println("before parsing");

      String parsedKafkaRecordValue = this.messageParser.parseMessage(consumerRecord, String.class);

      // TODO SSp: please remove
      System.out.println("after parsing");

      // TODO SSp: I'm still struggling that we have to convert the consumerrecord to a producerecord to process it.
      // Please change the api of MessageRetryOperations#processMessageWithRetry(ProducerRecord...) to
      // processMessageWithRetry(ConsumerRecord...)
      // Inside processMessageWithRetry you are currently creating a new producerrecord from the given producerrecord.
      // The code there looks very similar to what happens here. So we can improve that.
      //
      // Please provide a Interface KafakRecordSupport with one method:
      // ProducerRecord createRecordForRetry(ConsumerRecord record)
      //
      // Create an implementation DefaultKafakRecordSupport which converts the consumerRecord which is provided by the
      // (sample) application to a producerrecord which is used by devon-kafka.
      // The implementation converts the provided consumerrecord to the producerrecord and sets the retry topic.
      // Since we have a bean/interface users may overwrite it if necessary.
      // MessageRetryTemplate should work with the consumerRecord as much as possible and only convert the message to
      // the produceRecord if it is trying to enqueeue it for retry
      // Especially MessageProcessor#processMessage(ProducerRecord<Object, Object> message) should be changed to
      // processMessage(ConsumerRecord<Object, Object> message)

      ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(consumerRecord.topic(),
          consumerRecord.partition(), consumerRecord.key(), parsedKafkaRecordValue, consumerRecord.headers());

      this.messageRetryOperations.processMessageWithRetry(producerRecord, this.messageProcessor);

      // TODO SSp: Please remove
      System.out.println("After processing");
    } else {
      LOG.error("No message present for given topic and groupId");
    }
  }

}
