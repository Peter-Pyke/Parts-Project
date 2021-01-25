package model;
/**
 InHouse Part Class. This class will be used to create in house parts.
 @author Peter Chouinard.
 */
public class InHouse extends Part
{
    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /**
     This is a getter method for the machineID parameter.
     @return machineID parameter.
     */
    public int getMachineID(){return machineID;}
    /**
     This is a setter method for the machineID parameter.
     @param machineID the machine id to be set.
     */
    public void setMachineID(int machineID){this.machineID = machineID;}
}
