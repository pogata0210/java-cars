package com.lambaschool.javacars;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo  extends JpaRepository<Car, Long> {

    Car findCarsByYear(int year);
    Car findCarsByBrand(String brand);
    void deleteById(Long id);
}

