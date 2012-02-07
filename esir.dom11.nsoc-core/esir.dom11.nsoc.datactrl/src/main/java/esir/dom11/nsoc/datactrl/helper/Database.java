package esir.dom11.nsoc.datactrl.helper;

import esir.dom11.nsoc.model.Command;
import esir.dom11.nsoc.model.Data;
import esir.dom11.nsoc.model.Log;
import esir.dom11.nsoc.model.device.Device;

import java.util.LinkedList;

public class Database {

    /*
     * Attributes
     */

    private LinkedList<Command> _commandList;
    private LinkedList<Device> _deviceList;
    private LinkedList<Data> _dataList;
    private LinkedList<Log> _logList;

    /*
     * Constructors
     */

    public Database() {

    }

    /*
     * Getters / Setters
     */

    public LinkedList<Command> getCommandList() {
        return _commandList;
    }

    public void setCommandList(LinkedList<Command> commandList) {
        _commandList = commandList;
    }

    public LinkedList<Device> getDeviceList() {
        return _deviceList;
    }

    public void setDeviceList(LinkedList<Device> deviceList) {
        _deviceList = deviceList;
    }

    public LinkedList<Data> getDataList() {
        return _dataList;
    }

    public void setDataList(LinkedList<Data> dataList) {
        _dataList = dataList;
    }

    public LinkedList<Log> getLogList() {
        return _logList;
    }

    public void setLogList(LinkedList<Log> logList) {
        _logList = logList;
    }
}
