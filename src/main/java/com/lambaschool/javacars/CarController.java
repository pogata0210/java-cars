package com.lambaschool.javacars;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/id/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carrepo.findById(id).orElseThrow(() -> new CarNotFoundException(id));
    }

    @GetMapping("/year/{year}")
    public List<Car> getCarsByYear(@PathVariable int year) {
        return (List<Car>) carrepo.findCarsByYear(year);

    }

    @GetMapping("/brand/{brand}")
    public List<Car> findBrand(@PathVariable String brand) {
        CarLog message = new CarLog(" search for " + brand);
        log.info(" search for {}" + brand);
        rt.convertAndSend(JavaCarsApplication.QUEUE_NAME, message.toString());
        return carrepo.findAll().stream().filter(c -> c.getBrand().equalsIgnoreCase(brand)).collect(Collectors.toList());
    }
    @PostMapping("/upload")
    public List<Car> loadCars(@RequestBody List<Car> carsToUpload) {
        CarLog message = new CarLog("Data Loaded");
        rt.convertAndSend(JavaCarsApplication.QUEUE_NAME, message.toString());
        return carrepo.saveAll(carsToUpload);
    }


    @DeleteMapping("/delete/{id}")
    public Car deleteById(@PathVariable Long id) {
        Car car = carrepo.findById(id).orElseThrow();
        carrepo.delete(car);
        return car;
    }
}
/**  Car findCarsByYear(int year);
 Car findAllByBrand(String brand);
 void deleteById(Long id);**/