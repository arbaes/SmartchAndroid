package eu.creapix.louisss13.smartchandoid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.utils.Constants;

public class PointCountActivity extends AppCompatActivity {

    private TextView player1Score, player2Score;
    private TextView player1Name, player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_count);

        player1Score = findViewById(R.id.player_a_setScore);
        player2Score = findViewById(R.id.player_b_setScore);
        player1Name = findViewById(R.id.player_a_name);
        player2Name = findViewById(R.id.player_b_name);

        checkIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }

    private void checkIntent(Intent intent) {
        if (intent.hasExtra(Constants.MATCH_ID)) {
            int matchId = intent.getIntExtra(Constants.MATCH_ID, 0 );
            Log.e("MATCH_ID", "#" + matchId);
            setTitle("Match #"+matchId);

        }
        if (intent.hasExtra(Constants.MATCH_SCORE)) {

            PlayerScore currentScore = (PlayerScore) intent.getSerializableExtra(Constants.MATCH_SCORE);
            player1Name.setText(currentScore.getPlayer1Name());
            player1Score.setText(""+ currentScore.getPlayer1Score());

            player2Name.setText(currentScore.getPlayer2Name());
            player2Score.setText(""+currentScore.getPlayer2Score());
        }

    }
}
