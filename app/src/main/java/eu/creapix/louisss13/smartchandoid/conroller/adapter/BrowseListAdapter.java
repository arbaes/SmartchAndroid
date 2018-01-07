package eu.creapix.louisss13.smartchandoid.conroller.adapter;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.lang3.text.WordUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import eu.creapix.louisss13.smartchandoid.conroller.activities.MatchListActivity;
import eu.creapix.louisss13.smartchandoid.dataAccess.WebserviceListener;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.MatchParser;
import eu.creapix.louisss13.smartchandoid.model.jsonParsers.TournamentParser;
import eu.creapix.louisss13.smartchandoid.R;
import eu.creapix.louisss13.smartchandoid.utils.Constants;
import eu.creapix.louisss13.smartchandoid.utils.Utils;

/**
 * Created by arnau on 29-12-17.
 */

public class BrowseListAdapter extends RecyclerView.Adapter<BrowseListAdapter.BrowseListViewHolder> {

    private ArrayList<Object> datas;
    private MatchListActivity activity;
    private TabLayout tabLayout;
    private WebserviceListener wsListener;

    private int viewType;

    public BrowseListAdapter(MatchListActivity activity, ArrayList<Object> datas, int viewType, TabLayout tabLayout, WebserviceListener webserviceListener) {
        this.datas = datas;
        this.activity = activity;
        this.viewType = viewType;
        this.tabLayout = tabLayout;
        this.wsListener = webserviceListener;
    }

    @Override
    public int getItemViewType(int position) {

        return viewType;
    }

    @Override
    public BrowseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == Constants.TYPE_TOURNAMENT_DETAILS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_tournament_details, parent, false);
            return new BrowseListViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tournament, parent, false);
        return new BrowseListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BrowseListViewHolder viewHolder, int position) {
        if((datas.get(viewHolder.getAdapterPosition()) instanceof TournamentParser) && (viewType == Constants.TYPE_TOURNAMENT)) {
            populateTournament(viewHolder, (TournamentParser) datas.get(viewHolder.getAdapterPosition()));

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ArrayList<Object> selectedTrounament = new ArrayList<Object>();
                    selectedTrounament.add(datas.get(viewHolder.getAdapterPosition()));
                    TabLayout.Tab tabsel = tabLayout.getTabAt(1);
                    tabsel.select();
                    wsListener.onWebserviceFinishWithSuccess(Constants.GET_MATCHES,null,selectedTrounament);

                }
            });

        } else if ((datas.get(viewHolder.getAdapterPosition()) instanceof TournamentParser) && (viewType == Constants.TYPE_TOURNAMENT_DETAILS)){
            populateTournamentDetails(viewHolder, (TournamentParser) datas.get(viewHolder.getAdapterPosition()));
        }
    }

    private void populateTournamentDetails(BrowseListViewHolder viewHolder, TournamentParser tournament) {

        viewHolder.tournamentNameDetails.setText(tournament.getName());

        viewHolder.addressLine1.setText(tournament.getAddress().getStreet() + " " + tournament.getAddress().getNumber());
        viewHolder.addressLine2.setText(tournament.getAddress().getZipCode()+" "+tournament.getAddress().getCity());


        String startDate = tournament.getBeginDate();
        String endDate = tournament.getEndDate();

        String[] parsedDateTime = Utils.getParsedDateTime(startDate);
        String startDateParsedString = parsedDateTime[0];
        String startTimeParsedString = parsedDateTime[1];

        parsedDateTime = Utils.getParsedDateTime(endDate);
        String endDateParsedString = parsedDateTime[0];
        String endTimeParsedString = parsedDateTime[1];

        viewHolder.startDate.setText(startDateParsedString);
        viewHolder.startTime.setText(startTimeParsedString);

        viewHolder.endDate.setText(endDateParsedString);
        viewHolder.endTime.setText(endTimeParsedString);

        String tournamentState = "";
        switch (tournament.getEtat()){
            case 2:
                tournamentState = this.activity.getResources().getString(R.string.tournament_status_pending);
                break;
            case 1:
                tournamentState = this.activity.getResources().getString(R.string.tournament_status_closed);
                break;
            case 0:
                tournamentState = this.activity.getResources().getString(R.string.tournament_status_ongoing);
                break;
        }
        viewHolder.statusTournament.setText(tournamentState);



    }

    private void populateTournament(BrowseListViewHolder viewHolder, TournamentParser tournament) {
        String tournamentState = "";
        switch (tournament.getEtat()){
            case 2:
                tournamentState = this.activity.getResources().getString(R.string.tournament_status_pending);
                break;
            case 1:
                tournamentState = this.activity.getResources().getString(R.string.tournament_status_closed);
                break;
            case 0:
                tournamentState = this.activity.getResources().getString(R.string.tournament_status_ongoing);
                break;
        }

        viewHolder.tournamentName.setText(String.valueOf(tournament.getName()));
        //viewHolder.tournamentId.setText(tournamentId);
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
        private TextView startDate, endDate, startTime, endTime, addressLine1, addressLine2, statusTournament, tournamentNameDetails;

        public BrowseListViewHolder(View itemView) {
            super(itemView);
            tournamentName = (TextView) itemView.findViewById(R.id.tournament_label);
            tournamentId = (TextView) itemView.findViewById(R.id.tournament_id);
            tournamentState = (TextView) itemView.findViewById(R.id.tournament_state);

            startDate = itemView.findViewById(R.id.start_date);
            endDate = itemView.findViewById(R.id.end_date);
            startTime = itemView.findViewById(R.id.start_time);
            endTime = itemView.findViewById(R.id.end_time);
            addressLine1 = itemView.findViewById(R.id.address_line_1);
            addressLine2 = itemView.findViewById(R.id.address_line_2);
            statusTournament = itemView.findViewById(R.id.status_content);
            tournamentNameDetails = itemView.findViewById(R.id.tournament_name);
        }
    }

}