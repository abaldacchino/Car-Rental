package com.um.carrental.vehiclemanagement.services;

import com.um.carrental.vehiclemanagement.enums.RequestType;
import com.um.carrental.vehiclemanagement.exceptions.InvalidRequestException;

public class MatchingUtil {

    public boolean isDoubleMatch(double item, double criteria, RequestType requestType){
        switch(requestType){
            case EQUALS:
                return item == criteria;
            case NOTEQUALS:
                return item != criteria;
            case LESSTHAN:
                return item < criteria;
            case GREATERTHAN:
                return item > criteria;
        }
        throw new InvalidRequestException();
    }

}
