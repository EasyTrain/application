package live.easytrain.application.config;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import live.easytrain.application.api.entity.ArType;
import live.easytrain.application.api.entity.DpType;
import live.easytrain.application.api.entity.TimetableType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class XmlResponse {

    private static final String CLIENT_ID = "2be55b1682292385647056952792643a";
    private static final String CLIENT_SECRET = "890f104af09b669b7940bd927ddb59db";

    /*
     * There are three different types of information to retrieve, all allows only GET
     * 1. https://apis.deutschebahn.com/db-api-marketplace/apis/timetables/v1/fchg/{evaNo}
     *    Returns a Timetable object that contains all known changes for the station given by evaNo.
     *    The data includes all known changes from now on until indefinitely into the future.
     *    Once changes become obsolete (because their trip departs from the station) they are removed from this resource.
     *    Changes may include messages. On event level, they usually contain one or more of the 'changed' attributes ct, cp, cs or cpth.
     *    Changes may also include 'planned' attributes if there is no associated planned data for the change (e.g. an unplanned stop or trip).
     *    Full changes are updated every 30s and should be cached for that period by web caches.
     *
     * 2. https://apis.deutschebahn.com/db-api-marketplace/apis/timetables/v1/plan/{evaNo}/{date}/{hour}
     *    Returns a Timetable object that contains planned data for the specified station (evaNo)
     *    within the hourly time slice given by date (format YYMMDD) and hour (format HH). The data includes stops
     *    for all trips that arrive or depart within that slice. There is a small overlap between slices since some
     *    trips arrive in one slice and depart in another.
     *    Planned data does never contain messages. On event level, planned data contains the 'plannned' attributes pt, pp, ps
     *    and ppth while the 'changed' attributes ct, cp, cs and cpth are absent.
     *    Planned data is generated many hours in advance and is static, i.e. it does never change. It should be cached by
     *    web caches.public interface allows access to information about a station.
     *
     * 3. https://apis.deutschebahn.com/db-api-marketplace/apis/timetables/v1/rchg/{evaNo}
     *    Returns a Timetable object that contains all recent changes for the station given by evaNo. Recent changes are
     *    always a subset of the full changes. They may equal full changes but are typically much smaller. Data includes
     *    only those changes that became known within the last 2 minutes.
     *    A client that updates its state in intervals of less than 2 minutes should load full changes initially and then
     *    proceed to periodically load only the recent changes in order to save bandwidth.
     *    Recent changes are updated every 30s as well and should be cached for that period by web caches.
     * */
    private static final String _API_TIMETABLE_URL = "https://apis.deutschebahn.com/db-api-marketplace/apis/timetables/v1/%s";

    private TimetableType parseXmlResponse(String xmlResponse) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TimetableType.class, ArType.class, DpType.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            JAXBElement<TimetableType> typeJAXBElement = (JAXBElement<TimetableType>) unmarshaller.unmarshal(new StringReader(xmlResponse));

            return typeJAXBElement.getValue();

        } catch (JAXBException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    /*
     * The pattern should receive one of the following values:
     * 1. fchg/{evaNo}
     * 2. plan/{evaNo}/{date}/{hour}
     * 3. rchg/{evaNo}
     *       - evaNo -> Station number (8011160 - Berlin)
     *       - date -> YYMMDD
     *       - hour -> HH
     * */
    public TimetableType fetchXmlResponse(String pattern) {

        String xml = null;
        TimetableType timetableType = null;

        OkHttpClient client = new OkHttpClient();

        String url = String.format(_API_TIMETABLE_URL, pattern);

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("DB-Client-Id", CLIENT_ID)
                .addHeader("DB-Api-Key", CLIENT_SECRET)
                .addHeader("accept", "application/xml")
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                xml = response.body().string();
            }
            response.close();

        } catch (IOException e) {
            // prd
//            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            // dev
            Logger.getLogger(this.getClass().getName()).log(Level.FINE, e.getMessage(), e);
        }

        if (xml == null) {
            return null;
        }

        return parseXmlResponse(xml);
    }

}