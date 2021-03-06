package eu.creapix.louisss13.smartchandoid.model.jsonParsers;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    private int id;

    @SerializedName("name")
    private String lastName;

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getParsedBirthDate(){
        SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat DateToString = new SimpleDateFormat("dd MMMM yyyy", new Locale(Locale.getDefault().getLanguage()));

        String dateParsedString = "";
        try {
            Date date = StringToDate.parse(birthDate);
            dateParsedString = DateToString.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return WordUtils.capitalizeFully(dateParsedString);
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
