package edu.hitsz;

import edu.hitsz.aircraft.HeroAircraft;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {

    private final HeroAircraft heroAircraft = HeroAircraft.getHeroAircraftInstance();

    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**\n");
    }

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**\n");
    }

    @DisplayName("Test getHeroAircraftInstance method")
    @Test
    void getHeroAircraftInstance() {
        System.out.println("**--- Test getHeroAircraftInstance method executed ---**");
        HeroAircraft instance;
        instance = HeroAircraft.getHeroAircraftInstance();
        assertNotNull(instance);
        assertEquals(heroAircraft, instance);
    }

    @DisplayName("Test getHp method")
    @Test
    void getHp() {
        System.out.println("**--- Test getHp method executed ---**");
        assertEquals(10000, heroAircraft.getHp());
    }

    @DisplayName("Test vanish method")
    @Test
    void vanish() {
        System.out.println("**--- Test vanish method executed ---**");
        heroAircraft.vanish();
        assertTrue(heroAircraft.notValid());
    }

}
