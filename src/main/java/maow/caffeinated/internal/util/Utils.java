package maow.caffeinated.internal.util;

import java.util.Arrays;

public final class Utils {
    public static <T> boolean contains(T[] tArray, T t) {
        return Arrays.asList(tArray).contains(t);
    }
}
