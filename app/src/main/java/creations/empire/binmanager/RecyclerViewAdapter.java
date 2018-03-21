package creations.empire.binmanager;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static creations.empire.binmanager.R.layout.grid_item;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements RecyclerView.OnItemTouchListener{

    private List<BinHistroyInfo> MainImageUploadInfoList;
    private TextView titletext,desctext,rectext;

    RecyclerViewAdapter(Context context, List<BinHistroyInfo> TempList) {

        this.MainImageUploadInfoList = TempList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View View = LayoutInflater.from(parent.getContext()).inflate(grid_item, parent, false);

        return new ViewHolder(View);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BinHistroyInfo UploadInfo = MainImageUploadInfoList.get(position);
        titletext.setText(UploadInfo.getBinname());
        desctext.setText(UploadInfo.getBinaddress());
        rectext.setText(UploadInfo.getCleartime());

    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
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
            titletext = itemView.findViewById(R.id.bin_name);
            desctext = itemView.findViewById(R.id.bin_address);
            rectext = itemView.findViewById(R.id.bin_cleartime);


        }
    }
}