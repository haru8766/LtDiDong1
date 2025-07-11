package com.example.thaimongkieu_2123110013;

public class User {

    public int id;
    public String email;
    public String username;
    public String password;
    public Name name;
    public Address address;
    public String phone;

    public static class Name {
        public String firstname;
        public String lastname;
    }

    public static class Address {
        public String city;
        public String street;
        public int number;
        public String zipcode;
        public GeoLocation geolocation;
    }

    public static class GeoLocation {
        public String lat;

        // Từ khóa 'long' là từ khóa trong Java → dùng tên khác như '_long'
        @com.google.gson.annotations.SerializedName("long")
        public String _long;
    }

}



