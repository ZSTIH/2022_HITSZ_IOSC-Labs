package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author wb-yu
 */
public class ScatterShootStrategy implements ShootStrategy {

    @Override
    public List<BaseBullet> doShoot(int shootNum, int aircraftLocationX, int aircraftLocationY, int aircraftSpeedY, int direction, int power, boolean isHero) {

        List<BaseBullet> res = new LinkedList<>();
        int y = aircraftLocationY + direction * 2;
        int speedY = aircraftSpeedY + direction * 5;
        BaseBullet baseBullet;
        for (int i = 0; i < shootNum; i++) {
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            int x = aircraftLocationX + (i * 2 - shootNum + 1) * 10;
            int speedX;
            if (x < aircraftLocationX) {
                speedX = -4;
            } else if (x > aircraftLocationX) {
                speedX = 4;
            } else {
                speedX = 0;
            }
            if (isHero) {
                baseBullet = new HeroBullet(x, y, speedX, speedY, power);
            } else {
                baseBullet = new EnemyBullet(x, y, speedX, speedY, power);
            }
            res.add(baseBullet);
        }
        return res;

    }

}
