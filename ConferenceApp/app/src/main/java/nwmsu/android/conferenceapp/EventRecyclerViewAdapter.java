package nwmsu.android.conferenceapp;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.EventRecyclerViewHolder> {

    private ArrayList<EventRecyclerData> sessionsDataArrayList;
    private Context mcontext;

    public EventRecyclerViewAdapter(ArrayList<EventRecyclerData> recyclerDataArrayList, Context mcontext) {
        this.sessionsDataArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public EventRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.session_layout, parent, false);
        return new EventRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        EventRecyclerData recyclerData = sessionsDataArrayList.get(position);
        holder.nameTV.setText(recyclerData.getName());
        holder.timeTV.setText(recyclerData.getTime());
        holder.descriptionTV.setText(recyclerData.getDescription());
        //holder.iconIV.setImageResource(recyclerData.getIconID());

        //make a function that retrieves RSVP icon based on boolean
        //int rsvpIconID = getRsvpIcon( recyclerData.getTracked());
        //holder.rsvpIV.setImageResource( rsvpIconID);
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return sessionsDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class EventRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTV;
        private TextView timeTV;
        private TextView descriptionTV;

        //private ImageView iconIV;
        //private ImageView rsvpIV;

        public EventRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.idSessionName);
            timeTV = itemView.findViewById(R.id.idSessionTime);
            descriptionTV = itemView.findViewById(R.id.idSessionDescription);

            //iconIV = itemView.findViewById(R.id.idSessionIcon);
            //rsvpIV = itemView.findViewById(R.id.idSessionRSVP);
        }
    }
}
