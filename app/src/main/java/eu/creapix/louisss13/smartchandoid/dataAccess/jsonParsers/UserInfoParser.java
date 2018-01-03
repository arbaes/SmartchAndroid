package eu.creapix.louisss13.smartchandoid.dataAccess.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by arnau on 03-01-18.
 */

public class UserInfoParser extends JSONObject {

    @SerializedName("adresse")
    private AddressParser address;

    @SerializedName("birthday")
    private String birthDate;
    private String email;
    private String firstName;

    @SerializedName("name")
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String phone;

    public UserInfoParser() {
    }

    public AddressParser getAddress() {
        return address;
    }

    public void setAddress(AddressParser addresse) {
        this.address = addresse;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthday) {
        this.birthDate = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
