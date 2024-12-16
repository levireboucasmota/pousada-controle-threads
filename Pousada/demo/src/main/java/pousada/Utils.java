package pousada;

public class Utils {

    public static void timeCpuBound(double time, Runnable callback) {
        int remainingTime = (int) time;

        long startTime = System.currentTimeMillis();

        while (remainingTime > 0) {

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

            if (elapsedTime >= (time - remainingTime + 1)) {

                callback.run();

                remainingTime--;
            }
        }
    }
}