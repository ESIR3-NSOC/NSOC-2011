package esir.dom11.nsoc.conflictmgt;

import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Library(name = "NSOC_2011")
@ComponentType
public class ConflictMgt extends AbstractComponentType {

    /*
    * Class Attributes
    */

    private static Logger logger = LoggerFactory.getLogger(ConflictMgt.class.getName());

    /*
    * Attributes
    */


    /*
     * Getters / Setters
     */

    /*
    * Overrides
    */

    @Start
    public void start() {
        logger.info("= = = = = start conflict manager = = = = = =");
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop conflict manager = = = = = =");
    }

    @Update
    public void update() {
        logger.info("= = = = = update conflict manager = = = = = =");
    }
}
