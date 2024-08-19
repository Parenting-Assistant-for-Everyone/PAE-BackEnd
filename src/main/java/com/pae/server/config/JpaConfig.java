package com.pae.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.pae.server", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.pae.server.chat.repository.mongo.*"))
public class JpaConfig {
}
