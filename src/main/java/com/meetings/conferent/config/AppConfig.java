package com.meetings.conferent.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("com.meetings.conferent.dao"),
      @ComponentScan("com.meetings.conferent.service") })
public class AppConfig {

}
