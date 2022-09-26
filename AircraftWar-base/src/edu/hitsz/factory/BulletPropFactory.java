package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BulletProp;

/**
 * @author wb-yu
 */
public class BulletPropFactory implements PropFactory {

    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BulletProp(locationX, locationY, speedX, speedY);
    }

}
