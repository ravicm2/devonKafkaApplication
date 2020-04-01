package com.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import com.devonfw.module.jpa.dataaccess.api.AdvancedRevisionEntity;
import com.devonfw.module.jpa.dataaccess.impl.data.GenericRepositoryFactoryBean;
import com.devonfw.module.kafka.common.messaging.api.config.MessageReceiverConfig;
import com.devonfw.module.kafka.common.messaging.api.config.MessageSenderConfig;
import com.devonfw.module.kafka.common.messaging.retry.api.config.MessageDefaultRetryConfig;
import com.devonfw.module.kafka.common.messaging.trace.api.config.TraceConfig;

/**
 * Main entry point of this {@link SpringBootApplication}. Simply run this class to start this app.
 */
@SpringBootApplication
@EntityScan(basePackages = { "com.employee" }, basePackageClasses = { AdvancedRevisionEntity.class })
@EnableJpaRepositories(repositoryFactoryBeanClass = GenericRepositoryFactoryBean.class)
@EnableGlobalMethodSecurity(jsr250Enabled = true)
@Import({ MessageSenderConfig.class, MessageReceiverConfig.class, TraceConfig.class, MessageDefaultRetryConfig.class })
public class SpringBootApp {

  /**
   * Entry point for spring-boot based app
   *
   * @param args - arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(SpringBootApp.class, args);
  }
}
