package live.easytrain.application.serviceTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import live.easytrain.application.api.binder.ApiDataToEntities;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.exceptions.StationNotFoundException;
import live.easytrain.application.repository.StationRepo;
import live.easytrain.application.repository.TimetableRepo;
import live.easytrain.application.service.implentation.TimetableService;
import live.easytrain.application.service.interfaces.StationServiceInterface;
import live.easytrain.application.utils.DateTimeParserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TimetableServiceTest {
    @Mock
    private TimetableRepo timetableRepo;
    @Mock
    private ApiDataToEntities apiDataToEntities;
    @Mock
    private StationRepo stationRepo;
    @Mock
    private StationServiceInterface stationServiceInterface;
    @Mock
    private DateTimeParserUtils dateTimeParser;
    @InjectMocks
    private TimetableService timetableService;
    private String stationName;
    private LocalDate date;
    private LocalTime hour;
    private boolean recentChanges;
    private Integer evaNumber;
    private List<Timetable> timetables;
    private Station station;
    @BeforeEach
    public void setUp() {
        stationName = "Berlin Hbf";
        date = LocalDate.now();
        hour = LocalTime.now();
        recentChanges = false;
        evaNumber = 8011160;
        timetables = Arrays.asList(new Timetable(), new Timetable());
        station = new Station();
        station.setEvaNumber(evaNumber.toString());
    }
    @Test
    public void testSaveTimetableData_WhenTimetablesFound() {
        when(stationServiceInterface.evaNumberByStationName(stationName)).thenReturn(evaNumber);
        when(dateTimeParser.formatLocalDateToString(date)).thenReturn(date.toString());
        when(dateTimeParser.formatLocalTimeToString(hour)).thenReturn(hour.toString());
        when(apiDataToEntities.apiDataToTimetable(evaNumber, date.toString(), hour.toString(), recentChanges)).thenReturn(timetables);
        List<Timetable> result = timetableService.saveTimetableData(stationName, date, hour, recentChanges);
        assertEquals(timetables, result);
        verify(timetableRepo).saveAll(timetables);
    }
    @Test
    public void testSaveTimetableData_WhenNoTimetablesFound() {
        when(stationServiceInterface.evaNumberByStationName(stationName)).thenReturn(evaNumber);
        when(dateTimeParser.formatLocalDateToString(date)).thenReturn(date.toString());
        when(dateTimeParser.formatLocalTimeToString(hour)).thenReturn(hour.toString());
        when(apiDataToEntities.apiDataToTimetable(evaNumber, date.toString(), hour.toString(), recentChanges)).thenReturn(Collections.emptyList());
        List<Timetable> result = timetableService.saveTimetableData(stationName, date, hour, recentChanges);
        assertTrue(result.isEmpty());
        verify(timetableRepo, never()).saveAll(anyList());
    }
    @Test
    public void testFindAllEvaNumberByStationName() {
        when(stationRepo.findByStationNameStartingWith(stationName)).thenReturn(Arrays.asList(station));
        List<Station> result = timetableService.findAllEvaNumberByStationName(stationName);
        assertEquals(1, result.size());
        assertEquals(station, result.get(0));
    }
    @Test
    public void testEvaNumberByStationName_WhenStationFound() {
        when(stationRepo.findByStationName(stationName)).thenReturn(station);
        Integer result = timetableService.evaNumberByStationName(stationName);
        assertEquals(evaNumber, result);
    }
    @Test
    public void testEvaNumberByStationName_WhenStationNotFound() {
        when(stationRepo.findByStationName(stationName)).thenReturn(null);
        Exception exception = assertThrows(StationNotFoundException.class, () -> {
            timetableService.evaNumberByStationName(stationName);
        });
        assertEquals("Station not found: " + stationName, exception.getMessage());
    }
    @Test
    public void testFetchTimetableDataFromAPI() {
        when(stationRepo.findByStationName(stationName)).thenReturn(station);
        when(dateTimeParser.formatLocalDateToString(date)).thenReturn(date.toString());
        when(dateTimeParser.formatLocalTimeToString(hour)).thenReturn(hour.toString());
        when(apiDataToEntities.apiDataToTimetable(evaNumber, date.toString(), hour.toString(), false)).thenReturn(timetables);
        List<Timetable> result = timetableService.fetchTimetableDataFromAPI(stationName, date, hour);
        assertEquals(timetables, result);
    }
    @Test
    public void testGetAllTimetables() {
        when(timetableRepo.findAll()).thenReturn(timetables);
        List<Timetable> result = timetableService.getAllTimetables();
        assertEquals(timetables, result);
    }
    @Test
    public void testGetJourneyFromTimetableById_WhenJourneyFound() {
        Long journeyId = 1L;
        Timetable timetable = new Timetable();
        when(timetableRepo.findById(journeyId)).thenReturn(Optional.of(timetable));
        Timetable result = timetableService.getJourneyFromTimetableById(journeyId);
        assertEquals(timetable, result);
    }
    @Test
    public void testGetJourneyFromTimetableById_WhenJourneyNotFound() {
        Long journeyId = 1L;
        when(timetableRepo.findById(journeyId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            timetableService.getJourneyFromTimetableById(journeyId);
        });
        assertEquals("Journey not found: ", exception.getMessage());
    }
}
