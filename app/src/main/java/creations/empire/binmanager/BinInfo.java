package creations.empire.binmanager;


public class BinInfo {

    public String binID;

    public String binADD;

    public Double binLat;

    public Double binLong;

    public BinInfo(){}

    public BinInfo(String binID,String binADD,Double binLat,Double binLong){

        this.binID = binID;
        this.binADD = binADD;
        this.binLat = binLat;
        this.binLong = binLong;
    }

    public String getBinID(){
        return binID;
    }

    public String getBinADD(){
        return binADD;
    }

    public Double getBinLat(){
        return binLat;
    }

    public Double getBinLong(){
        return binLong;
    }
}
