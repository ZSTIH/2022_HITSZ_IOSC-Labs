package edu.hitsz.aircraft;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.ShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB）与英雄机
 *
 * @author wb-yu
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {

    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    /**
     * 子弹一次发射数量
     */
    protected int shootNum;

    /**
     * 子弹伤害
     */
    protected int power;

    /**
     * 子弹射击方向 （向上发射为-1，向下发射为1）
     */
    protected int direction;

    /**
     * 是否为英雄机的标记 （英雄机为true，敌机为false）
     */
    protected boolean isHero;

    /**
     * 设计策略：直射或散射
     */
    private ShootStrategy shootStrategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction, boolean isHero) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootNum = shootNum;
        this.power = power;
        this.direction = direction;
        this.isHero = isHero;
    }

    public void decreaseHp(int decrease) {
        hp -= decrease;
        if (hp <= 0) {
            hp = 0;
            vanish();
        } else if (hp > maxHp) {
            hp = maxHp;
        }
    }

    public int getHp() {
        return hp;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     *
     * @return 可射击对象需实现，返回子弹
     * 非可射击对象空实现，返回null
     */
    public List<BaseBullet> shoot() {
        return shootStrategy.doShoot(this.shootNum, this.locationX, this.locationY, this.speedY, this.direction, this.power, this.isHero);
    }

}
