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

        if (timetableType == null) {
            throw new RuntimeException("No data found for entered information." +
                    " Try to change the date to today's date or change the hour to today's hour.");
        } else {
            for (SType sType : timetableType.getS()) {
                TlType tlType = sType.getTl();
                if (tlType != null && tlType.getC().equals(ICE)) {
                    sTypesICE.add(sType);
                }
            }

            if (sTypesICE.isEmpty()) {
                throw new RuntimeException("No ICE found on trajectory");
            } else {
                for (SType sTypeIce : sTypesICE) {

                    String startingStation = "";
                    String endingStation = "";
                    String delay = "Information";

                    //Fields to retrieve
                    String planedArrivalTime = "";
                    String planedDepartureTime = "";
                    String platformNumber = "";
                    String trainNumber = "";
                    String estimatedTime = "";
                    String arrivalTime = "";
                    String departureTime = "";


                    // The train start stations are on <ar> tag and the destination on <dp> tag
                    // The stations are separated by "|" and regex doesn't like this special char
                    // ar tag null means that the start station of the train is the departure station
                    if (sTypeIce.getAr() == null) {
                        startingStation = String.valueOf(stationNumber);

                        if(sTypeIce.getDp().getCt() == null){
                            estimatedTime = sTypeIce.getDp().getPt();
                            arrivalTime = sTypeIce.getDp().getPt();
                        } else{
                            estimatedTime = sTypeIce.getDp().getCt();
                            arrivalTime = sTypeIce.getDp().getCt();
                        }

                        planedArrivalTime = sTypeIce.getDp().getPt();
                        platformNumber = sTypeIce.getDp().getPp();
                    } else {
                        String arStations = sTypeIce.getAr().getPpth().replace("|", "@");
                        String[] arStartingStation = arStations.split("@");
                        startingStation = arStartingStation[0];

                        planedArrivalTime = sTypeIce.getAr().getPt();

                        if(sTypeIce.getAr().getCt() == null){
                            estimatedTime = sTypeIce.getAr().getPt();
                            arrivalTime = sTypeIce.getAr().getPt();
                        } else {
                            estimatedTime = sTypeIce.getAr().getCt();
                            arrivalTime = sTypeIce.getAr().getCt();
                        }

                        platformNumber = sTypeIce.getAr().getPp();
                    }

                    if (sTypeIce.getDp() == null) {
                        endingStation = String.valueOf(stationNumber);
                        planedDepartureTime = sTypeIce.getAr().getPt();

                        if(sTypeIce.getDp().getCt() == null){
                            departureTime = sTypeIce.getAr().getPt();
                        }else{
                            departureTime = sTypeIce.getAr().getCt();
                        }

                        platformNumber = sTypeIce.getAr().getPp();
                    } else {
                        String dpStations = sTypeIce.getDp().getPpth().replace("|", "@");
                        String[] dpEndStation = dpStations.split("@");
                        endingStation = dpEndStation[dpEndStation.length - 1];

                        planedDepartureTime = sTypeIce.getDp().getPt();

                        if (sTypeIce.getDp().getCt() == null){
                            departureTime = sTypeIce.getDp().getPt();
                        }else{
                            departureTime = sTypeIce.getDp().getCt();
                        }

                        platformNumber = sTypeIce.getDp().getPp();
                    }

                    if (!sTypeIce.getM().isEmpty()) {
                        delay = "Delayed";
                    }

                    trainNumber = sTypeIce.getTl().getC() + sTypeIce.getTl().getN();
                    String scheduledId = sTypeIce.getId();

                    // Journey duration
                    timetables.add(new Timetable(startingStation, endingStation, delay, timeBuilder(estimatedTime),
                            timeBuilder(arrivalTime), timeBuilder(departureTime), scheduledId, platformNumber, trainNumber,
                            timeBuilder(planedArrivalTime), timeBuilder(planedDepartureTime)));
                }
            }
        }

        return timetables;
    }

    private String timeBuilder(String strTime) {
        if (strTime == null) {
            return null;
        }

        String str = strTime.substring(6);
        StringBuilder sb = new StringBuilder(str);
        sb.insert(2, ':');
        return sb.toString();
    }

}
