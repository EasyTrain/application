package live.easytrain.application.service;

import live.easytrain.application.entity.JourneyUpdate;

import java.util.List;
import java.util.Optional;

public interface JourneyUpdateServiceInterface {

    Iterable<JourneyUpdate> getAllJourneyUpdates();

    Optional<JourneyUpdate> getJourneyUpdateByScheduledId(Long id);

//    List<JourneyUpdate> checkDelays(String scheduleId);
}
