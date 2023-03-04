package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.entity.Measurement;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.service.MeasurementService;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    @GetMapping
    public List<Measurement> getMeasurements(){
        return measurementService.getMeasurements();
    }

    @GetMapping("/{id}")
    public Measurement getMeasurement(@PathVariable Integer id){
        return measurementService.getMeasurement(id);
    }

    @PostMapping
    public Result addMeasurement(@RequestBody Measurement measurement){
        return measurementService.addMeasurement(measurement);
    }

    @PutMapping("/{id}")
    public Result editMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement){
        return measurementService.editMeasurement(id, measurement);
    }

    @DeleteMapping("/{id}")
    public Result deleteMeasurement(@PathVariable Integer id){
        return measurementService.deleteMeasurement(id);
    }

}
