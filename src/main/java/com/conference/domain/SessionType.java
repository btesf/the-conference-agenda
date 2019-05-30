package com.conference.domain;

import java.time.LocalTime;

public enum SessionType {

    MORNING(180, 9),
    AFTERNOON(240, 13);

    public final int DURATION_IN_MINUTES;
    public final int SESSION_BEGIN_TIME;
    public final int SESSION_END_TIME;

    private SessionType(final int durationInMinutes, final int sessionBeginTime){

        this.DURATION_IN_MINUTES = durationInMinutes;
        this.SESSION_BEGIN_TIME = sessionBeginTime;

        LocalTime localTime = LocalTime.of(SESSION_BEGIN_TIME, 0);
        localTime = localTime.plusMinutes(DURATION_IN_MINUTES);

        SESSION_END_TIME = localTime.getHour();
    }
}
