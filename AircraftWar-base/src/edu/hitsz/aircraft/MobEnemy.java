package edu.hitsz.aircraft;

import edu.hitsz.prop.Observer;
import edu.hitsz.strategy.DirectShootStrategy;

/**
 * 普通敌机
 * 不可射击
 *
 * @author wb-yu
 */
public class MobEnemy extends AbstractEnemyAircraft implements Observer {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power);
        this.setShootStrategy(new DirectShootStrategy());
        this.scoreAward = 200;
    }

    @Override
    public void update(boolean flag) {
        if (flag) {
            this.vanish();
        }
    }

}
