package live.easytrain.application.external.binder;

import live.easytrain.application.config.XmlResponse;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.external.entity.SType;
import live.easytrain.application.external.entity.TimetableType;
import live.easytrain.application.external.entity.TlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiDataToEntities {

    private XmlResponse xmlResponse;

    @Autowired
    public ApiDataToEntities(XmlResponse xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    /*
     * stationNumber = evaNo, so it is mandatory.
     * recentChanges = true -> for Recent changes Timetables API requests and
     * recentChanges = false -> for other requests
     * */
    public List<Timetable> apiDataToTimetable(Integer stationNumber, String date, String hour, boolean recentChanges) {

        List<Timetable> timetables = new ArrayList<>();
        List<SType> sTypesICE = new ArrayList<>();
        TimetableType timetableType = null;

        if (stationNumber == null) {
            throw new IllegalArgumentException("stationNumber must not be null");
        } else if (date.isEmpty() && hour.isEmpty() && !recentChanges) {
            timetableType = xmlResponse.fetchXmlResponse("fchg" + "/" + stationNumber);
        } else if (date.isEmpty() && hour.isEmpty()) {
            timetableType = xmlResponse.fetchXmlResponse("rchg" + "/" + stationNumber);
        } else {
            timetableType = xmlResponse.fetchXmlResponse("plan" + "/" + stationNumber + "/" + date + "/" + hour);
        }

        for (SType sType : timetableType.getS()) {
            TlType tlType = sType.getTl();
            if (tlType != null && tlType.getC().equals("ICE")) {
                sTypesICE.add(sType);
            }
        }

        if (sTypesICE.isEmpty()) {
            throw new RuntimeException("No ICE found on trajectory");
        } else{
            for(SType sTypeIce : sTypesICE){

                String startingStation ="";
                String endingStation = "";

                // The train start stations are on <ar> tag and the destination on <dp> tag
                // The stations are separated by | and regex doesn't like this special char
                if(sTypeIce.getAr() == null) {
                    startingStation = String.valueOf(stationNumber);
                } else {
                    String arStations = sTypeIce.getAr().getPpth().replace("|", "@");
                    String[] arStartingStation = arStations.split("@");
                    startingStation = arStartingStation[0];
                }

                if (sTypeIce.getDp() == null){
                    endingStation = String.valueOf(stationNumber);
                } else {
                    String dpStations = sTypeIce.getDp().getPpth().replace("|", "@");
                    String [] dpEndStation   = dpStations.split("@");
                    endingStation = dpEndStation[dpEndStation.length-1];
                }

                String delay = "Störung"; // map the delay field
                String estimatedTime = sTypeIce.getAr().getPt();
                String arrivalTime = sTypeIce.getAr().getPt(); // To confirm we can request rchg/{evaNo}
                String departureTime = sTypeIce.getDp().getPt();
                String scheduledId = sTypeIce.getId();

                timetables.add(new Timetable(startingStation, endingStation, delay, estimatedTime,arrivalTime,
                        departureTime ,scheduledId));
            }
        }

        return timetables;
    }

}
