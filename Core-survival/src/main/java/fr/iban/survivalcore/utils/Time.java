package fr.iban.survivalcore.utils;

import java.util.concurrent.TimeUnit;

public class Time {
	
	public static String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
        
        if(seconds <= 61) {
        	return second + " secondes";
        } else if(seconds > 60) {
        	return minute + " minutes " + second + " secondes";
        } else if(seconds > 3600) {
        	return hours + " heures " + minute + " minutes " + second + " secondes";
        } else if(seconds > 86400) {
        	return day + " jours " + hours + " heures " + minute + " minutes " + second + " secondes";
        }
        return day + " jours " + hours + " heures " + minute + " minutes " + second + " secondes";

    }

}
