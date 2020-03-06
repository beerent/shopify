package com.beerent.shopifyapi.model.users;

public class User {
    private Long ecommerceId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public User(Long ecommerceId, String firstName, String lastName, String email, String phoneNumber) {
        this.ecommerceId = ecommerceId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
