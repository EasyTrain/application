package live.easytrain.application.external.binder;

import live.easytrain.application.config.XmlResponse;
import live.easytrain.application.entity.Timetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    * */
    public List<Timetable> apiDataToTimetable(Integer stationNumber, String date, String hour){
        List<Timetable> timetables = new ArrayList<>();

        if (stationNumber == null){
            throw new IllegalArgumentException("stationNumber must not be null");
        }

        return timetables;
    }

}
