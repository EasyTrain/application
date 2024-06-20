package live.easytrain.application.serviceTest;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import live.easytrain.application.api.binder.ApiDataToEntities;
import live.easytrain.application.entity.JourneyUpdate;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.repository.JourneyUpdateRepo;
import live.easytrain.application.service.implentation.JourneyUpdateService;
import live.easytrain.application.service.interfaces.StationServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class JourneyUpdateServiceTest {
    @Mock
    private ApiDataToEntities apiDataToEntities;
    @Mock
    private JourneyUpdateRepo journeyUpdateRepo;
    @Mock
    private StationServiceInterface stationService;
    @InjectMocks
    private JourneyUpdateService journeyUpdateService;
    private String scheduleId;
    private String stationName;
    private Integer evaNumber;
    private boolean recentChanges;
    private List<JourneyUpdate> journeyUpdates;
    private List<Timetable> timetables;
    private JourneyUpdate previousUpdate;
    private JourneyUpdate currentUpdate;
    @BeforeEach
    public void setUp() {
        scheduleId = "-7937947848915178179-2406191255-12";
        stationName = "Berlin Hbf";
        evaNumber = 8011160;
        recentChanges = true;
        previousUpdate = new JourneyUpdate();
        previousUpdate.setDelay("5");
        currentUpdate = new JourneyUpdate();
        currentUpdate.setDelay("10");
        journeyUpdates = Arrays.asList(previousUpdate, currentUpdate);
        timetables = Arrays.asList(new Timetable(), new Timetable());
        // Set a non-null delay value
        Timetable timetable1 = new Timetable();
        timetable1.setDelay("10");
        Timetable timetable2 = new Timetable();
        timetable2.setDelay("20");
        timetables = Arrays.asList(timetable1, timetable2);
    }

    @Test
    public void testGetJourneyUpdatesByScheduleId() {
        when(journeyUpdateRepo.findByScheduleId(scheduleId)).thenReturn(journeyUpdates);
        List<JourneyUpdate> result = journeyUpdateService.getJourneyUpdatesByScheduleId(scheduleId);
        assertEquals(2, result.size());
        verify(journeyUpdateRepo).saveAll(anyList());
    }
    @Test
    public void testSaveJourneyUpdates_WhenTimetablesFound() {
        when(stationService.evaNumberByStationName(stationName)).thenReturn(evaNumber);
        when(apiDataToEntities.apiDataToTimetable(evaNumber, null, null, recentChanges)).thenReturn(timetables);
        List<JourneyUpdate> result = journeyUpdateService.saveJourneyUpdates(stationName, recentChanges);
        System.out.println("Result size: " + result.size());
        result.forEach(update -> System.out.println("JourneyUpdate: " + update));
        assertFalse(result.isEmpty());
        verify(journeyUpdateRepo).saveAll(anyList());
    }
    @Test
    public void testSaveJourneyUpdates_WhenNoTimetablesFound() {
        when(stationService.evaNumberByStationName(stationName)).thenReturn(evaNumber);
        when(apiDataToEntities.apiDataToTimetable(evaNumber, null, null, recentChanges)).thenReturn(new ArrayList<>());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            journeyUpdateService.saveJourneyUpdates(stationName, recentChanges);
        });
        assertEquals("No ICE found on trajectory", exception.getMessage());
    }
    @Test
    public void testFindAll_WhenTimetablesFound() {
        when(stationService.evaNumberByStationName(stationName)).thenReturn(evaNumber);
        when(apiDataToEntities.apiDataToTimetable(evaNumber, null, null, true)).thenReturn(timetables);

        List<JourneyUpdate> result = journeyUpdateService.findAll(stationName);

        System.out.println("Result size: " + result.size());
        result.forEach(update -> System.out.println("JourneyUpdate: " + update));

        assertFalse(result.isEmpty());
    }
    @Test
    public void testFindAll_WhenNoTimetablesFound() {
        when(stationService.evaNumberByStationName(stationName)).thenReturn(evaNumber);
        when(apiDataToEntities.apiDataToTimetable(evaNumber, null, null, true)).thenReturn(new ArrayList<>());
        List<JourneyUpdate> result = journeyUpdateService.findAll(stationName);
        assertTrue(result.isEmpty());
    }
}

