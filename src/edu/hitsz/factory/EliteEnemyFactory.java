package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * @author wb-yu
 */
public class EliteEnemyFactory implements EnemyAircraftFactory {

    public static int initialHp = 60;

    public static int initialShootNum = 1;

    public static int initialPower = 20;

    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new EliteEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                Math.random() > 0.5 ? 5 : -5,
                10,
                initialHp,
                initialShootNum,
                initialPower);
    }

}
