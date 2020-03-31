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
// TODO SSp: Since the class tests the kafka service please rename to
// com.employee.general.service.impl.kafka.EmployeeManagementKafkaServiceImplTest

// TODO SSp: please remove injectied RestService instance, we want to create a subsystem test here which uses the
// "real" interfaces. so Please inherit from RestServiceTest and use getServiceClientFactory to actually call the rest
// service via
// an rest client
public class EmployeeManagementRestServiceImplTest extends SystemDbTest {

  // TODO SSp: Please remove (see above)
  @Inject
  private EmployeemanagementRestService employeemanagementRestService;

  /**
   *
   */
  @Test
  // TODO SSp: Please rename to saveEmployeeViaKafkaService
  public void shouldSendEmployeeToKafkaAndSaveInDbWithListener() {

    // Arrange
    EmployeeEto employee = new EmployeeEto();
    employee.setAge(24);
    employee.setLocation("Chennai,India");
    employee.setName("Ashwin");
    employee.setValidEmployee(false);

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
    this.employeemanagementRestService.sendEmployeeToKafka(employee);

    Awaitility.await()
        // .atLeast(457, TimeUnit.MILLISECONDS).and().atMost(500, TimeUnit.MILLISECONDS)
        .until(() -> this.employeemanagementRestService.saveEmployee(employee) != null);

    // Assert
    EmployeeEto foundEmployee = this.employeemanagementRestService.getEmployee(1000000);
    assertThat(foundEmployee).isEqualToIgnoringGivenFields(employee, "id", "persistentEntity");

  }

}
