package eu.creapix.louisss13.smartchandoid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.activities.MonitoredMatchesActivity;
import eu.creapix.louisss13.smartchandoid.R;

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
    public void onBindViewHolder(MonitoredMatchViewHolder viewHolder, int position) {
        final MatchParser matchData = (MatchParser) matchs.get(viewHolder.getAdapterPosition());


        PlayerScore matchScore = matchData.getMatchScore();

        String player1Name = matchData.getPlayer1().getLastName() + " " + matchData.getPlayer1().getFirstName().charAt(0)+".";
        String player2Name = matchData.getPlayer2().getLastName() + " " + matchData.getPlayer2().getFirstName().charAt(0)+".";
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
                activity.monitorMatch(matchData.getId(), matchScoreSetted);
            }
        });
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
