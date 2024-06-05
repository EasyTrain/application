package live.easytrain.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndTestController {

@GetMapping("/frontend")
public String showFrontEndTest() {
    return "index";
}

@GetMapping("/timetabletest")
public String showTimetableTest() {
    return "timetable";
}

@GetMapping("/timetabletest2")
public String showTimetableTest2() {
    return "timetable-lookup";
}

}
