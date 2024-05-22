package live.easytrain.application.service;

import jakarta.transaction.Transactional;
import live.easytrain.application.entity.Timetable;

import java.util.List;
import java.util.Optional;

public interface TimetableServiceInterface {

    List<Timetable> getAllTimetables();
    Timetable getTimetableById(Long id);
    Timetable saveTimetable(Timetable timetable);
    void deleteTimetable(Long id);

}
