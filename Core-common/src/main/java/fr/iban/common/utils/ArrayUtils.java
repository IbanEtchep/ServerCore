package fr.iban.common.utils;

import java.util.Random;

public final class ArrayUtils {
    
    private ArrayUtils() {}
    
    public static final Random RANDOM = new Random();
    
    public static <T> T getRandomFromArray(T[] array) {
	return array[RANDOM.nextInt(array.length)];
    }

}
