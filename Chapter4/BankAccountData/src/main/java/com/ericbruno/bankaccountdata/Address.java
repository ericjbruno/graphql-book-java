package com.ericbruno.bankaccountdata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-01T13:10:55-0400"
)
public class Address implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String street;
    private String city;
    private States state;
    private String zip;

    public Address() {
    }

    public Address(String street, String city, States state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public States getState() {
        return state;
    }
    public void setState(States state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (street != null) {
            joiner.add("street: \"" + street + "\"");
        }
        if (city != null) {
            joiner.add("city: \"" + city + "\"");
        }
        if (state != null) {
            joiner.add("state: " + state);
        }
        if (zip != null) {
            joiner.add("zip: \"" + zip + "\"");
        }
        return joiner.toString();
    }

}
