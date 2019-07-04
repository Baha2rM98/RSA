package RSA;

abstract class AsciiChecker {

    static boolean checkRange(byte[] asciis) {
        for (byte ascii : asciis) {
            if (ascii < 0)
                return false;
        }
        return true;
    }
}
