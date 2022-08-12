package edu.hitsz;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.factory.EliteEnemyFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EliteEnemyTest {

    private EliteEnemy eliteEnemy;

    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**\n");
    }

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        eliteEnemy = (EliteEnemy) new EliteEnemyFactory().createEnemyAircraft();
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**\n");
        eliteEnemy = null;
    }

    @DisplayName("Test forward method")
    @Test
    void forward() {
        System.out.println("**--- Test forward method executed ---**");
        int pre_locationY = eliteEnemy.getLocationY();
        eliteEnemy.forward();
        int new_locationY = eliteEnemy.getLocationY();
        assertEquals(pre_locationY + eliteEnemy.getSpeedY(), new_locationY);
    }

    @DisplayName("Test decreaseHp method")
    @Test
    void decreaseHp() {
        System.out.println("**--- Test decreaseHp method executed ---**");
        eliteEnemy.decreaseHp(35);
        assertEquals(25, eliteEnemy.getHp());
    }

    @DisplayName("Test getSpeedY method")
    @Test
    void getSpeedY() {
        System.out.println("**--- Test getSpeedY method executed ---**");
        assertEquals(10, eliteEnemy.getSpeedY());
    }

}
