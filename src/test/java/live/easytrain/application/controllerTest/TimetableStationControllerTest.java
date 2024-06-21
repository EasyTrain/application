package live.easytrain.application.controllerTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import live.easytrain.application.controller.TimetableStationController;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.service.interfaces.StationServiceInterface;
import live.easytrain.application.service.interfaces.TimetableServiceInterface;
import live.easytrain.application.utils.DateTimeParserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TimetableStationControllerTest {
    private MockMvc mockMvc;
    @Mock
    private TimetableServiceInterface timetableService;
    @Mock
    private StationServiceInterface stationService;
    @Mock
    private DateTimeParserUtils dateTimeParser;
    @InjectMocks
    private TimetableStationController timetableStationController;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(timetableStationController).build();
    }
    @Test
    public void testShowTimetableForm() throws Exception {
        List<Station> mockStations = List.of(new Station());
        when(stationService.findAllStationByStationCode("ICE")).thenReturn(mockStations);
        mockMvc.perform(get("/timetables"))
                .andExpect(status().isOk())
                .andExpect(view().name("timetable-lookup"))
                .andExpect(model().attributeExists("stations"))
                .andExpect(model().attributeExists("timetable"));
        verify(stationService, times(1)).findAllStationByStationCode("ICE");
    }
    @Test
    public void testSaveTimetablesData_NoTrainsFound() throws Exception {
        String stationName = "Berlin Hbf";
        String time = "10:00";
        boolean recentChanges = false;
        when(timetableService.saveTimetableData(eq(stationName), any(LocalDate.class), any(), eq(recentChanges)))
                .thenReturn(Collections.emptyList());
        mockMvc.perform(post("/timetables/display-timetable")
                        .param("stationName", stationName)
                        .param("time", time)
                        .param("recentChanges", String.valueOf(recentChanges)))
                .andExpect(status().isOk())
                .andExpect(view().name("timetable-error"))
                .andExpect(model().attributeExists("error"));
        verify(timetableService, times(1)).saveTimetableData(eq(stationName), any(LocalDate.class), any(), eq(recentChanges));
    }
    @Test
    public void testSaveTimetablesData_TimetablesFound() throws Exception {
        String stationName = "Berlin Hbf";
        String time = "10:00";
        boolean recentChanges = false;
        Timetable mockTimetable = new Timetable();
        when(timetableService.saveTimetableData(eq(stationName), any(LocalDate.class), any(), eq(recentChanges)))
                .thenReturn(List.of(mockTimetable));
        mockMvc.perform(post("/timetables/display-timetable")
                        .param("stationName", stationName)
                        .param("time", time)
                        .param("recentChanges", String.valueOf(recentChanges)))
                .andExpect(status().isOk())
                .andExpect(view().name("timetable"))
                .andExpect(model().attributeExists("timetables"));
        verify(timetableService, times(1)).saveTimetableData(eq(stationName), any(LocalDate.class), any(), eq(recentChanges));
    }
}
