package test.pivotal.pal.tracker;

import io.pivotal.pal.tracker.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class TimeEntryControllerTest {
    private TimeEntryRepository timeEntryRepository;
    private TimeEntryController controller;
    private CounterService counter;
    private GaugeService gauge;

    @Before
    public void setUp() throws Exception {
        timeEntryRepository = mock(TimeEntryRepository.class);
        counter = mock(CounterService.class);
        gauge = mock(GaugeService.class);
        controller = new TimeEntryController(timeEntryRepository, counter, gauge);
    }

    @Test
    public void testCreate() throws Exception {
        TimeEntry timeEntryToCreate = new TimeEntry(123L, 456L, LocalDate.parse("2017-01-08"), 8);
        TimeEntry expectedResult = new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8);
        doReturn(expectedResult)
            .when(timeEntryRepository)
            .create(any(TimeEntry.class));


        ResponseEntity response = controller.create(timeEntryToCreate);


        verify(timeEntryRepository).create(timeEntryToCreate);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expectedResult);
    }

    @Test
    public void testRead() throws Exception {
        TimeEntry expected = new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8);
        doReturn(expected)
            .when(timeEntryRepository)
            .find(1L);

        ResponseEntity<TimeEntry> response = controller.read(1L);

        verify(timeEntryRepository).find(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void testRead_NotFound() throws Exception {
        doReturn(null)
            .when(timeEntryRepository)
            .find(1L);

        ResponseEntity<TimeEntry> response = controller.read(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testList() throws Exception {
        List<TimeEntry> expected = asList(
            new TimeEntry(1L, 123L, 456L, LocalDate.parse("2017-01-08"), 8),
            new TimeEntry(2L, 789L, 321L, LocalDate.parse("2017-01-07"), 4)
        );
        doReturn(expected).when(timeEntryRepository).list();

        ResponseEntity<List<TimeEntry>> response = controller.list();

        verify(timeEntryRepository).list();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void testUpdate() throws Exception {
        TimeEntry expected = new TimeEntry(1L, 987L, 654L, LocalDate.parse("2017-01-07"), 4);
        doReturn(expected)
            .when(timeEntryRepository)
            .update(eq(1L), any(TimeEntry.class));

        ResponseEntity response = controller.update(1L, expected);

        verify(timeEntryRepository).update(1L, expected);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    public void testUpdate_NotFound() throws Exception {
        doReturn(null)
            .when(timeEntryRepository)
            .update(eq(1L), any(TimeEntry.class));

        ResponseEntity response = controller.update(1L, new TimeEntry(1, 1, 1, null, 1));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testDelete() throws Exception {
        ResponseEntity<TimeEntry> response = controller.delete(1L);
        verify(timeEntryRepository).delete(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
