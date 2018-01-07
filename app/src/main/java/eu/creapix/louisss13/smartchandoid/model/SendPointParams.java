package eu.creapix.louisss13.smartchandoid.model;

import eu.creapix.louisss13.smartchandoid.dataAccess.enums.RequestMethods;

/**
 * Created by arnau on 07-01-18.
 */

public class SendPointParams {
    int matchId;
    int scoredBy;
    RequestMethods method;

    public int getMatchId() {
        return matchId;
    }

    public int getScoredBy() {
        return scoredBy;
    }

    public RequestMethods getMethod() {
        return method;
    }

    public SendPointParams(int matchId, int scoredBy, RequestMethods method) {
        this.matchId = matchId;
        this.scoredBy = scoredBy;
        this.method = method;

    }

}