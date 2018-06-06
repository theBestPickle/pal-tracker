package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {
    private final static int MAX_DB_ENTRIES = 5;
    private final TimeEntryRepository repository;

    public TimeEntryHealthIndicator(TimeEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Health health() {
        if(repository.list().size() < MAX_DB_ENTRIES)
            return Health.up().build();
        return Health.down().build();
    }
}
