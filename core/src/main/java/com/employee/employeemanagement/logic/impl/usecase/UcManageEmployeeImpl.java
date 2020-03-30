package com.employee.employeemanagement.logic.impl.usecase;

import java.time.Instant;
import java.util.Objects;

import javax.inject.Named;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.devonfw.module.kafka.common.messaging.retry.api.RetryState;
import com.employee.employeemanagement.dataaccess.api.EmployeeEntity;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.logic.api.usecase.UcManageEmployee;
import com.employee.employeemanagement.logic.base.usecase.AbstractEmployeeUc;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Use case implementation for modifying and deleting Employees
 */
@Named
@Validated
@Transactional
public class UcManageEmployeeImpl extends AbstractEmployeeUc implements UcManageEmployee {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(UcManageEmployeeImpl.class);

  /**
   * The constructor.
   */
  public UcManageEmployeeImpl() {

    super();
  }

  @Override
  public boolean deleteEmployee(long employeeId) {

    EmployeeEntity employee = getEmployeeRepository().find(employeeId);
    getEmployeeRepository().delete(employee);
    LOG.debug("The employee with id '{}' has been deleted.", employeeId);
    return true;
  }

  @Override
  public EmployeeEto saveEmployee(EmployeeEto employee) {

    Objects.requireNonNull(employee, "employee");

    EmployeeEntity employeeEntity = getBeanMapper().map(employee, EmployeeEntity.class);

    // initialize, validate employeeEntity here if necessary
    EmployeeEntity resultEntity = getEmployeeRepository().save(employeeEntity);
    LOG.debug("Employee with id '{}' has been created.", resultEntity.getId());
    return getBeanMapper().map(resultEntity, EmployeeEto.class);
  }

  @Override
  public void sendEmployeeToKafka(EmployeeEto message) {

    try {

      String convertedMessage = new ObjectMapper().writer().writeValueAsString(message);

      ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>("sample-employee-topic", 0, "employee",
          convertedMessage);

      setHeaders(producerRecord);

      getMessageSender().sendMessage(producerRecord);

    } catch (Exception e) {
      LOG.error("Error while sending message: The error is {}", e);
    }

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
