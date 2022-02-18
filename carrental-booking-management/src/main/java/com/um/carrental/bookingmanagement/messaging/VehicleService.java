package com.um.carrental.bookingmanagement.messaging;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VehicleService {
    @GET("vehicles/{numberPlate}")
    public Call<VehicleAPIResponse> getVehicle(@Query("numberPlate") String numberPlate);
}
