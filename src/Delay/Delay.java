package Delay;

import java.util.Random;

public class Delay {
    private static final Random random = new Random();
    private static int delayTimeMillis = 1000; // Ritardo iniziale di 1 secondo (1000 millisecondi)
    private static int delayPercentage = 5;     // Percentuale di ritardo iniziale del 5%

    public static void setDelay(int timeMillis) {
        delayTimeMillis = timeMillis;
    }

    public static void setProbability(int percentage) {
        delayPercentage = percentage;
    }

    public static void increaseDelay(int timeMillis) {
        delayTimeMillis += timeMillis;
    }

    public static void increaseProbability(int percentage) {
        delayPercentage += percentage;
    }

    public static int getDelay() {
        return delayTimeMillis;
    }

    public static int getProbability() {
        return delayPercentage;
    }

    public static void delayWithProbability() {
        // Genera un numero casuale tra 0 e 99.
        int randomValue = random.nextInt(100);

        // Impone il ritardo solo in base alla percentuale specificata.
        if (randomValue < delayPercentage) {
            try {
                // Introduce il ritardo specificato.
                Thread.sleep(delayTimeMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        else{
            try{
                // Introduce il ritardo specificato.
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    }