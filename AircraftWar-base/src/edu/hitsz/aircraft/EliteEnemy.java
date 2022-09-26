package edu.hitsz.aircraft;

import edu.hitsz.prop.Observer;
import edu.hitsz.strategy.DirectShootStrategy;

/**
 * 精英敌机
 * 可以射击
 *
 * @author wb-yu
 */
public class EliteEnemy extends AbstractEnemyAircraft implements Observer {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power);
        this.setShootStrategy(new DirectShootStrategy());
        this.scoreAward = 300;
    }

    @Override
    public void update(boolean flag) {
        if (flag) {
            this.vanish();
        }
    }

}
