package eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers;

import org.json.JSONObject;

import java.util.ArrayList;

import eu.creapix.louisss13.smartchandoid.model.Address;
import eu.creapix.louisss13.smartchandoid.model.Tournament;

/**
 * Created by arnau on 30-12-17.
 */

public class TournamentParser extends JSONObject {

    private AddressParser address;
    private Integer[] adminsId;
    private String beginDate;
    private int clubId;
    private String endDate;
    private int etat;
    private Integer[] matchesId;
    private String name;
    private Integer[] participantsId;

    public AddressParser getAddress() {
        return address;
    }

    public void setAddress(AddressParser address) {
        this.address = address;
    }

    public Integer[] getAdminsId() {
        return adminsId;
    }

    public void setAdminsId(Integer[] adminsId) {
        this.adminsId = adminsId;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Integer[] getMatchesId() {
        return matchesId;
    }

    public void setMatchesId(Integer[] matchesId) {
        this.matchesId = matchesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getParticipantsId() {
        return participantsId;
    }

    public void setParticipantsId(Integer[] participantsId) {
        this.participantsId = participantsId;
    }

    public TournamentParser(){};

}
