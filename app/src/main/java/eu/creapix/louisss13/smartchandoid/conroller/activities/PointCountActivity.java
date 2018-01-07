package eu.creapix.louisss13.smartchandoid.conroller.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.dataAccess.PointCountDao;
import eu.creapix.louisss13.smartchandoid.model.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;
import eu.creapix.louisss13.smartchandoid.model.SendPointParams;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.PointLevelParser;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.ScoreCalculatedParser;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.PreferencesUtils;
import eu.creapix.louisss13.smartchandoid.utils.Utils;

public class PointCountActivity extends AppCompatActivity implements WebserviceListener {

    private TextView player1Score, player2Score;
    private TextView player1Name, player2Name;
    private TextView player1PreviousSets, player2PreviousSets;
    private int matchId;
    private int player1CurrentSetScore, player2CurrentSetScore;
    private View wait;
    private int asyncCnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_count);

        player1Score = findViewById(R.id.player_a_setScore);
        player2Score = findViewById(R.id.player_b_setScore);
        player1Name = findViewById(R.id.player_a_name);
        player2Name = findViewById(R.id.player_b_name);
        player1PreviousSets = findViewById(R.id.finished_sets_score_player_1);
        player2PreviousSets = findViewById(R.id.finished_sets_score_player_2);

        wait = findViewById(R.id.relative_wait);

        checkIntent(getIntent());

        TextView title = findViewById(R.id.title);
        title.setText(new StringBuilder().append(getString(R.string.title_activity_point_count_prefix)).append(String.valueOf(matchId)).toString());

        final TextView mAddPointPlayer1 = findViewById(R.id.player_a_sendScore);
        mAddPointPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.hasConnexion(getApplicationContext())) {
                    SendPointParams params = new SendPointParams(matchId, Constants.PLAYER_1_POINT, RequestMethods.POST);
                    mAddPointPlayer1.setBackgroundResource(R.color.secondaryLightColor);
                    mAddPointPlayer1.setTextColor(getResources().getColor(R.color.secondaryColor));
                    mAddPointPlayer1.setEnabled(false);
                    new SendPoint().execute(params);
                } else {
                    Toast.makeText(PointCountActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                }


            }
        });

        final TextView mAddPointPlayer2 = findViewById(R.id.player_b_sendScore);
        mAddPointPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.hasConnexion(getApplicationContext())) {
                    SendPointParams params = new SendPointParams(matchId, Constants.PLAYER_2_POINT, RequestMethods.POST);
                    mAddPointPlayer2.setBackgroundResource(R.color.secondaryLightColor);
                    mAddPointPlayer2.setTextColor(getResources().getColor(R.color.secondaryColor));
                    mAddPointPlayer2.setEnabled(false);
                    new SendPoint().execute(params);
                } else {
                    Toast.makeText(PointCountActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                }

            }
        });

        final TextView mDeletePointPlayer1 = findViewById(R.id.player_a_deleteScore);
        mDeletePointPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.hasConnexion(getApplicationContext())) {
                    SendPointParams params = new SendPointParams(matchId, Constants.PLAYER_1_POINT, RequestMethods.DELETE);
                    mDeletePointPlayer1.setBackgroundResource(R.color.secondaryLightColor);
                    mDeletePointPlayer1.setTextColor(getResources().getColor(R.color.secondaryColor));
                    mDeletePointPlayer1.setEnabled(false);
                    new SendPoint().execute(params);
                } else {
                    Toast.makeText(PointCountActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                }

            }
        });

        final TextView mDeletePointPlayer2 = findViewById(R.id.player_b_deleteScore);
        mDeletePointPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Utils.hasConnexion(getApplicationContext())) {
                    SendPointParams params = new SendPointParams(matchId, Constants.PLAYER_2_POINT, RequestMethods.DELETE);
                    mDeletePointPlayer2.setBackgroundResource(R.color.secondaryLightColor);
                    mDeletePointPlayer2.setTextColor(getResources().getColor(R.color.secondaryColor));
                    mDeletePointPlayer2.setEnabled(false);
                    new SendPoint().execute(params);
                } else {
                    Toast.makeText(PointCountActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onWebserviceFinishWithSuccess(final String method, final Integer scoredBy, final ArrayList<Object> datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (datas != null && datas.size() > 0){
                    ScoreCalculatedParser scores = (ScoreCalculatedParser) datas.get(0);
                    populateScores(scores.getPointLevels());
                }



                if (Constants.POST_POINT.equals(method)) {

                    switch (scoredBy) {
                        case Constants.PLAYER_1_POINT:
                            TextView mAddPointPlayer1 = findViewById(R.id.player_a_sendScore);
                            mAddPointPlayer1.setBackgroundResource(R.color.primaryLightColor);
                            mAddPointPlayer1.setTextColor(getResources().getColor(R.color.primaryDarkColor));
                            mAddPointPlayer1.setEnabled(true);
                            break;
                        case Constants.PLAYER_2_POINT:
                            TextView mAddPointPlayer2 = findViewById(R.id.player_b_sendScore);
                            mAddPointPlayer2.setBackgroundResource(R.color.primaryLightColor);
                            mAddPointPlayer2.setTextColor(getResources().getColor(R.color.primaryDarkColor));
                            mAddPointPlayer2.setEnabled(true);


                    }
                } else if (Constants.DELETE_POINT.equals(method)) {

                    switch (scoredBy) {
                        case Constants.PLAYER_1_POINT:
                            TextView mAddPointPlayer1 = findViewById(R.id.player_a_deleteScore);
                            mAddPointPlayer1.setBackgroundResource(R.color.primaryDarkColor);
                            mAddPointPlayer1.setTextColor(getResources().getColor(R.color.primaryLightColor));
                            mAddPointPlayer1.setEnabled(true);
                            break;
                        case Constants.PLAYER_2_POINT:
                            TextView mAddPointPlayer2 = findViewById(R.id.player_b_deleteScore);
                            mAddPointPlayer2.setBackgroundResource(R.color.primaryDarkColor);
                            mAddPointPlayer2.setTextColor(getResources().getColor(R.color.primaryLightColor));
                            mAddPointPlayer2.setEnabled(true);
                    }
                }
            }
        });
    }

    public void resetButtons(){
        TextView mAddPointPlayer = findViewById(R.id.player_a_sendScore);
        mAddPointPlayer.setBackgroundResource(R.color.primaryLightColor);
        mAddPointPlayer.setTextColor(getResources().getColor(R.color.primaryDarkColor));
        mAddPointPlayer.setEnabled(true);

        mAddPointPlayer = findViewById(R.id.player_b_sendScore);
        mAddPointPlayer.setBackgroundResource(R.color.primaryLightColor);
        mAddPointPlayer.setTextColor(getResources().getColor(R.color.primaryDarkColor));
        mAddPointPlayer.setEnabled(true);

        TextView mDeletePointPlayer = findViewById(R.id.player_a_deleteScore);
        mDeletePointPlayer.setBackgroundResource(R.color.primaryDarkColor);
        mDeletePointPlayer.setTextColor(getResources().getColor(R.color.primaryLightColor));
        mDeletePointPlayer.setEnabled(true);

        mDeletePointPlayer = findViewById(R.id.player_b_deleteScore);
        mDeletePointPlayer.setBackgroundResource(R.color.primaryDarkColor);
        mDeletePointPlayer.setTextColor(getResources().getColor(R.color.primaryLightColor));
        mDeletePointPlayer.setEnabled(true);
    }


    @Override
    public void onWebserviceFinishWithError(String error, final int errorCode) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (errorCode) {
                    case 400:
                        Toast.makeText(PointCountActivity.this, R.string.error_unauthorized, Toast.LENGTH_SHORT).show();
                        break;
                    case 401:
                        Utils.alertSessionExpired(PointCountActivity.this);
                        break;
                    default:
                        Utils.alertError(PointCountActivity.this,getString(R.string.server_error_title), getString(R.string.server_error_content));
                        break;
                }
                resetButtons();
            }
        });

    }

    private class SendPoint extends AsyncTask<SendPointParams, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            wait.setVisibility(View.VISIBLE);
            asyncCnt++;
        }

        @Override
        protected Boolean doInBackground(SendPointParams... params) {
            try {

                PointCountDao pointCountDao = new PointCountDao();
                pointCountDao.postPoint(
                        PointCountActivity.this,
                        params[0].getScoredBy(),
                        params[0].getMatchId(),
                        PreferencesUtils.getToken(PointCountActivity.this),
                        params[0].getMethod()
                );
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aSuccess) {
            super.onPostExecute(aSuccess);
            asyncCnt--;
            if (asyncCnt == 0) wait.setVisibility(View.GONE);
            if (!(aSuccess)) {
                Toast.makeText(PointCountActivity.this, R.string.error_connection_lost_title, Toast.LENGTH_SHORT).show();
                Utils.alertError(PointCountActivity.this, getString(R.string.error_connection_lost_title),getString(R.string.error_connection_lost_content));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resetButtons();
                    }
                });
            }
        }
    }

    private String getFinishedSetsDisplay(ArrayList<PointLevelParser> sets, int scoredBy){

        StringBuilder finishedSetsDisplay = new StringBuilder();
        if (sets != null ) {
            switch (scoredBy) {
                case Constants.PLAYER_1_POINT:
                    for (int i = 0; i < sets.size() - 1; i++) {
                        finishedSetsDisplay.append(sets.get(i).getScorePlayer1()).append("  ");
                    }
                    break;
                case Constants.PLAYER_2_POINT:
                    for (int i = 0; i < sets.size() - 1; i++) {
                        finishedSetsDisplay.append(sets.get(i).getScorePlayer2()).append("  ");
                    }
                    break;
            }
            if (sets.size() > 1) {
                finishedSetsDisplay.substring(0, finishedSetsDisplay.length() - 2);
            }
        }
        return finishedSetsDisplay.toString();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkIntent(intent);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK, new Intent());
        finish();
    }

    public void populateScores(ArrayList<PointLevelParser> currentScore){

        String displayPreviousSetsPlayer1 = this.getFinishedSetsDisplay(currentScore, Constants.PLAYER_1_POINT);
        String displayPreviousSetsPlayer2 = this.getFinishedSetsDisplay(currentScore, Constants.PLAYER_2_POINT);

        player1PreviousSets.setText(displayPreviousSetsPlayer1);
        player2PreviousSets.setText(displayPreviousSetsPlayer2);

        if (currentScore != null) {
            player1CurrentSetScore = currentScore.get(currentScore.size() - 1).getScorePlayer1();
            player2CurrentSetScore = currentScore.get(currentScore.size() - 1).getScorePlayer2();
        } else {
            player1CurrentSetScore = 0;
            player2CurrentSetScore = 0;
        }
        player1Score.setText(String.valueOf(player1CurrentSetScore));
        player2Score.setText(String.valueOf(player2CurrentSetScore));

    }

    private void checkIntent(Intent intent) {
        if (intent.hasExtra(Constants.MATCH_ID)) {
            int matchId = intent.getIntExtra(Constants.MATCH_ID, 0);
            Log.e("MATCH_ID", "#" + matchId);
            this.matchId = matchId;

        }
        if (intent.hasExtra(Constants.MATCH_SCORE) && intent.hasExtra(Constants.PLAYER_1_NAME) && intent.hasExtra(Constants.PLAYER_2_NAME)) {

            ScoreCalculatedParser matchState = (ScoreCalculatedParser) intent.getSerializableExtra(Constants.MATCH_SCORE);
            ArrayList<PointLevelParser> currentScore = matchState.getPointLevels();

            player1Name.setText(intent.getStringExtra(Constants.PLAYER_1_NAME));
            player2Name.setText(intent.getStringExtra(Constants.PLAYER_2_NAME));
            populateScores(currentScore);
        }

    }

}
