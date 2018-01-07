package eu.creapix.louisss13.smartchandoid.conroller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.conroller.activities.ViewUserDetailsActivity;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.ClubParser;

/**
 * Created by arnau on 29-12-17.
 */

public class ClubListAdapter extends RecyclerView.Adapter<ClubListAdapter.ClubListViewHolder> {

    private ArrayList<Object> datas;
    private ViewUserDetailsActivity activity;

    public ClubListAdapter(ViewUserDetailsActivity activity, ArrayList<Object> datas) {
        this.datas = datas;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public ClubListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_club, parent, false);
        return new ClubListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClubListViewHolder viewHolder, int position) {
        populateClub(viewHolder, (ClubParser) datas.get(viewHolder.getAdapterPosition()));
    }

    private void populateClub(ClubListViewHolder viewHolder, ClubParser club) {
        viewHolder.clubName.setText(club.getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ClubListViewHolder extends RecyclerView.ViewHolder {
        private TextView clubName;

        public ClubListViewHolder(View itemView) {
            super(itemView);
            clubName = (TextView) itemView.findViewById(R.id.club_name);
        }
    }
}