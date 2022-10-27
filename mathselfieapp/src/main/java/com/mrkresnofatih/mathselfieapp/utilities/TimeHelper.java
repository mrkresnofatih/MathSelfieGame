package com.mrkresnofatih.mathselfieapp.utilities;

import java.time.Instant;

public class TimeHelper {
    public static String GetDescUtcMilliTimestamp() {
        var futureEpoch = Instant.parse("2075-01-01T00:00:00Z").toEpochMilli();
        var currentEpoch = Instant.now().toEpochMilli();
        return String.format("%015d", futureEpoch - currentEpoch);
    }
}
