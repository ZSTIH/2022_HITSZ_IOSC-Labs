package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author wb-yu
 */
public interface ShootStrategy {

    /**
     * 射击子弹并返回生成的子弹列表
     *
     * @param shootNum          子弹一次发射的数量
     * @param aircraftLocationX 射击子弹的飞行物的X坐标
     * @param aircraftLocationY 射击子弹的飞行物的Y坐标
     * @param aircraftSpeedY    射击子弹的飞行物的Y速度
     * @param direction         子弹射击方向 (向上发射：-1，向下发射：1)
     * @param power             子弹伤害
     * @param isHero            是否为英雄机
     * @return 生成的子弹列表
     */
    List<BaseBullet> doShoot(int shootNum, int aircraftLocationX, int aircraftLocationY, int aircraftSpeedY, int direction, int power, boolean isHero);

}
