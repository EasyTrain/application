package live.easytrain.application.service;

import live.easytrain.application.entity.Station;

import java.util.List;

public interface StationServiceInterface {

    List<Station>findAllEvaNumberByStationName(String stationName);
    Integer evaNumberByStationName(String stationName);
    Integer evaNumberByStationName(List<Station> stations, String stationName);

    List<Station> findAllStationByStationCode(String stationCode);
}
