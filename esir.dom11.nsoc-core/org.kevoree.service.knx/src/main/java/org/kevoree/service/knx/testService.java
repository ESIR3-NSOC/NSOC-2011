package org.kevoree.service.knx;

import com.sun.org.apache.xalan.internal.xsltc.dom.AdaptiveResultTreeImpl;
import org.kevoree.annotation.*;
import org.kevoree.annotation.ComponentType;
import org.kevoree.framework.*;
import org.kevoree.annotation.Port;

/**
 * Created by IntelliJ IDEA.
 * User: michel
 * Date: 09/01/12
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */


@Provides({
        @ProvidedPort(name = "test", type = PortType.SERVICE, className = IntTestService.class)
})

@ComponentType
public class testService extends AbstractComponentType implements IntTestService{
    @Start
    public void startComponent() {
        System.out.print("testService: start");
    }

    @Stop
    public void stopComponent() {
        System.out.println("ConnectionKNX:: Stop");
    }

    @Update
    public void updateComponent() {
        System.out.println("ConnectionKNX:: Update");
        stopComponent();
        startComponent();
    }

    @Override
    @Port(name = "test", method = "test")
    public void test() {
        System.out.print("test OK");
    }
}
