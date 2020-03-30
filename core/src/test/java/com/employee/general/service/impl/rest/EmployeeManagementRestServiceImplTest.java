package com.employee.general.service.impl.rest;

import javax.inject.Inject;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.devonfw.module.test.common.base.SystemDbTest;
import com.employee.SpringBootApp;
import com.employee.employeemanagement.logic.api.to.EmployeeEto;
import com.employee.employeemanagement.service.api.rest.EmployeemanagementRestService;

/**
 *
 */
@SpringBootTest(classes = SpringBootApp.class)
public class EmployeeManagementRestServiceImplTest extends SystemDbTest {

  @Inject
  private EmployeemanagementRestService employeemanagementRestService;

  /**
   *
   */
  @Test
  public void shouldSendEmployeeToKafkaAndSaveInDbWithListener() {

    // Arrange
    EmployeeEto employee = new EmployeeEto();
    employee.setAge(24);
    employee.setLocation("Chennai,India");
    employee.setName("Ashwin");
    employee.setValidEmployee(false);

    // Act
    this.employeemanagementRestService.sendEmployeeToKafka(employee);

    Awaitility.await()
        // .atLeast(457, TimeUnit.MILLISECONDS).and().atMost(500, TimeUnit.MILLISECONDS)
        .until(() -> this.employeemanagementRestService.saveEmployee(employee) != null);

    // Assert
    EmployeeEto foundEmployee = this.employeemanagementRestService.getEmployee(1000000);
    assertThat(foundEmployee).isEqualToIgnoringGivenFields(employee, "id", "persistentEntity");

  }

}
