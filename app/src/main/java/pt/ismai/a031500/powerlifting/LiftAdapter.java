package pt.ismai.a031500.powerlifting;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

public class LiftAdapter extends FirestoreRecyclerAdapter<Lift, LiftAdapter.LiftHolder> {

    public LiftAdapter(@NonNull FirestoreRecyclerOptions<Lift> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LiftHolder holder, int position, @NonNull Lift model) {
        /* if (position % 2 == 1) {
            holder.lift_row.setBackgroundResource(R.drawable.border_body);
            holder.weight_row.setBackgroundResource(R.drawable.border_body);
            holder.repMax_row.setBackgroundResource(R.drawable.border_body);
            holder.reps_row.setBackgroundResource(R.drawable.border_body);
            holder.date_row.setBackgroundResource(R.drawable.border_body);
        } */
        holder.lift_row.setText(model.getLift());
        holder.weight_row.setText(Integer.toString(model.getWeight()));
        holder.repMax_row.setText(Integer.toString(model.getRm1()));
        holder.reps_row.setText(Integer.toString(model.getReps()));
        holder.date_row.setText(timestampToString(model.getDate()));
    }

    private String timestampToString(Timestamp timestamp) {
        String timestampStr = (timestamp.toDate()).toString();
        String[] dateArr = timestampStr.split(" ");
        String month;
        switch (dateArr[1]) {
            case "Jan":
                month = "01";
                break;
            case "Feb":
                month = "02";
                break;
            case "Mar":
                month = "03";
                break;
            case "Apr":
                month = "04";
                break;
            case "May":
                month = "05";
                break;
            case "Jun":
                month = "06";
                break;
            case "Jul":
                month = "07";
                break;
            case "Aug":
                month = "08";
                break;
            case "Sep":
                month = "09";
                break;
            case "Oct":
                month = "10";
                break;
            case "Nov":
                month = "11";
                break;
            case "Dec":
                month = "12";
                break;
            default:
                month = "00";
        }
        String dateStr = month + "/" + dateArr[2] + "/" + dateArr[5];
        return dateStr;
    }

    @NonNull
    @Override
    // Add views to a ViewHolder to improve performance (when we scroll down items are being added
    // to a queue and when we scroll up the system loads the elements from that queue instead of
    // calling again findViewById)
    public LiftHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lift_row, viewGroup, false);
        return new LiftHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class LiftHolder extends RecyclerView.ViewHolder {
        TextView lift_row, weight_row, repMax_row, reps_row, date_row;

        public LiftHolder(View itemView) {
            super(itemView);
            lift_row = itemView.findViewById(R.id.lift_row);
            weight_row = itemView.findViewById(R.id.weight_row);
            repMax_row = itemView.findViewById(R.id.repMax_row);
            reps_row = itemView.findViewById(R.id.reps_row);
            date_row = itemView.findViewById(R.id.date_row);
        }
    }
}
