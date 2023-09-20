package Delay;

import java.util.ArrayList;
import java.util.Random;

public class Delay {
    private static ArrayList<String> names= new ArrayList<String>();

    public static void addName(String name){
        names.add(name);
    }

    public static void celanNames(){
        names.clear();
    }

    public static void removeName(String name){
        names.remove(name);
    }

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
        delayTimeMillis = delayTimeMillis+ timeMillis;
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

    public static void delayWithProbability(String name) {
        if(!names.contains(name)){
            return;
        }
        // Genera un numero casuale tra 0 e 99.
        int randomValue = random.nextInt(100);

        // Impone il ritardo solo in base alla percentuale specificata.
        if (randomValue < delayPercentage) {
            try {
                // Introduce il ritardo specificato.
                System.out.println("Long Delay" + delayTimeMillis);
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