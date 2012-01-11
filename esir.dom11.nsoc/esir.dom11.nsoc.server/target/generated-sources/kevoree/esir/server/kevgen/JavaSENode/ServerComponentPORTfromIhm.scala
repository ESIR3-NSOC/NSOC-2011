package esir.server.kevgen.JavaSENode
import org.kevoree.framework.port._
import scala.{Unit=>void}
import esir.server._
class ServerComponentPORTfromIhm(component : ServerComponent) extends org.kevoree.framework.MessagePort with KevoreeRequiredPort {
def getName : String = "fromIhm"
def getComponentName : String = component.getName 
def process(o : Object) = {
{this ! o}
}
def getInOut = false
}
