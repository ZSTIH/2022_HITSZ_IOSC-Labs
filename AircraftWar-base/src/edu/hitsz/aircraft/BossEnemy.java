package edu.hitsz.aircraft;


import edu.hitsz.prop.Observer;
import edu.hitsz.strategy.ScatterShootStrategy;

/**
 * BOSS敌机
 * 可以射击
 *
 * @author wb-yu
 */
public class BossEnemy extends AbstractEnemyAircraft implements Observer {

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power);
        this.setShootStrategy(new ScatterShootStrategy());
        this.scoreAward = 400;
    }

    @Override
    public void update(boolean flag) {
        if (flag) {
            this.vanish();
        }
    }

}
