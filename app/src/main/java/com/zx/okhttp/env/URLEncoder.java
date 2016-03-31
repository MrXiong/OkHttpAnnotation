package com.zx.okhttp.env;

import java.util.Set;
import java.util.TreeSet;

public class URLEncoder {

    private static Set<Byte> safetyBytesSet = new TreeSet<>();

    static {
        // 添加安全字符
        safetyBytesSet.add((byte) '-');
        safetyBytesSet.add((byte) '_');
        safetyBytesSet.add((byte) '.');
        safetyBytesSet.add((byte) '~');
        // 添加数字
        addRangeToSet((byte) '0', (byte) '9');
        // 添加小写字母
        addRangeToSet((byte) 'a', (byte) 'z');
        // 添加大写字母
        addRangeToSet((byte) 'A', (byte) 'Z');
    }

    private static void addRangeToSet(byte begin, byte end) {
        for (byte b = begin; b <= end; ++b) {
            safetyBytesSet.add(b);
        }
    }

    public static boolean isSafetyByte(Byte b) {
        return safetyBytesSet.contains(b);
    }

    public static String encode(String str, String encode) {
        StringBuilder sb = new StringBuilder();
        byte[] encodeBytes;
        try {
            encodeBytes = new String(str.getBytes(), encode).getBytes();
        } catch (Exception e) {
            encodeBytes = str.getBytes();
        }
        for (byte b : encodeBytes) {
            if (!isSafetyByte(b)) {
                sb.append(String.format("%%%02X", b));
            } else {
                sb.append((char) b);
            }
        }
        return sb.toString();
    }
}
