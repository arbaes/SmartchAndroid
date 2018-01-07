package eu.creapix.louisss13.smartchandoid.model;

import eu.creapix.louisss13.smartchandoid.model.jsonParsers.AddressParser;

/**
 * Created by arnau on 07-01-18.
 */

public class TournamentDetails {

    private AddressParser address;
    private String beginDate;
    private String endDate;
    private int etat;
    private String name;

    public TournamentDetails(AddressParser address, String beginDate, String endDate, int etat, String name) {
        this.address = address;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.etat = etat;
        this.name = name;
    }
}
