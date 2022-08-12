package edu.hitsz.bullet;

import edu.hitsz.prop.Observer;

/**
 * @author wb-yu
 */
public class EnemyBullet extends BaseBullet implements Observer {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    /**
     * @param flag 是否需要销毁当前飞行物
     *             观察者的更新方法
     */
    @Override
    public void update(boolean flag) {
        if (flag) {
            this.vanish();
        }
    }
}
