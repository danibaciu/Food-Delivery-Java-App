package pao.components;

import java.io.*;


public class Address extends Entity<Long> {

    private String country, city, street;

    private Integer streetNumber;

    public Address(String country, String city, String street, Integer streetNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
    }

    public Address() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                '}';
    }

    @Override
    public String convertEntityToCsvString() {
        return country + "," + city + "," + street + "," + streetNumber;
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {
        String []tempArr = CsvString.split(CsvDelimiter);
        try {
            this.country = tempArr[0];
            this.city = tempArr[1];
            this.street = tempArr[2];
            this.streetNumber = Integer.valueOf(tempArr[3]);
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea adreselor din CSV : " + exception.toString());
        }
    }
}
