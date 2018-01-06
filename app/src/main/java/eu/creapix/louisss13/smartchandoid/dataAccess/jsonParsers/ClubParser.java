package eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import eu.creapix.louisss13.smartchandoid.model.Address;

/**
 * Created by arnau on 03-01-18.
 */

public class ClubParser extends JSONObject {

    private int clubId;

    @SerializedName("adresse")
    private AddressParser address;

    @SerializedName("contactMail")
    private String mail;

    private String phone;
    private String name;

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public AddressParser getAddress() {
        return address;
    }

    public void setAddress(AddressParser address) {
        this.address = address;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ClubParser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
