package io.pivotal.pal.tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    Map<Long, TimeEntry> store = new HashMap<>();
    Long id;

    public InMemoryTimeEntryRepository() {
        this.id = 0L;
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        this.id++;

        TimeEntry timeEntryWithId = timeEntry.toBuilder()
                .id(id)
                .build();

        store.put(this.id, timeEntryWithId);
        return timeEntryWithId;
    }

    @Override
    public TimeEntry find(long id) {
        return store.get(id) != null ? store.get(id) : null;
    }

    @Override
    public List<TimeEntry> list() {
        return store.values().stream().collect(Collectors.toList());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if(store.get(id) == null)
            return null;
        store.put(id, timeEntry.toBuilder().id(id).build());
        return store.get(id);
    }

    @Override
    public void delete(long id) {
        store.remove(id);
    }
}
