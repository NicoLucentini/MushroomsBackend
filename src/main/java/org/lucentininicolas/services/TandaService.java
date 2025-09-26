package org.lucentininicolas.services;

import org.lucentininicolas.dtos.TandaDto;
import org.lucentininicolas.entities.Tanda;
import org.lucentininicolas.entities.TaskLog;
import org.lucentininicolas.exceptions.DuplicateIdException;
import org.lucentininicolas.exceptions.NotExistentIdException;
import org.lucentininicolas.repositories.TandaRepository;
import org.lucentininicolas.repositories.TaskLogRepository;
import org.lucentininicolas.repositories.specifications.TandaSpecifications;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TandaService {

    private TandaRepository tandaRepository;
    private TaskLogRepository taskLogRepository;
    private WeatherApiService weatherApiService;
    private ModelMapper modelMapper;

    @Autowired
    public TandaService(TandaRepository tandaRepository, TaskLogRepository taskLogRepository, WeatherApiService weatherApiService, ModelMapper modelMapper) {
        this.tandaRepository = tandaRepository;
        this.taskLogRepository = taskLogRepository;
        this.weatherApiService = weatherApiService;
        this.modelMapper = modelMapper;
    }

    public TandaDto create(TandaDto tandaDto) {

        if(exists(tandaDto.getId()))
            throw new DuplicateIdException("Duplicate Id");

        LocalDate currentDate = tandaDto.getCreationDate() == null ?
                LocalDate.now() :
                tandaDto.getCreationDate();

        WeatherApiResponseDto responseDto = WeatherApiResponseDto.builder().build();
        try {
            responseDto = weatherApiService.GetData();
        } catch (Exception e) {
            throw new RuntimeException("Weather APi Exception");
        }

        tandaDto.setCreationDate(currentDate);
        tandaDto.setTemperature(responseDto.getTemp_c());
        tandaDto.setHumidity(responseDto.getHumidity());
        Tanda tanda = modelMapper.map(tandaDto, Tanda.class);
        Tanda response = tandaRepository.save(tanda);
        System.out.println("Tanda Saved with id" + tanda.getId());
        TaskLog log = TaskLog.builder().taskId(response.getId()).details(response.toString()).timestamp(LocalDateTime.now()).build();
        taskLogRepository.save(log);
        return tandaDto;
    }
    public TandaDto update(TandaDto tandaDto){
        if(!exists(tandaDto.getId()))
            throw new NotExistentIdException("Id not found");

        Tanda tanda = modelMapper.map(tandaDto, Tanda.class);
        tandaRepository.save(tanda);
        return tandaDto;
    }
    public boolean exists(Integer id){
        return tandaRepository.existsById(id);
    }

    public List<TandaDto> getAll(){
        return tandaRepository.findAll().stream().map(x->modelMapper.map(x, TandaDto.class))
                .collect(Collectors.toList());
    }
    public List<TandaDto> getBetween(LocalDate start, LocalDate end) {
        return tandaRepository.findByCreationDateBetween(start, end).stream().map(x->modelMapper.map(x, TandaDto.class))
                .collect(Collectors.toList());
    }
    public List<TandaDto> search(LocalDate start, LocalDate end, Double temp, Double hum) {
        System.out.println("Start: " + start.toString() + ", end " + end.toString() + ", temp " + temp + ", hum " + hum);

        List<TandaDto> data = getAll();

        data.forEach(x-> System.out.println(x.toString()));

        Specification<Tanda> spec = Specification.unrestricted();


        if (start != null && end != null) {
            spec = spec.and(TandaSpecifications.creationDateBetween(start, end));
        }


        if (temp != null ) {
            spec = spec.and(TandaSpecifications.temperatureGreaterThan(temp));
        }
        if (hum != null) {
            spec = spec.and(TandaSpecifications.humidityLessThan(hum));
        }

        return tandaRepository.findAll(spec).stream().map(x->modelMapper.map(x, TandaDto.class))
                .collect(Collectors.toList());
    }
}
