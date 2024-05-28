package live.easytrain.application.exceptions;

import live.easytrain.application.entity.Station;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String message) {
        super(message);
    }}
