package live.easytrain.application.service;


import jakarta.transaction.Transactional;
import live.easytrain.application.entity.JourneyUpdate;
import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface JourneyUpdateServiceInterface {
    List<JourneyUpdate> getJourneyUpdatesByScheduleId(String scheduleId);
    List<JourneyUpdate> saveJourneyUpdates(String stationName, boolean recentChanges);
    List<JourneyUpdate> findAll();
}
