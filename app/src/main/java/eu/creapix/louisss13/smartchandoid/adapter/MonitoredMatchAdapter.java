package eu.creapix.louisss13.smartchandoid.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.activities.MonitoredMatchesActivity;
import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.model.PlayerScore;

/**
 * Created by arnau on 28-12-17.
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
        Log.e("TAG", "onBindViewHolder: " + ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getId() + " - " + ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getMatchScore().getPlayer1Score() + " - " + ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getMatchScore().getPlayer2Score());
        PlayerScore matchScore = ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getMatchScore();

        String player1Name = ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer1().getLastName() + " " + ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer1().getFirstName().charAt(0) + ".";
        String player2Name = ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer2().getLastName() + " " + ((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getPlayer2().getFirstName().charAt(0) + ".";
        matchScore.setPlayer1Name(player1Name);
        matchScore.setPlayer2Name(player2Name);
        final PlayerScore matchScoreSetted = matchScore;


        String score = matchScore.getPlayer1Score() + " - " + matchScore.getPlayer2Score();
        viewHolder.playerLeftName.setText(matchScore.getPlayer1Name());
        viewHolder.playerScores.setText(score);
        viewHolder.playerRightName.setText(matchScore.getPlayer2Name());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.monitorMatch(((MatchParser) matchs.get(viewHolder.getAdapterPosition())).getId(), matchScoreSetted);
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

    public static class MonitoredMatchViewHolder extends RecyclerView.ViewHolder {
        private TextView playerLeftName, playerScores, playerRightName;

        public MonitoredMatchViewHolder(View itemView) {
            super(itemView);
            playerLeftName = (TextView) itemView.findViewById(R.id.player_left);
            playerRightName = (TextView) itemView.findViewById(R.id.player_right);
            playerScores = (TextView) itemView.findViewById(R.id.score);
        }
    }
}
