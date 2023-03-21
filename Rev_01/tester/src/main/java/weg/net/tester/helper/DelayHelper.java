package weg.net.tester.helper;

import java.util.concurrent.TimeUnit;

public class DelayHelper {
    public static void delayMilliseconds(int wait) {
        try {
            TimeUnit.MILLISECONDS.sleep(wait);
        } catch (InterruptedException e) {
        }
    }
}
