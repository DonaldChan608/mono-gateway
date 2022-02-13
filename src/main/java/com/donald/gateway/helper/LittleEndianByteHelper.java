package com.donald.gateway.helper;

public class LittleEndianByteHelper {

    public static short getShortFromBytes(byte[] msg, int offset) {
        return (short) (msg[offset + 1] & 0xff << 8 |
                        msg[offset] & 0xff);
    }

    public static int getIntFromBytes(byte[] msg, int offset) {
        return msg[offset + 3] & 0xff << 24 |
                msg[offset + 2] & 0xff << 16 |
                msg[offset + 1] & 0xff << 8 |
                msg[offset] & 0xff;
    }

    public static long getLongFromBytes(byte[] msg, int offset) {
        return (long) (msg[offset + 7] & 0xff) << 56 |
                (long) (msg[offset + 6] & 0xff) << 48 |
                (long) (msg[offset + 5] & 0xff) << 40 |
                (long) (msg[offset + 4] & 0xff) << 32 |
                (long) (msg[offset + 3] & 0xff) << 24 |
                msg[offset + 2] & 0xff << 16 |
                msg[offset + 1] & 0xff << 8 |
                msg[offset] & 0xff;
    }



    public static int writeShortToByteArray(byte[] dest, short value, int offset) {
        dest[offset++] = (byte) (value >> 8 & 0xff);
        dest[offset++] = (byte) (value & 0xff);
        return offset;
    }

    public static int writeIntToByteArray(byte[] dest, int value, int offset) {
        dest[offset++] = (byte) (value >> 24 & 0xff);
        dest[offset++] = (byte) (value >> 16 & 0xff);
        dest[offset++] = (byte) (value >> 8 & 0xff);
        dest[offset++] = (byte) (value & 0xff);
        return offset;
    }

    public static int writeLongToByteArray(byte[] dest, long value, int offset) {
        dest[offset++] = (byte) (value >> 56 & 0xff);
        dest[offset++] = (byte) (value >> 48 & 0xff);
        dest[offset++] = (byte) (value >> 40 & 0xff);
        dest[offset++] = (byte) (value >> 32 & 0xff);
        dest[offset++] = (byte) (value >> 24 & 0xff);
        dest[offset++] = (byte) (value >> 16 & 0xff);
        dest[offset++] = (byte) (value >> 8 & 0xff);
        dest[offset++] = (byte) (value & 0xff);
        return offset;
    }
}
