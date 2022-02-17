package com.um.carrental.bookingmanagement.helpers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

public class MyAssertions {
    public static void assertValidUUID(String id){
        try{
            UUID uuid = UUID.fromString(id);
        }catch(Exception e){
            fail(id + "is invalid");
        }
    }
}
