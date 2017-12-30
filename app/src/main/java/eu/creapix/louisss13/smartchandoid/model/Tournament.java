package eu.creapix.louisss13.smartchandoid.model;

import java.util.ArrayList;
import java.util.Date;

import eu.creapix.louisss13.smartchandoid.utils.enums.ETournamentState;

/**
 * Created by arnau on 29-12-17.
 */

public class Tournament {

    private int id;
    private String name;
    private Club club;
    private Address address;
    private Date beginDate;
    private Date endDate;
    private ETournamentState state;
    private ArrayList<Player> participants;
    private ArrayList<UserAccount> admins;
    private ArrayList<MatchsPhase> matches;

    public Tournament() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ETournamentState getState() {
        return state;
    }

    public void setState(ETournamentState state) {
        this.state = state;
    }

    public ArrayList<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Player> participants) {
        this.participants = participants;
    }

    public ArrayList<UserAccount> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<UserAccount> admins) {
        this.admins = admins;
    }

    public ArrayList<MatchsPhase> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<MatchsPhase> matches) {
        this.matches = matches;
    }
}
