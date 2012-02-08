package test;

import esir.dom11.nsoc.context.energy.ImpulseToPower;
import junit.framework.TestCase;

public class ImpulseToPowerTest extends TestCase {

    public void test() {
        ImpulseToPower imp = new ImpulseToPower(900);
        System.out.println(imp.addImpulse());
        tempo(1000);
        double power = imp.addImpulse();
        assertEquals("false",900.0, power);
        System.out.println(power);
        tempo(800);
        power = imp.addImpulse();
        assertEquals("false",1125.0, power);
        System.out.println(power);
    }

    public void tempo(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
