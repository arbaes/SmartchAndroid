package eu.creapix.louisss13.smartchandoid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.activities.MonitoredMatchesActivity;
import eu.creapix.louisss13.smartchandoid.R;

/**
 * Created by arnau on 28-12-17.
 */

public class MonitoredMatchAdapter extends RecyclerView.Adapter<MonitoredMatchAdapter.MonitoredMatchViewHolder> {

    private ArrayList<PlayerScore> playerScores;
    private MonitoredMatchesActivity activity;

    public MonitoredMatchAdapter(MonitoredMatchesActivity activity, ArrayList<PlayerScore> playerScores) {
        this.playerScores = playerScores;
        this.activity = activity;
    }

    @Override
    public MonitoredMatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_match_layout, parent, false);
        return new MonitoredMatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonitoredMatchViewHolder viewHolder, int position) {
        PlayerScore playerScore = playerScores.get(viewHolder.getAdapterPosition());

        String score = playerScore.getPlayerScore() + " - " + playerScore.getPlayerScore();
        viewHolder.playerLeftName.setText(playerScore.getPlayerName());
        viewHolder.playerScores.setText(score);
        viewHolder.playerRightName.setText(playerScore.getPlayerName());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.monitorMatch();
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerScores.size();
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
