package org.lucentininicolas.services;

import org.lucentininicolas.dtos.TandaDto;
import org.lucentininicolas.entities.Tanda;
import org.lucentininicolas.repositories.TandaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class TandaService {

    @Autowired
    private TandaRepository tandaRepository;
    @Autowired
    private WeatherApiService weatherApiService;
    @Autowired
    private ModelMapper modelMapper;

    public TandaDto create(TandaDto tandaDto) {
        LocalDate currentDate = LocalDate.now();
        WeatherResponseDto responseDto = WeatherResponseDto.builder().build();
        try
        {
            responseDto = weatherApiService.GetData();
        }
        catch (Exception e){}

        tandaDto.setCreationDate(currentDate);
        tandaDto.setTemperature(responseDto.temp_c);
        tandaDto.setHumidity(responseDto.humidity);
        Tanda tanda = modelMapper.map(tandaDto, Tanda.class);
        tandaRepository.save(tanda);
        return tandaDto;
    }
    public List<TandaDto> getAll(){
        return tandaRepository.findAll().stream().map(x->modelMapper.map(x, TandaDto.class))
                .collect(Collectors.toList());
    }
}
