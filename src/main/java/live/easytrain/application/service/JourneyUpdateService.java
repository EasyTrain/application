package live.easytrain.application.service;

import live.easytrain.application.repository.JourneyUpdateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyUpdateService implements JourneyUpdateServiceInterface{

    private JourneyUpdateRepo journeyUpdateRepo;
    @Autowired
    public JourneyUpdateService(JourneyUpdateRepo journeyUpdateRepo) {
        this.journeyUpdateRepo = journeyUpdateRepo;
    }
}
