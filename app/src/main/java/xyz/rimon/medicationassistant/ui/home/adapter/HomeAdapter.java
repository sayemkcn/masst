package xyz.rimon.medicationassistant.ui.home.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import xyz.rimon.medicationassistant.R;
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
        View view = inflater.inflate(R.layout.single_drug_item, parent, false);
        return new HomeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {
        Drug drug = this.drugList.get(position);
        holder.tvDrugName.setText(drug.getType() + " " + drug.getName());
        holder.tvTag.setText(drug.getTimes().length
                + " "
                + context.getResources().getString(R.string.msg_timesADay)
                + " "
                + drug.getDaysCount()
                + " "
                + context.getResources().getString(R.string.msg_forDays));
        holder.alertSwitch.setChecked(drug.isAlert());
    }

    @Override
    public int getItemCount() {
        return this.drugList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDrugName;
        TextView tvTag;
        TextView tvComment;
        Switch alertSwitch;

        MyViewHolder(final View itemView) {
            super(itemView);
            tvDrugName = itemView.findViewById(R.id.tvDrugName);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvComment = itemView.findViewById(R.id.tvComment);
            alertSwitch = itemView.findViewById(R.id.alertSwitch);

            // item long press action
            final String[] options = {"Edit", "Delete"};
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(context)
                            .setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (i == 0) {
                                        ((CoreActivity) context).loadChildFragment(AddDrugFragment_.builder().isEdit(true).position(getAdapterPosition()).build());
                                    } else if (i == 1) {
                                        drugList.remove(getAdapterPosition());
                                        StorageUtils.writeObjects(StorageUtils.ALL_DRUGS_FILE, drugList);
                                        notifyDataSetChanged();
                                    }
                                }
                            })
                            .show();
                    return false;
                }
            });

        }
    }
}