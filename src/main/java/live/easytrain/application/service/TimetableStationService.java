package live.easytrain.application.service;


import live.easytrain.application.entity.Station;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.exceptions.StationNotFoundException;
import live.easytrain.application.external.binder.ApiDataToEntities;
import live.easytrain.application.repository.StationRepo;
import live.easytrain.application.repository.TimetableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TimetableStationService implements TimetableStationServiceInterface {
    private TimetableRepo timetableRepo;
    private ApiDataToEntities apiDataToEntities;
    private StationRepo stationRepo;

    @Autowired
    public TimetableStationService(StationRepo stationRepo, TimetableRepo timetableRepo, ApiDataToEntities apiDataToEntities) {
        this.timetableRepo = timetableRepo;
        this.apiDataToEntities = apiDataToEntities;
        this.stationRepo = stationRepo;
    }


//    @Override
//    public void saveTimetableData(String stationName, String date, String hour, boolean recentChanges) {
//        stationName = "Berlin Hbf";
//        System.out.println(stationName + " 1");
//        Integer evaNumber = evaNumberByStationName(findAllEvaNumberByStationName(stationName), stationName);
//        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(evaNumber, date, hour, false);
//        for (Timetable timetable : timetables) {
//            timetableRepo.save(timetable);
//            timetables.add(timetable);
//        }
//    }


    @Override
    public void saveTimetableData(String stationName, String date, String hour, boolean recentChanges) {
        List<Station> stations = findAllEvaNumberByStationName(stationName);
        if (stations.isEmpty()) {
            throw new StationNotFoundException("Station not found: " + stationName);
        }


        Integer evaNumber = evaNumberByStationName(findAllEvaNumberByStationName(stationName), stationName);

        List<Timetable> timetables = apiDataToEntities.apiDataToTimetable(evaNumber, date, hour, false);
        for (Timetable timetable : timetables) {
            timetableRepo.save(timetable);
        }
    }

    @Override
    public List<Station> findAllEvaNumberByStationName(String stationName) {
        return stationRepo.findByStationNameStartingWith(stationName);
    }
    private Integer evaNumberByStationName(List<Station> stations, String stationName) {
        Station stationTarget = null;
        if (stations.isEmpty()) {
            throw new StationNotFoundException("Station not found! 1,2");
        } else {
            for (Station station : stations) {
                if (station.getStationName().equalsIgnoreCase(stationName)) {
                    stationTarget = station;
                }
            }
        }
        if (stationTarget == null) {
            throw new StationNotFoundException("Station not found hello");
        }
        return Integer.valueOf(stationTarget.getEvaNumber());
    }

    @Override
    public Integer evaNumberByStationName(String stationName) {
        List<Station> stations = findAllEvaNumberByStationName(stationName);
        for (Station station : stations) {
            if (station.getStationName().equalsIgnoreCase(stationName)) {
                return Integer.valueOf(station.getEvaNumber());
            }
        }
        throw new StationNotFoundException("Station not found: " + stationName);
    }

}








