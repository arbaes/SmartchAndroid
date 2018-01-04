package eu.creapix.louisss13.smartchandoid.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.dataAccess.PointCountDao;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.model.PlayerScore;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;

public class PointCountActivity extends AppCompatActivity implements WebserviceListener {

    private TextView player1Score, player2Score;
    private TextView player1Name, player2Name;
    private int matchId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_count);

        player1Score = findViewById(R.id.player_a_setScore);
        player2Score = findViewById(R.id.player_b_setScore);
        player1Name = findViewById(R.id.player_a_name);
        player2Name = findViewById(R.id.player_b_name);

        checkIntent(getIntent());

        final TextView mAddPointPlayer1 = findViewById(R.id.player_a_sendScore);
        mAddPointPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendPointsParams params = new SendPointsParams(matchId,Constants.PLAYER_1_POINT,RequestMethods.POST);
                mAddPointPlayer1.setBackgroundResource(R.color.colorAccent);
                mAddPointPlayer1.setEnabled(false);
                new SendPoint().execute(params);

            }
        });

        final TextView mAddPointPlayer2 = findViewById(R.id.player_b_sendScore);
        mAddPointPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendPointsParams params = new SendPointsParams(matchId,Constants.PLAYER_2_POINT,RequestMethods.POST);
                mAddPointPlayer2.setBackgroundResource(R.color.colorAccent);
                mAddPointPlayer2.setEnabled(false);
                new SendPoint().execute(params);

            }
        });

        final TextView mDeletePointPlayer1 = findViewById(R.id.player_a_deleteScore);
        mDeletePointPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendPointsParams params = new SendPointsParams(matchId,Constants.PLAYER_1_POINT,RequestMethods.DELETE);
                mDeletePointPlayer1.setBackgroundResource(R.color.colorAccent);
                mDeletePointPlayer1.setEnabled(false);
                new SendPoint().execute(params);

            }
        });

        final TextView mDeletePointPlayer2 = findViewById(R.id.player_b_deleteScore);
        mDeletePointPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SendPointsParams params = new SendPointsParams(matchId,Constants.PLAYER_2_POINT,RequestMethods.DELETE);
                mDeletePointPlayer2.setBackgroundResource(R.color.colorAccent);
                mDeletePointPlayer2.setEnabled(false);
                new SendPoint().execute(params);

            }
        });
    }

    @Override
    public void onWebserviceFinishWithSuccess(final String method, final ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (Constants.POST_POINT.equals(method)) {

                    int scoredBy = (Integer) datas.get(0);
                    updateScore( scoredBy,Constants.ADD_POINT);
                    switch (scoredBy){
                        case Constants.PLAYER_1_POINT:
                            TextView mAddPointPlayer1 = findViewById(R.id.player_a_sendScore);
                            mAddPointPlayer1.setBackgroundResource(R.color.primaryLightColor);
                            mAddPointPlayer1.setEnabled(true);
                            break;
                        case Constants.PLAYER_2_POINT:
                            TextView mAddPointPlayer2 = findViewById(R.id.player_b_sendScore);
                            mAddPointPlayer2.setBackgroundResource(R.color.primaryLightColor);
                            mAddPointPlayer2.setEnabled(true);


                    }
                } else if (Constants.DELETE_POINT.equals(method)){
                    Log.e("DELETE","OK");
                    int scoredBy = (Integer) datas.get(0);
                    updateScore( scoredBy,Constants.DELETE_POINT);
                    switch (scoredBy){
                        case Constants.PLAYER_1_POINT:
                            TextView mAddPointPlayer1 = findViewById(R.id.player_a_deleteScore);
                            mAddPointPlayer1.setBackgroundResource(R.color.primaryDarkColor);
                            mAddPointPlayer1.setEnabled(true);
                            break;
                        case Constants.PLAYER_2_POINT:
                            TextView mAddPointPlayer2 = findViewById(R.id.player_b_deleteScore);
                            mAddPointPlayer2.setBackgroundResource(R.color.primaryDarkColor);
                            mAddPointPlayer2.setEnabled(true);


                    }

                }
            }
        });

    }

    @Override
    public void onWebserviceFinishWithError(String error) {

    }

    private class SendPoint extends AsyncTask<SendPointsParams, Void, Void> {

        @Override
        protected Void doInBackground(SendPointsParams... params) {
            try {

                PointCountDao pointCountDao = new PointCountDao();

                pointCountDao.postPoint(
                        PointCountActivity.this,
                        params[0].scoredBy,
                        params[0].matchId,
                        PreferencesUtils.getToken(PointCountActivity.this),
                        params[0].method
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
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
            this.matchId = matchId;

        }
        if (intent.hasExtra(Constants.MATCH_SCORE)) {


            PlayerScore currentScore = (PlayerScore) intent.getSerializableExtra(Constants.MATCH_SCORE);
            player1Name.setText(currentScore.getPlayer1Name());
            player1Score.setText(""+ currentScore.getPlayer1Score());

            player2Name.setText(currentScore.getPlayer2Name());
            player2Score.setText(""+currentScore.getPlayer2Score());
        }

    }

    public void updateScore(int scoredBy, String actionType){

        TextView currentScoreView = null;
        switch (scoredBy){
            case Constants.PLAYER_1_POINT:
                currentScoreView = player1Score;
                break;
            case Constants.PLAYER_2_POINT:
                currentScoreView = player2Score;
                break;
        }
        int currentScore = Integer.parseInt(currentScoreView.getText().toString());

        switch (actionType){
            case Constants.ADD_POINT:
                currentScore++;
                break;
            case Constants.DELETE_POINT:
                currentScore--;
                break;
        }

        currentScoreView.setText(""+currentScore);
    }

    private static class SendPointsParams {
        int matchId;
        int scoredBy;
        RequestMethods method;

        public SendPointsParams(int matchId, int scoredBy, RequestMethods method) {
            this.matchId = matchId;
            this.scoredBy = scoredBy;
            this.method = method;
        }

    }

}
