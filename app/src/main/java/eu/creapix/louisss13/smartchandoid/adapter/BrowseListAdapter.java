package eu.creapix.louisss13.smartchandoid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.activities.MatchListActivity;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.TournamentParser;
import eu.creapix.louisss13.smartchandoid.R;

/**
 * Created by arnau on 29-12-17.
 */

public class BrowseListAdapter extends RecyclerView.Adapter<BrowseListAdapter.BrowseListViewHolder> {

    private ArrayList<Object> datas;
    private MatchListActivity activity;

    private final int TYPE_MATCH = 0;
    private final int TYPE_TOURNAMENT = 1;

    public BrowseListAdapter(MatchListActivity activity, ArrayList<Object> datas) {
        this.datas = datas;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
       if(datas.get(position) instanceof TournamentParser)
           return TYPE_TOURNAMENT;

       return TYPE_MATCH;
    }

    @Override
    public BrowseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_MATCH) {
            // TODO return row_matches + create view holder
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tournament, parent, false);
            return new BrowseListViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tournament, parent, false);
        return new BrowseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BrowseListViewHolder viewHolder, int position) {
        if(datas.get(viewHolder.getAdapterPosition()) instanceof TournamentParser) {
            populateTournament(viewHolder, (TournamentParser) datas.get(viewHolder.getAdapterPosition()));
        } else {
            populateMatch(viewHolder);
        }
    }

    private void populateMatch(BrowseListViewHolder viewHolder) {

    }

    private void populateTournament(BrowseListViewHolder viewHolder, TournamentParser tournament) {
        String tournamentId = "#IDXXXXXXX";
        String tournamentState = "";
        switch (tournament.getEtat()){
            case 2:
                tournamentState = "EN COURS";
                break;
            case 1:
                tournamentState = "FINI";
                break;
            case 3:
                tournamentState = "EN PREPARATION";
                break;
        }

        viewHolder.tournamentName.setText(tournament.getName());
        viewHolder.tournamentId.setText(tournamentId);
        viewHolder.tournamentState.setText(tournamentState);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //activity.monitorMatch();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class BrowseListViewHolder extends RecyclerView.ViewHolder {
        private TextView tournamentName, tournamentId, tournamentState;

        public BrowseListViewHolder(View itemView) {
            super(itemView);
            tournamentName = (TextView) itemView.findViewById(R.id.tournament_label);
            tournamentId = (TextView) itemView.findViewById(R.id.tournament_id);
            tournamentState = (TextView) itemView.findViewById(R.id.tournament_state);
        }
    }
}