package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * @author wb-yu
 */
public class MobEnemyFactory implements EnemyAircraftFactory {


    public static int initialHp = 30;


    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new MobEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                0,
                8,
                30,
                0,
                0);
    }


}
