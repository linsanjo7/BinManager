package creations.empire.binmanager;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static creations.empire.binmanager.R.layout.areas_layout2;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> implements RecyclerView.OnItemTouchListener{

    private List<AreaInfo> UploadInfoList;
    private  TextView binid, simno, gaurdno, address, clrtime, status;
    private String stat;

    RecyclerViewAdapter2(Context context, List<AreaInfo> TempList) {

        this.UploadInfoList = TempList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View View = LayoutInflater.from(parent.getContext()).inflate(areas_layout2, parent, false);

        return new ViewHolder(View);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AreaInfo UploadInfo = UploadInfoList.get(position);
        binid.setText(UploadInfo.getBinid());
        simno.setText(UploadInfo.getSimno());
        gaurdno.setText(UploadInfo.getGaurdno());
        address.setText(UploadInfo.getAddr());
        clrtime.setText(UploadInfo.getClrtime());
        stat = UploadInfo.getStatus();
        if(stat.equals("0"))
            status.setText("BIN EMPTY");
        else if(stat.equals("1"))
            status.setText("BIN HALF FULL");
        else if(stat.equals("2"))
            status.setText("BIN 75% FULL");
        else if(stat.equals("3"))
            status.setText("100% FULL");
        else if(stat.equals("4"))
            status.setText("BIN CLEARED");
        else
            status.setText("Not Available");

    }

    @Override
    public int getItemCount() {

        return UploadInfoList.size();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    class ViewHolder extends RecyclerView.ViewHolder {


        ViewHolder(View itemView) {
            super(itemView);
            binid = itemView.findViewById(R.id.textView21);
            simno = itemView.findViewById(R.id.textView22);
            gaurdno = itemView.findViewById(R.id.textView23);
            address = itemView.findViewById(R.id.textView24);
            clrtime = itemView.findViewById(R.id.textView25);
            status = itemView.findViewById(R.id.textView26);


        }
    }
}