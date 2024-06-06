package live.easytrain.application.service;

import live.easytrain.application.entity.Station;
import live.easytrain.application.exceptions.StationNotFoundException;
import live.easytrain.application.repository.StationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService implements StationServiceInterface {

    private StationRepo stationRepo;

    @Autowired
    public StationService(StationRepo stationRepo) {
        this.stationRepo = stationRepo;
    }

    // returns all stations starting with same city name
    @Override
    public List<Station> findAllEvaNumberByStationName(String stationName) {
        return stationRepo.findByStationNameStartingWith(stationName);
    }

    // Returns the evaNumber by given station name
    @Override
    public Integer evaNumberByStationName(String stationName) {
        Station station = stationRepo.findByStationName(stationName);

        if (station != null) {
            return Integer.valueOf(station.getEvaNumber());
        }
        throw new StationNotFoundException("Station not found: " + stationName);
    }

    // Returns the evaNumber by given list of stations and station name
    @Override
    public Integer evaNumberByStationName(List<Station> stations, String stationName) {
        Station stationTarget = null;
        if (stations.isEmpty()) {
            throw new StationNotFoundException("Station not found!");
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
    public List<Station> findAllStationByStationCode(String stationCode) {
        return stationRepo.findAllByStationCode(stationCode);
    }

}
