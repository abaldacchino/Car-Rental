package com.um.carrental.bookingmanagement.data.repositories;

import com.um.carrental.bookingmanagement.data.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {
}
