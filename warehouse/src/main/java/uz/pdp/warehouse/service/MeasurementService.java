package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.entity.Measurement;
import uz.pdp.warehouse.payload.Result;
import uz.pdp.warehouse.repository.MeasurementRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    public List<Measurement> getMeasurements(){
        return measurementRepository.findAll();
    }

    public Measurement getMeasurement(Integer id){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isPresent()){
            return optionalMeasurement.get();
        }
        return new Measurement();
    }

    public Result addMeasurement(Measurement measurement){
        if (!measurement.getName().isEmpty()){
            boolean exists = measurementRepository.existsByName(measurement.getName());
            if (!exists){
                Measurement measurement1 = new Measurement();
                measurement1.setName(measurement.getName());
                measurementRepository.save(measurement1);
                return new Result("O'lchov birligi qo'shildi!", true);
            }
            return new Result("Bunday nomli o'lchov birligi mavjud!", false);
        }
        return new Result("O'lchov birligi nomini kiriting!", false);
    }

    public Result editMeasurement(Integer id, Measurement measurement){
        if (!measurement.getName().isEmpty() && id!=null){
            Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
            if (optionalMeasurement.isPresent()){
                Measurement measurement1 = optionalMeasurement.get();
                if (measurement1.getName().equals(measurement.getName())){
                    measurementRepository.save(measurement1);
                    return new Result("O'lchov birligi taxrirlandi!", true);
                }else {
                    boolean exists = measurementRepository.existsByName(measurement.getName());
                    if (!exists){
                        measurement1.setName(measurement.getName());
                        measurementRepository.save(measurement1);
                        return new Result("O'lchov birligi taxrirlandi!", true);
                    }else {
                        return new Result("Bunday nomli o'lchov birligi mavjud!", false);
                    }
                }
            }
            return new Result("Kiritilgan id bo'yicha o'lchov birligi topilmadi!", false);
        }
        return new Result("Qatorlarni to'ldiring!", false);
    }

    public Result deleteMeasurement(Integer id){
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (optionalMeasurement.isPresent()){
            try {
                measurementRepository.delete(optionalMeasurement.get());
                return new Result("O'lchov birligi o'chirildi!", true);
            }catch (Exception e){
                return new Result("Bu o'lchov birligi boshqa joylarda ishlatilganligi sababli o'chirishning imkoni yo'q!", false);
            }

        }
        return new Result("Kiritilgan id bo'yicha o'lchov birligi topilmadi!", false);
    }

}
