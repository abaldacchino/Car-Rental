package com.um.carrental.bookingmanagement.messaging;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomerService {
    @GET("customer/{customerID}")
    public Call<VehicleAPIResponse> getVehicle(@Query("customerID") String customerID);
}
