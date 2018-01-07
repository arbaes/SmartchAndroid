package eu.creapix.louisss13.smartchandoid.model.jsonParsers;

/**
 * Created by arnau on 30-12-17.
 */

public class AddressParser {

    private String city;
    private String country;
    private int id;
    private String number;
    private String street;
    private String zipCode;

    public AddressParser(){

    }

    public String getShortAdress(){
        return city + ", " + country;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
