package com.um.carrental.bookingmanagement.messaging;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerService {
    @GET("customers/{customerID}")
    public Call<CustomerAPIResponse> getCustomer(@Path("customerID") String customerID);
}
