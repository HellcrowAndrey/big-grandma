package com.github.mapper.factories;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class BigTypes {

    public static BigDecimal toBigDecimal(Object val) {
        return Objects.isNull(val) ? BigDecimal.ZERO : new BigDecimal(String.valueOf(val));
    }

    public static BigInteger toBigInteger(Object val) {
        return Objects.isNull(val) ? BigInteger.ZERO : new BigInteger(String.valueOf(val));
    }

    public static boolean isBigDecimal(Class<?> clz) {
        return clz.equals(BigDecimal.class);
    }

    public static boolean isBigInteger(Class<?> clz) {
        return clz.equals(BigInteger.class);
    }

}
