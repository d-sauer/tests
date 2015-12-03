package com.test;

/**
 * @author Davor Sauer
 */
public class Test1 {

    public static void main(String[] args) {
        String bestName = "\\AB C d 123 \\e|f/g";

        bestName = bestName.replaceAll("[^A-Za-z0-9_]*", "");


        System.out.println(bestName);

    }
}
