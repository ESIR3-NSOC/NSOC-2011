package esir.server.kevgen.JavaSENode
import org.kevoree.framework._
import esir.server._
class ServerComponentFactory extends org.kevoree.framework.osgi.KevoreeInstanceFactory {
override def registerInstance(instanceName : String, nodeName : String)=ServerComponentFactory.registerInstance(instanceName,nodeName)
override def remove(instanceName : String)=ServerComponentFactory.remove(instanceName)
def createInstanceActivator = ServerComponentFactory.createInstanceActivator}
object ServerComponentFactory extends org.kevoree.framework.osgi.KevoreeInstanceFactory {
def createInstanceActivator: org.kevoree.framework.osgi.KevoreeInstanceActivator = new ServerComponentActivator
def createComponentActor() : KevoreeComponent = {
new KevoreeComponent(createServerComponent()){def startComponent(){getKevoreeComponentType.asInstanceOf[esir.server.ServerComponent].startComponent()}
def stopComponent(){getKevoreeComponentType.asInstanceOf[esir.server.ServerComponent].stopComponent()}
override def updateComponent(){getKevoreeComponentType.asInstanceOf[esir.server.ServerComponent].updateComponent()}
}}
def createServerComponent() : esir.server.ServerComponent ={
var newcomponent = new esir.server.ServerComponent();
newcomponent.getHostedPorts().put("toIhm",createServerComponentPORTtoIhm(newcomponent))
newcomponent.getNeededPorts().put("fromIhm",createServerComponentPORTfromIhm(newcomponent))
newcomponent}
def createServerComponentPORTtoIhm(component : ServerComponent) : ServerComponentPORTtoIhm ={ new ServerComponentPORTtoIhm(component)}
def createServerComponentPORTfromIhm(component : ServerComponent) : ServerComponentPORTfromIhm ={ return new ServerComponentPORTfromIhm(component);}
}
