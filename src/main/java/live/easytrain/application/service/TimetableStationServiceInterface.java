package live.easytrain.application.service;

import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;


import java.util.List;

public interface TimetableStationServiceInterface {
    void saveTimetableData(String stationName, String date, String hour, boolean recentChanges);
}
