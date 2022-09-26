package edu.hitsz;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.prop.BloodProp;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BloodPropTest {

    private BloodProp bloodProp;

    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**\n");
    }

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        bloodProp = new BloodProp((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BLOOD_PROP_IMAGE.getWidth())), (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2), 0, 10);
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**\n");
        bloodProp = null;
    }

    @DisplayName("Test getRecoveryAmount method")
    @Test
    void getRecoveryAmount() {
        System.out.println("**--- Test getRecoveryAmount method executed ---**");
        int recoveryAmount = bloodProp.getRecoveryAmount();
        assertEquals(300, recoveryAmount);
    }

    @DisplayName("Test setLocation method")
    @Test
    void setLocation() {
        System.out.println("**--- Test setLocation method executed ---**");
        bloodProp.setLocation(55, 105);
        assertEquals(55, bloodProp.getLocationX());
        assertEquals(105, bloodProp.getLocationY());
    }

    @DisplayName("Test notValid method")
    @Test
    void notValid() {
        System.out.println("**--- Test notValid method executed ---**");
        assertFalse(bloodProp.notValid());
    }

}
