package xyz.rimon.medicationassistant.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.rimon.medicationassistant.R;
import xyz.rimon.medicationassistant.domains.Drug;

/**
 * Created by SAyEM on 8/11/17.
 */

public class DrugListAdapter extends RecyclerView.Adapter<DrugListAdapter.MyViewHolder> {
    private Context context;
    private List<Drug> drugList;

    public DrugListAdapter(Context context, List<Drug> drugList) {
        this.context = context;
        this.drugList = drugList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.single_drug_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Drug drug = this.drugList.get(position);
        holder.tvDrugName.setText(drug.getName());
        holder.tvTag.setText(drug.getTimes().length
                + " "
                + context.getResources().getString(R.string.msg_timesADay)
                + " "
                + drug.getDaysCount()
                + " "
                + context.getResources().getString(R.string.msg_forDays));
    }

    @Override
    public int getItemCount() {
        return this.drugList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDrugName;
        TextView tvTag;
        TextView tvComment;

        MyViewHolder(View itemView) {
            super(itemView);
            tvDrugName = itemView.findViewById(R.id.tvDrugName);
            tvTag = itemView.findViewById(R.id.tvTag);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }
}
