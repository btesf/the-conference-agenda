package com.conference.domain.event;

import com.conference.Constants;
import com.conference.domain.SessionType;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

public class Lunch extends Event{

    public Lunch() {
        /*
        *  to get an actual duration, you can get the difference between time from SessionType.MORNING and SessionType.AFTERNOON
        */
        super("Lunch", -1 /* place holder time - it will be set immediately below */);

        Duration trackDuration = Duration.ofHours(SessionType.AFTERNOON.SESSION_END_TIME - SessionType.MORNING.SESSION_BEGIN_TIME);
        int totalEventTimeOutsideLunch = SessionType.AFTERNOON.DURATION_IN_MINUTES + SessionType.MORNING.DURATION_IN_MINUTES;

        this.setMinutes((int)trackDuration.toMinutes() - totalEventTimeOutsideLunch);
    }
}
