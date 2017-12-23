package eu.creapix.louisss13.smartchandoid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import eu.creapix.louisss13.smartchandoid.Model.PlayerScore;

/**
 * Created by arnau on 21-12-17.
 */

public class MonitoredMatchesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitored_matches);

        final ListView listview = (ListView) findViewById(R.id.listview);

        final ArrayList<PlayerScore> playerScores = new ArrayList<PlayerScore>();
        for (int i = 1; i <= 20; i++) {
            playerScores.add(new PlayerScore("Joueur" + i, i+7));
        }


        final PlayerScoreAdapter adapter = new PlayerScoreAdapter(this, playerScores);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                MonitorMatch();
            }

        });
    }

    public void MonitorMatch() {
        Intent intent = new Intent(this, PointCountActivity.class);
        startActivity(intent);
    }

    public class PlayerScoreAdapter extends ArrayAdapter<PlayerScore> {

        //tweets est la liste des models à afficher
        public PlayerScoreAdapter(Context context, List<PlayerScore> playerScores) {
            super(context, 0, playerScores);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.rowlayout,parent, false);
            }

            MatchViewHolder viewHolder = (MatchViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new MatchViewHolder();
                viewHolder.playerLeft_name = (TextView) convertView.findViewById(R.id.player_left);
                viewHolder.playerScores = (TextView) convertView.findViewById(R.id.score);
                viewHolder.playerRight_name = (TextView) convertView.findViewById(R.id.player_right);
                convertView.setTag(viewHolder);
            }

            //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
            PlayerScore playerScore = getItem(position);

            //il ne reste plus qu'à remplir notre vue
            viewHolder.playerLeft_name.setText(playerScore.getPlayerName());
            viewHolder.playerScores.setText(playerScore.getPlayerScore() + " - " + playerScore.getPlayerScore());
            viewHolder.playerRight_name.setText(playerScore.getPlayerName());

            return convertView;
        }

        private class MatchViewHolder{
            public TextView playerLeft_name;
            public TextView playerScores;
            public TextView playerRight_name;
        }

    }

    /*
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }*/

}