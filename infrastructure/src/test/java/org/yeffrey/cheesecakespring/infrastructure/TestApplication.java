package org.yeffrey.cheesecakespring.infrastructure;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(scanBasePackages = {"org.yeffrey.cheesecakespring"})
public class TestApplication {
    @Configuration
    @EntityScan({"org.yeffrey.cheesecakespring"})
    public static class TestConfiguration {
    }
}
