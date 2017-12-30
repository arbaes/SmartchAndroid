package eu.creapix.louisss13.smartchandoid.model;

import java.util.ArrayList;

/**
 * Created by arnau on 29-12-17.
 */

public class Club {

    private int clubId;
    private String name;
    private String contactMail;
    private String phone;
    private ArrayList<Player> members;
    private ArrayList<UserAccount> admins;
    private Address address;

    public Club() {
    }

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Player> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Player> members) {
        this.members = members;
    }

    public ArrayList<UserAccount> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<UserAccount> admins) {
        this.admins = admins;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
