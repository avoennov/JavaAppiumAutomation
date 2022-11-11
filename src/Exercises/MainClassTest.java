package Exercises;

import org.junit.Test;

public class MainClassTest extends MainClass{

    @Test
    public void testGetLocalNumber() {
        if (getLocalNumber() == 14) {
            System.out.println("Test passed, local number is: " + getLocalNumber());
        } else {
            System.out.println("Test failed, local number is: " + getLocalNumber());
        }
    }
}
