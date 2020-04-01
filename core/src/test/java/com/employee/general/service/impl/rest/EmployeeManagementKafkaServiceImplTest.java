package com.employee.general.service.impl.rest;

import java.time.Instant;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devonfw.module.kafka.common.messaging.retry.api.RetryState;
import com.devonfw.module.service.common.api.client.config.ServiceClientConfigBuilder;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.logic.api.to.EmployeeSearchCriteriaTo;
import com.employee.employeemanagement.service.api.rest.EmployeemanagementRestService;
import com.employee.general.service.base.test.RestServiceTest;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 */
@ExtendWith(SpringExtension.class)
public class EmployeeManagementKafkaServiceImplTest extends RestServiceTest {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(EmployeeManagementKafkaServiceImplTest.class);

  private EmployeemanagementRestService employeemanagementRestService;

  private EmployeeEto employee;

  private String convertedMessage;

  private ProducerRecord<Object, Object> producerRecord;

  @Override
  @BeforeEach
  protected void doSetUp() {

    // Arrange
    this.employee = new EmployeeEto();
    this.employee.setAge(24);
    this.employee.setLocation("Chennai,India");
    this.employee.setName("Ashwin");
    this.employee.setValidEmployee(false);

    try {

      this.convertedMessage = new ObjectMapper().writer().writeValueAsString(this.employee);
      this.producerRecord = new ProducerRecord<>("sample-employee-topic", 0, "employee", this.convertedMessage);

      setHeaders(this.producerRecord);

    } catch (Exception e) {
      LOG.error("Error while sending message: The error is {}", e);
    }

    this.employeemanagementRestService = getServiceClientFactory().create(EmployeemanagementRestService.class,
        new ServiceClientConfigBuilder().host("localhost").authBasic().userLogin("waiter").userPassword("waiter")
            .buildMap());

    super.doSetUp();
  }

  /**
   *
   */
  @Test
  public void saveEmployeeViaKafkaService() {

    // Act
    // TODO SSp: Sorry, obviously I was not clear what the test should do. The test should directly send a new employee
    // to save to the application.
    // It should then use the rest service to verify that the employee has been saved.
    // the test should work like the following

    // Act

    // kafkaTemplate.sendMessage(employeeEto);

    // Assert
    // EmployeeSearchCriteriaTo employeCriteria = new EmployeeSearchCriteriaTo();
    // employeCriteria.setName("Ashwin");
    // Awaitility.await()....until(() -> this.employeemgmtService.findEmployee(employeCriteria) != null);

    // i commented
    // this.employeemanagementRestService.sendEmployeeToKafka(this.employee);

    // Awaitility.await()
    // .atLeast(457, TimeUnit.MILLISECONDS).and().atMost(500, TimeUnit.MILLISECONDS)
    // .until(() -> this.employeemanagementRestService.saveEmployee(this.employee) != null);

    // Assert
    // EmployeeEto foundEmployee = this.employeemanagementRestService.getEmployee(1000000);
    // assertThat(foundEmployee).isEqualToIgnoringGivenFields(this.employee, "id", "persistentEntity");

    // new imple
    // Act
    getMessageSender().sendMessage(this.producerRecord);

    // Assert
    EmployeeSearchCriteriaTo employeCriteria = new EmployeeSearchCriteriaTo();
    employeCriteria.setName(this.employee.getName());
    employeCriteria.setLocation(this.employee.getLocation());

    Awaitility.await()
        .until(() -> this.employeemanagementRestService.findEmployees(employeCriteria).isEmpty() == false);

  }

  private void setHeaders(ProducerRecord<Object, Object> producerRecord) {

    // can add more custom headers

    // Retry headers
    setRetryHeaders(producerRecord);
  }

  private void setRetryHeaders(ProducerRecord<Object, Object> producerRecord) {

    // value for the headers.
    getMessageRetryContext().setRetryCount(2);
    getMessageRetryContext().setRetryNext(Instant.parse("2020-03-11T10:37:30.00Z"));
    getMessageRetryContext().setRetryReadCount(1);
    getMessageRetryContext().setRetryUntil(Instant.parse("2020-03-31T10:37:30.00Z"));
    getMessageRetryContext().setRetryState(RetryState.PENDING);

    // injecting retry headers.
    getMessageRetryContext().injectInto(producerRecord);
  }

}
