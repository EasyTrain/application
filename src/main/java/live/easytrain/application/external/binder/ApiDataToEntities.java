package live.easytrain.application.external.binder;

import live.easytrain.application.config.XmlResponse;
import live.easytrain.application.entity.Timetable;
import live.easytrain.application.external.entity.SType;
import live.easytrain.application.external.entity.TimetableType;
import live.easytrain.application.external.entity.TlType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiDataToEntities {

    private XmlResponse xmlResponse;
    private static final String ICE = "ICE";

    @Autowired
    public ApiDataToEntities(XmlResponse xmlResponse) {
        this.xmlResponse = xmlResponse;
    }

    /*
     * stationNumber = evaNo, so it is mandatory.
     * recentChanges (rchg/{evaNo) = true -> for Recent changes Timetables API requests and
     * recentChanges (rchg/{evaNo) = false -> for other requests
     * */
    public List<Timetable> apiDataToTimetable(Integer stationNumber, String date, String hour, boolean recentChanges) {

        List<Timetable> timetables = new ArrayList<>();
        List<SType> sTypesICE = new ArrayList<>();
        TimetableType timetableType = null;

        if (stationNumber == null) {
            throw new IllegalArgumentException("stationNumber must not be null");
        } else if (date == null && hour == null && !recentChanges) {
            timetableType = xmlResponse.fetchXmlResponse("fchg" + "/" + stationNumber);
        } else if (date == null && hour == null) {
            timetableType = xmlResponse.fetchXmlResponse("rchg" + "/" + stationNumber);
        } else {
            //plan/{evaNo}/{date}/{hour}
            timetableType = xmlResponse.fetchXmlResponse("plan" + "/" + stationNumber + "/" + date + "/" + hour);
        }

        for (SType sType : timetableType.getS()) {
            TlType tlType = sType.getTl();
            if (tlType != null && tlType.getC().equals(ICE)) {
                sTypesICE.add(sType);
            }
        }

        if (sTypesICE == null) {
            throw new RuntimeException("No ICE found on trajectory");
        } else {
            for (SType sTypeIce : sTypesICE) {

                String startingStation = "";
                String endingStation = "";
                String delay = "On time";

                //Fields to retrieve
                String planedArrivalTime = "";
                String planedDepartureTime = "";


                // The train start stations are on <ar> tag and the destination on <dp> tag
                // The stations are separated by "|" and regex doesn't like this special char
                if (sTypeIce.getAr() == null) {
                    startingStation = String.valueOf(stationNumber);
                } else {
                    String arStations = sTypeIce.getAr().getPpth().replace("|", "@");
                    String[] arStartingStation = arStations.split("@");
                    startingStation = arStartingStation[0];

                    planedArrivalTime = sTypeIce.getAr().getPt();
                }

                if (sTypeIce.getDp() == null) {
                    endingStation = String.valueOf(stationNumber);
                } else {
                    String dpStations = sTypeIce.getDp().getPpth().replace("|", "@");
                    String[] dpEndStation = dpStations.split("@");
                    endingStation = dpEndStation[dpEndStation.length - 1];

                    planedDepartureTime = sTypeIce.getDp().getPt();
                }

                if (!sTypeIce.getM().isEmpty()) {
                    delay = "Delayed";
                }

                String estimatedTime = sTypeIce.getAr().getCt();
                String arrivalTime = sTypeIce.getAr().getCt(); // To confirm we can request rchg/{evaNo}
                String departureTime = sTypeIce.getDp().getCt();
                String scheduledId = sTypeIce.getId();

                String trainNumber = sTypeIce.getTl().getN();

                // Journey duration
                timetables.add(new Timetable(startingStation, endingStation, delay, estimatedTime, arrivalTime,
                        departureTime, scheduledId));
            }
        }

        return timetables;
    }

}
