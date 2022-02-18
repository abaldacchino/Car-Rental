package com.um.carrental.bookingmanagement.messaging;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessagingService implements MessagingServiceInterface{
    VehicleService vehicleService = null;
    CustomerService customerService = null;

    public void instantiateVehicleService(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:9001/carrental/1.0/vehicle-management-service/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        vehicleService = retrofit.create(VehicleService.class);
    }

    @Override
    public double getVehiclePrice(String numberPlate){
        Call<VehicleAPIResponse> callSync = vehicleService.getVehicle(numberPlate);
        try{
            Response<VehicleAPIResponse> response = callSync.execute();
            VehicleAPIResponse apiResponse = response.body();
            if(apiResponse==null)return -1;
            else return apiResponse.getPrice();
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public void instantiateCustomerService(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("customer url here")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        customerService = retrofit.create(CustomerService.class);
    }

    @Override
    public boolean customerExistsById(String customerId){
        // Add call here
        return true;
    }
}
