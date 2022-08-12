package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;


/**
 * 道具类
 *
 * @author wb-yu
 */
public abstract class AbstractProp extends AbstractFlyingObject {

    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 道具生效
     *
     * @param heroAircraft   英雄机
     * @param enemyAircrafts 所有敌机
     * @param enemyBullets   所有敌机子弹
     */
    abstract public void activate(HeroAircraft heroAircraft, List<AbstractEnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets);

}