package creations.empire.binmanager;



public class DriverInfo {

    public String driverName;

    public String driverADD;

    public Long driverMob;

    public String driverEmail;

    public DriverInfo(){}

    public DriverInfo(String driverName,String driverADD,Long driverMob,String driverEmail){

        this.driverName = driverName;
        this.driverADD = driverADD;
        this.driverMob = driverMob;
        this.driverEmail = driverEmail;
    }

    public String getDriverName(){
        return driverName;
    }

    public String getBinADD(){
        return driverADD;
    }

    public Long getBinLat(){
        return driverMob;
    }

    public String getBinLong(){
        return driverEmail;
    }
}
