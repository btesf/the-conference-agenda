package com.conference.domain.event;

import com.conference.domain.SessionType;
import java.time.Duration;

public class Lunch extends Event{

    private static final String TITLE = "Lunch";

    public Lunch() {
        /*
        *  to get an actual duration, you can get the difference between Morning and afternoon sessions
        */
        super(TITLE);

        Duration trackDuration = Duration.ofHours(SessionType.AFTERNOON.SESSION_END_TIME - SessionType.MORNING.SESSION_BEGIN_TIME);
        int totalEventTimeOutsideLunch = SessionType.AFTERNOON.DURATION_IN_MINUTES + SessionType.MORNING.DURATION_IN_MINUTES;

        this.setMinutes((int)trackDuration.toMinutes() - totalEventTimeOutsideLunch);
    }
}
