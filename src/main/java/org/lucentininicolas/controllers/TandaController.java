package org.lucentininicolas.controllers;

import org.lucentininicolas.dtos.TandaDto;
import org.lucentininicolas.services.TandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tanda")
public class TandaController {

    @Autowired
    private TandaService tandaService;
    @PostMapping
    public TandaDto create(@RequestBody TandaDto tandaDto){
        return tandaService.create(tandaDto);
    }
    @PutMapping
    public TandaDto update(@RequestBody TandaDto tandaDto){
        return tandaService.update(tandaDto);
    }
    @GetMapping
    public List<TandaDto> getAll(){
        return tandaService.getAll();
    }
    @GetMapping("/between")
    public List<TandaDto> getBetween( @RequestParam LocalDate startDate,
                                       @RequestParam LocalDate endDate){
        return tandaService.getBetween(startDate, endDate);
    }
    @GetMapping("/search")
    public List<TandaDto> search(
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Double temperature,
            @RequestParam(required = false) Double humidity
    ) {
        return tandaService.search(startDate, endDate, temperature, humidity);
    }

}
