package fr.iban.survivalcore.utils;

import org.apache.commons.lang.math.RandomUtils;

public final class ArrayUtils {
    
    private ArrayUtils() {}
    
    public static <T> T getRandomFromArray(T[] array) {
	return array[RandomUtils.nextInt(array.length)];
    }

}
