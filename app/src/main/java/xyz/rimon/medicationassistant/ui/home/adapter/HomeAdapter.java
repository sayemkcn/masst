package xyz.rimon.medicationassistant.ui.home.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.List;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.commons.Logger;
import xyz.rimon.medicationassistant.core.CoreActivity;
import xyz.rimon.medicationassistant.domains.Drug;
import xyz.rimon.medicationassistant.events.DrugUpdatedEvent;
import xyz.rimon.medicationassistant.ui.adddrug.AddDrugFragment_;
import xyz.rimon.medicationassistant.utils.StorageUtils;

/**
 * Created by SAyEM on 8/13/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private List<Drug> drugList;

    public HomeAdapter(Context context, List<Drug> drugList) {
        this.context = context;
        this.drugList = drugList;
    }


    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.single_home_card_item, parent, false);
        return new HomeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
        Drug drug = this.drugList.get(position);
        holder.tvDrugName.setText(drug.getType() + " " + drug.getName());
        try {
            holder.tvTime.setText(drug.getNextTime() + " " + context.getResources().getString(R.string.msg_isTheTimeForMedication));
        } catch (ParseException e) {
            Logger.e("HomeAdaperOnBindViewHoler", e.toString());
        }

        holder.tvComment.setText(drug.getComment());

        switch (drug.getType()) {
            case Drug.Type.TYPE_TABLET:
                holder.imgIcon.setImageResource(R.mipmap.ic_tablet);
                break;
            case Drug.Type.TYPE_CAPSULE:
                holder.imgIcon.setImageResource(R.mipmap.ic_capsule);
                break;
            case Drug.Type.TYPE_SYRUP:
                holder.imgIcon.setImageResource(R.mipmap.ic_syrup);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.drugList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDrugName;
        TextView tvTime;
        TextView tvComment;
        ImageView imgIcon;

        MyViewHolder(final View itemView) {
            super(itemView);
            tvDrugName = itemView.findViewById(R.id.tvDrugName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvComment = itemView.findViewById(R.id.tvComment);
            imgIcon = itemView.findViewById(R.id.imgIcon);

        }
    }
}