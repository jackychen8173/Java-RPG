package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
//        try {
//            new GameScreen();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application");
//        }

        try {
            new GUI();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
