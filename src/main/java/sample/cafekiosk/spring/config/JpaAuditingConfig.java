package sample.cafekiosk.spring.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configurable
public class JpaAuditingConfig {
}
