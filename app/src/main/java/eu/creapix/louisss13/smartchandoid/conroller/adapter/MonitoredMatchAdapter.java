package eu.creapix.louisss13.smartchandoid.conroller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.conroller.activities.MonitoredMatchesActivity;
import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.ScoreCalculatedParser;

/**
 * Created by Arnaud Baes on 28-12-17.
 * IG-3C 2017 - 2018
 */

public class MonitoredMatchAdapter extends RecyclerView.Adapter<MonitoredMatchAdapter.MonitoredMatchViewHolder> {

    private ArrayList<Object> matchs;
    private MonitoredMatchesActivity activity;

    public MonitoredMatchAdapter(MonitoredMatchesActivity activity, ArrayList<Object> matchs) {
        this.matchs = matchs;
        this.activity = activity;
    }

    @Override
    public MonitoredMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_match_layout, parent, false);
        return new MonitoredMatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MonitoredMatchViewHolder viewHolder, int position) {

        PlayerScore matchScore = ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getMatchScore();
        final ScoreCalculatedParser matchState = ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getScore();

        final String player1Name = ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer1().getLastName() + " " + ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer1().getFirstName().charAt(0) + ".";
        final String player2Name = ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer2().getLastName() + " " + ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer2().getFirstName().charAt(0) + ".";


        String score = matchScore.getPlayer1Score() + " - " + matchScore.getPlayer2Score();
        viewHolder.playerLeftName.setText(player1Name);
        viewHolder.playerScores.setText(score);
        viewHolder.playerRightName.setText(player2Name);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.monitorMatch(((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getId(), matchState, player1Name, player2Name);
            }
        });
    }

    public ArrayList<Object> getMatchs() {
        return matchs;
    }

    @Override
    public int getItemCount() {
        return matchs.size();
    }

    static class MonitoredMatchViewHolder extends RecyclerView.ViewHolder {
        private TextView playerLeftName, playerScores, playerRightName;

        MonitoredMatchViewHolder(View itemView) {
            super(itemView);
            playerLeftName = itemView.findViewById(R.id.player_left);
            playerRightName = itemView.findViewById(R.id.player_right);
            playerScores = itemView.findViewById(R.id.score);
        }
    }
}
