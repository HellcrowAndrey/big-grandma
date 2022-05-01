package com.github.mapper;

public class StringSqlUtils {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static String toStringWithQuote(Object obj) {
        return "'" + obj + "'";
    }
    public static String toStringSeparatorComa(Object[] a) {
        if (a == null) {
            throw new IllegalArgumentException();
        }

        int iMax = a.length - 1;
        if (iMax == -1) {
            throw new IllegalArgumentException();
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax) {
                return b.toString();
            }
            b.append(", ");
        }
    }
    public static String toStringSeparatorComaAndQuotes(Object[] a) {
        if (a == null) {
            throw new IllegalArgumentException();
        }

        int iMax = a.length - 1;
        if (iMax == -1) {
            throw new IllegalArgumentException();
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append("'").append(a[i]).append("'");
            if (i == iMax) {
                return b.toString();
            }
            b.append(", ");
        }
    }
    public static String toStringSeparatorSpace(Object[] a) {
        if (a == null) {
            throw new IllegalArgumentException();
        }

        int iMax = a.length - 1;
        if (iMax == -1) {
            throw new IllegalArgumentException();
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax) {
                return b.toString();
            }
            b.append(" ");
        }
    }

    public static Object toTextOrSame(Object val) {
        return val instanceof String || val instanceof Character ? toStringWithQuote(val) : val;
    }


}
