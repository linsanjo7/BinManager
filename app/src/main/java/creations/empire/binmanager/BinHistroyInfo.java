package creations.empire.binmanager;



public class BinHistroyInfo {

    public String binid;

    public String binname;

    public String binaddress;

    public String cleartime;

    public BinHistroyInfo(){}

    public BinHistroyInfo(String binid,String binname,String binaddress,String cleartime){

        this.binid = binid;
        this.binaddress = binaddress;
        this.binname = binname;
        this.cleartime = cleartime;
    }

    public String getBinid(){
        return binid;
    }

    public String getBinname(){
        return binname;
    }

    public String getBinaddress(){
        return binaddress;
    }

    public String getCleartime(){
        return cleartime;
    }
}