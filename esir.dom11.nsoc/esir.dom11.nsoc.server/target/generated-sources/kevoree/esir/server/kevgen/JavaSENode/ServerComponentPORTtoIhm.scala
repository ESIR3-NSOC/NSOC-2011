package esir.server.kevgen.JavaSENode
import org.kevoree.framework.port._
import esir.server._
import scala.{Unit=>void}
class ServerComponentPORTtoIhm(component : ServerComponent) extends org.kevoree.framework.MessagePort with KevoreeProvidedPort {
def getName : String = "toIhm"
def getComponentName : String = component.getName 
def process(o : Object) = {this ! o}
override def internal_process(msg : Any)= msg match {
case _ @ msg =>try{component.receiveToIhm(msg)}catch{case _ @ e => {e.printStackTrace();println("Uncatched exception while processing Kevoree message")}}
}
}
