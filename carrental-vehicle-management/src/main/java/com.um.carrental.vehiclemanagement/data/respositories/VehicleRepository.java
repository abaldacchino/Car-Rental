package com.um.carrental.vehiclemanagement.data.respositories;

import com.um.carrental.vehiclemanagement.data.entities.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<VehicleEntity, String> {

}
