package RSA;

abstract class ABS {

    static byte[] abs(byte[] inputChars) {
        final int length = inputChars.length;
        byte[] chars = new byte[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (byte) Math.abs(inputChars[i]);
        }
        return chars;
    }

    static byte[] subAbs(byte[] asciis) {
        final int length = asciis.length;
        final int negatived = -1;
        byte[] strings = new byte[length];
        for (int i = 0; i < length; i++) {
            if (asciis[i] == 32)
                continue;
            strings[i] = (byte) (negatived * asciis[i]);
        }
        return strings;
    }
}

