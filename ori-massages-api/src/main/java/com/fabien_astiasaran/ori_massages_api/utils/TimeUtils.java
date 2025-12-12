package com.fabien_astiasaran.ori_massages_api.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class TimeUtils {

    private TimeUtils() {}

    public static int localTimeToInt(LocalTime localTime) {
        return localTime.getHour() * 60 + localTime.getMinute();
    }

    public static LocalTime intToLocalTimeWithOffset(int startingTime, int offsetMinutes) {
        return LocalTime.of(
                (startingTime + offsetMinutes) / 60,
                (startingTime + offsetMinutes) % 60);
    }

    public static List<String> localTimeToString(List<LocalTime> localTimes){
        return localTimes.stream().map(TimeUtils::localTimeToString).toList();
    }

    public static String localTimeToString(LocalTime localTime){
        String formatSlotPattern = "HH:mm";
        DateTimeFormatter formatSlotFormatter = DateTimeFormatter.ofPattern(formatSlotPattern);
        return formatSlotFormatter.format(LocalTime.of(localTime.getHour(), localTime.getMinute()));
    }
}
