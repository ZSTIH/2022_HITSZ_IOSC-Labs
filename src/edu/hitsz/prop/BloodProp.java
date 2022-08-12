package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * @author wb-yu
 */
public class BloodProp extends AbstractProp {

    private final int recoveryAmount = 300;

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public int getRecoveryAmount() {
        return this.recoveryAmount;
    }

    @Override
    public void activate(HeroAircraft heroAircraft, List<AbstractEnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets) {
        heroAircraft.decreaseHp(-this.recoveryAmount);
        System.out.println("BloodSupply active!");
    }


}
