package esir.dom11.nsoc.context.energy

class EnergyBalance (production:Double, consumption:Double){

  def getProduction():Double = production;
  def getConsumption():Double = consumption;

  def getEnergyBalance():Double = production-consumption;

}