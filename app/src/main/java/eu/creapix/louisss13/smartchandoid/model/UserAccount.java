package eu.creapix.louisss13.smartchandoid.model;

import java.util.Date;

/**
 * Created by Louisss13 on 19-12-17.
 */

public class UserAccount {

    private String email;
    private String password;
    private String userName;
    private Date birthday;
    private Date dateInscription;
    private Date dateDerniereConnection;
    private int active;

    public UserAccount(String email, String password, String userName, Date birthday, Date dateInscription, Date dateDerniereConnection, int active) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.birthday = birthday;
        this.dateInscription = dateInscription;
        this.dateDerniereConnection = dateDerniereConnection;
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Date getDateDerniereConnection() {
        return dateDerniereConnection;
    }

    public void setDateDerniereConnection(Date dateDerniereConnection) {
        this.dateDerniereConnection = dateDerniereConnection;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
