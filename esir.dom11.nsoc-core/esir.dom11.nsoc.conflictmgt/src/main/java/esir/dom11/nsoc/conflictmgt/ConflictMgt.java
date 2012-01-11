package esir.dom11.nsoc.conflictmgt;

/*
 TODO: thread gestion temps lock, save list cmd et action dans bd (30min), gérer condition actuator free, gérer catégorie
  */

import esir.dom11.nsoc.model.Action;
import esir.dom11.nsoc.model.Command;
import org.kevoree.annotation.*;
import org.kevoree.framework.AbstractComponentType;
import org.kevoree.framework.MessagePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

@Provides({
    @ProvidedPort(name = "cmdFromCtrl", type = PortType.MESSAGE)
})
@Requires({
    @RequiredPort(name = "actToActuator", type = PortType.MESSAGE)
})
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

    private LinkedList<Command> _commandBufferList;         //buffer des dernières commandes reçues avant traitement et sauvegarde
    private HashMap<UUID,Action> _lastActuatorActionMap;    //list des actions acceptées et envoyées pour gestion des conflits

    /*
     * Getters / Setters
     */

    /*
    * Overrides
    */

    @Start
    public void start() {
        logger.info("= = = = = start conflict manager = = = = = =");

        //Initialisation
        _lastActuatorActionMap = new HashMap<UUID, Action>();
        _commandBufferList = new LinkedList<Command>();
    }

    @Stop
    public void stop() {
        logger.info("= = = = = stop conflict manager = = = = = =");
    }

    @Update
    public void update() {
        logger.info("= = = = = update conflict manager = = = = = =");
    }
    
    /*
     * Overrides
     */

    @Port(name = "cmdFromCtrl")
    public void cmdFromCtrl(Command command) {

        //Sauvegarde de la commande à traiter
        _commandBufferList.add(command);

        //v1, autorise les actions qui agissent sur un actionneur non présent dans _lastActuatorActionMap
        for (Action action : command.getActionList()) {
            if (isActuatorFree(action)) {
                _lastActuatorActionMap.put(action.getIdActuator(),action);
                getPortByName("actToActuator",MessagePort.class).process(action);
            }
        }

    }

    /*
     * Methods
     */

    /**
     * isActuatorFree
     * @param action
     * @return "true" si l'IdActuator de l'action n'est pas dans le _lastActuatorActionMap
     */
    private boolean isActuatorFree(Action action) {
        if (_lastActuatorActionMap.containsKey(action.getIdActuator())){
            return false;
        }
        else {
            return true;
        }
    }

    private void saveInDb() {
        // TODO
    }
    
    private int getPriority() {
        // TODO
        return 0;
    }
}
