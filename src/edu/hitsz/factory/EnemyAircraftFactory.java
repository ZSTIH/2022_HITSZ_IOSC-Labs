package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;

/**
 * @author wb-yu
 */
public interface EnemyAircraftFactory {

    /**
     * 用于通过工厂模式创建敌机实例
     *
     * @return 返回创建的敌机实例
     */
    AbstractEnemyAircraft createEnemyAircraft();

}
