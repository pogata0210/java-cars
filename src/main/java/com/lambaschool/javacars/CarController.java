package com.lambaschool.javacars;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController

public class CarController
{

    private final CarRepo carrepo;
    private final RabbitTemplate rt;


    public CarController(CarRepo carrepo, RabbitTemplate rt) {
        this.carrepo = carrepo;
        this.rt = rt;

    }
    //GET
    @GetMapping("/cars/id/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carrepo.findById(id).orElseThrow(() -> new CarNotFoundException(id));
    }

    @GetMapping("/cars/year/{year}")
    public List<Car> getCarsByYear(@PathVariable int year) {
        return (List<Car>) carrepo.findCarsByYear(year);

    }


    @GetMapping("/cars/brand/{brand}")

    public List<Car> getCarsByYear(@PathVariable String brand)

    {
        List<Car> brandList = carrepo.findAll();
        List<Car> cars = new ArrayList<>();
        for(Car c : brandList){
            if (c.getBrand().toLowerCase().equals(brand.toLowerCase())){
                cars.add(c);



                /**
                 *  List<GdpData> data = repo.findAll();
                 *         //List<Car> foundCars == new List<>();
                 *         for (GdpData g: data) {
                 *             if (g.getCountry().toLowerCase().replaceAll("\\s", "").equals(country)){
                 *                 return g; // foundCars.add(g); (no return)
                 *             }
                 *         }return null; //return foundCars;
                 */
    }
        } return null;
    }
    //POST
    @PostMapping("/cars/upload")
    public List<Car> loadCars(@RequestBody List<Car> carsToUpload) {
        CarLog message = new CarLog("Data Loaded");
        rt.convertAndSend(JavaCarsApplication.QUEUE_NAME, message.toString());
        return carrepo.saveAll(carsToUpload);
    }

//DELETE
    @DeleteMapping("/cars/delete/{id}")
    public Car deleteById(@PathVariable Long id)
    {

        Car car = carrepo.findById(id).orElseThrow();
        carrepo.delete(car);
        return car;

    }
}
