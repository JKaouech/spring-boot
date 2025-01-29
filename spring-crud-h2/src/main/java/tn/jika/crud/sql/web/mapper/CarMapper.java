package tn.jika.crud.sql.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import tn.jika.crud.sql.model.Car;
import tn.jika.crud.sql.web.model.CarDto;

@Mapper(componentModel = "spring")
public interface CarMapper {

	CarDto carToDto (Car car);
	List<CarDto> carToDto (List<Car> cars);
	
	Car dtoToCar (CarDto car);
	List<Car> dtoToCar (List<CarDto> cars);
}
