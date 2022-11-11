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
    @Test
    public void testGetClassNumber(){
        if(getClass_number() > 45) {
            System.out.println("Test passed, class number is: " + getClass_number());
        } else {
            System.out.println("Test failed, class number is: " + getClass_number());
        }
    }
}
