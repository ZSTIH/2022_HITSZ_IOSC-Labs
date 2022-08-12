package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.DirectShootStrategy;
import edu.hitsz.strategy.ScatterShootStrategy;

import java.util.List;

/**
 * @author wb-yu
 */
public class BulletProp extends AbstractProp {
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void activate(HeroAircraft heroAircraft, List<AbstractEnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets) {

        Runnable r1 = () -> {
            heroAircraft.changeShootNum(heroAircraft.getShootNum() + 2);
            heroAircraft.setShootStrategy(new ScatterShootStrategy());
        };

        Thread thread1 = new Thread(r1);
        thread1.start();

        Runnable r2 = () -> {
            try {
                thread1.join();
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            heroAircraft.changeShootNum(1);
            heroAircraft.setShootStrategy(new DirectShootStrategy());
        };

        Thread thread2 = new Thread(r2);
        thread2.start();

        System.out.println("FireSupply active!");
    }
}
