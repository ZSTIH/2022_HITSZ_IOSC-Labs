package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/**
 * @author wb-yu
 */
public class BossEnemyFactory implements EnemyAircraftFactory {


    public static int initialHp = 240;

    public static int initialShootNum = 3;

    public static int initialPower = 20;

    @Override
    public AbstractEnemyAircraft createEnemyAircraft() {
        return new BossEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2),
                Math.random() > 0.5 ? 7 : -7,
                0,
                initialHp,
                initialShootNum,
                initialPower);
    }


}
