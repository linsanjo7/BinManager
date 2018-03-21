package creations.empire.binmanager;


import android.content.SharedPreferences;

public class AreaInfo {

    public String binid;
    public String simno;
    public String gaurdno;
    public String addr;
    public String clrtime;
    public String status;

    public AreaInfo(){}

    public AreaInfo(String binid, String simno, String gaurdno,String addr,String clrtime,String status){
        this.binid = binid;
        this.simno = simno;
        this.gaurdno = gaurdno;
        this.addr = addr;
        this.clrtime = clrtime;
        this.status = status;
    }



    public String getBinid() {
        return binid;
    }

    public String getSimno() {
        return simno;
    }

    public String getGaurdno() {
        return gaurdno;
    }

    public String getAddr() {
        return addr;
    }

    public String getClrtime() {
        return clrtime;
    }

    public String getStatus() {
        return status;
    }
}
