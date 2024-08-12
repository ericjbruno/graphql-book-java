package com.ericbruno.bankaccountdata;

import java.util.StringJoiner;

@javax.annotation.processing.Generated(
    value = "com.kobylynskyi.graphql.codegen.GraphQLCodegen",
    date = "2024-07-01T13:10:55-0400"
)
public class Customer implements java.io.Serializable, Entity {

    private static final long serialVersionUID = 1L;

    @javax.validation.constraints.NotNull
    private String id;
    private Boolean active;
    private String firstName;
    private String lastName;
    private Address address;
    private String phone;
    private String ssn;
    private String email;

    public Customer() {
    }

    public Customer(String id, Boolean active, String firstName, String lastName, Address address, String phone, String ssn, String email) {
        this.id = id;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.ssn = ssn;
        this.email = email;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSsn() {
        return ssn;
    }
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (id != null) {
            joiner.add("id: \"" + id + "\"");
        }
        if (active != null) {
            joiner.add("active: " + active);
        }
        if (firstName != null) {
            joiner.add("firstName: \"" + firstName + "\"");
        }
        if (lastName != null) {
            joiner.add("lastName: \"" + lastName + "\"");
        }
        if (address != null) {
            joiner.add("address: " + address);
        }
        if (phone != null) {
            joiner.add("phone: \"" + phone + "\"");
        }
        if (ssn != null) {
            joiner.add("ssn: \"" + ssn + "\"");
        }
        if (email != null) {
            joiner.add("email: \"" + email + "\"");
        }
        return joiner.toString();
    }

}
