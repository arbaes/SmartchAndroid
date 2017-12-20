package eu.creapix.louisss13.smartchandoid.Model;

import java.util.Date;

/**
 * Created by Louisss13 on 19-12-17.
 */

public class UserAccount {
    private String email;
    private String password;
    private String userName;
    private Date birthday ;
    private Date dateInscription ;
    private Date dateDerniereConnection ;
    private int active ;

    public UserAccount(String email,String password, String userName, Date birthday, Date dateInscription, Date dateDerniereConnection, int active) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.birthday = birthday;
        this.dateInscription = dateInscription;
        this.dateDerniereConnection = dateDerniereConnection;
        this.active = active;
    }
}
