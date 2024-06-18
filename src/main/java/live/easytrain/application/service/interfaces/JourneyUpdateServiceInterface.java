package live.easytrain.application.service.interfaces;

import live.easytrain.application.entity.JourneyUpdate;

import java.util.List;

public interface JourneyUpdateServiceInterface {
    List<JourneyUpdate> getJourneyUpdatesByScheduleId(String scheduleId);
    List<JourneyUpdate> saveJourneyUpdates(String stationName, boolean recentChanges);
    List<JourneyUpdate> findAll(String stationName);
}
