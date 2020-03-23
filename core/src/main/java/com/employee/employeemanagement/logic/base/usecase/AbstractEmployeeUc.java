package com.employee.employeemanagement.logic.base.usecase;

import javax.inject.Inject;

import com.devonfw.module.kafka.common.messaging.api.client.MessageSender;
import com.devonfw.module.kafka.common.messaging.api.client.converter.MessageConverter;
import com.devonfw.module.kafka.common.messaging.retry.impl.MessageRetryContext;
import com.employee.employeemanagement.dataaccess.api.repo.EmployeeRepository;
import com.employee.general.logic.base.AbstractUc;

/**
 * Abstract use case for Employees, which provides access to the commonly necessary data access objects.
 */
public class AbstractEmployeeUc extends AbstractUc {

  /**
   * The constructor.
   */
  public AbstractEmployeeUc() {

    super();
  }

  /** @see #getEmployeeRepository() */
  @Inject
  private EmployeeRepository employeeRepository;

  @Inject
  private MessageSender messageSender;

  @Inject
  private MessageConverter messageConverter;

  @Inject
  private MessageRetryContext messageRetryContext;

  /**
   * Returns the field 'employeeRepository'.
   *
   * @return the {@link EmployeeRepository} instance.
   */
  public EmployeeRepository getEmployeeRepository() {

    return this.employeeRepository;
  }

  /**
   * @return messageSender
   */
  public MessageSender getMessageSender() {

    return this.messageSender;
  }

  /**
   * @return messageConverter
   */
  public MessageConverter getMessageConverter() {

    return this.messageConverter;
  }

  /**
   * @return messageRetryContext
   */
  public MessageRetryContext getMessageRetryContext() {

    return this.messageRetryContext;
  }

}
