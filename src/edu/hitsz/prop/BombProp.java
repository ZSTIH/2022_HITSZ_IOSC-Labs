package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-yu
 */
public class BombProp extends AbstractProp {

    private final List<Observer> subscribers = new ArrayList<>();
    /**
     * 炸弹道具所消灭敌机产生的道具总和
     */
    private final List<AbstractProp> propsGenerated = new ArrayList<>();
    /**
     * 炸弹道具所消灭敌机的分数总和
     */
    private int scoresAward;

    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void addSubscriber(Observer observer) {
        subscribers.add(observer);
    }

    public void removeAllSubscriber() {
        subscribers.clear();
    }

    @Override
    public void activate(HeroAircraft heroAircraft, List<AbstractEnemyAircraft> enemyAircrafts, List<BaseBullet> enemyBullets) {

        this.scoresAward = 0;
        this.propsGenerated.clear();

        for (AbstractFlyingObject enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.notValid() || enemyAircraft instanceof BossEnemy) {
                continue;
            }
            addSubscriber((Observer) enemyAircraft);
        }

        for (AbstractFlyingObject enemyBullet : enemyBullets) {
            if (enemyBullet.notValid()) {
                continue;
            }
            addSubscriber((Observer) enemyBullet);
        }

        notifyAllSubscribers();

        removeAllSubscriber();

        System.out.println("BombSupply active!");
    }

    public void notifyAllSubscribers() {
        for (Observer subscriber : subscribers) {
            subscriber.update(true);
            if (subscriber instanceof AbstractEnemyAircraft) {
                scoresAward += ((AbstractEnemyAircraft) subscriber).getScoreAward();
                if (subscriber instanceof EliteEnemy) {
                    ((EliteEnemy) subscriber).generateProp(propsGenerated);
                }
            }
        }
    }

    public int getScoresAward() {
        return scoresAward;
    }

    public List<AbstractProp> getPropsGenerated() {
        return propsGenerated;
    }

}
