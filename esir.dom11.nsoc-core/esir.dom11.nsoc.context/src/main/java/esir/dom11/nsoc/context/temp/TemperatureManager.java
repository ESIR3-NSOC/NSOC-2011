package esir.dom11.nsoc.context.temp;

public class TemperatureManager {

    private double _tempInt;
    private double _tempExt;
    private double _userTempSetting;
    private boolean _presence;


    public TemperatureManager() {
        _tempInt = 0.0;
        _tempExt = 0.0;
        _userTempSetting = 0.0;
        _presence = false;
    }

    public TemperatureManager(double tempInt, double tempExt, double userTempSetting, boolean presence) {
        _tempInt = tempInt;
        _tempExt = tempExt;
        _userTempSetting = userTempSetting;
        _presence = presence;
    }

    /**
     *
     * @return
     */
    public double determineContextTempSetting(){
        if(_presence){
            return _userTempSetting;
        } else{
            // no presence -> -3.0°C
            return _userTempSetting-3.0;
        }
    }


    public double getTempInt() {
        return _tempInt;
    }

    public void setTempInt(double tempInt) {
        _tempInt = tempInt;
    }

    public double getTempExt() {
        return _tempExt;
    }

    public void setTempExt(double tempExt) {
        _tempExt = tempExt;
    }

    public double getTempSetting() {
        return _userTempSetting;
    }

    public void setTempSetting(double tempSetting) {
        _userTempSetting = tempSetting;
    }

    public boolean getPresence() {
        return _presence;
    }

    public void setPresence(boolean presence) {
        _presence = presence;
    }
}
