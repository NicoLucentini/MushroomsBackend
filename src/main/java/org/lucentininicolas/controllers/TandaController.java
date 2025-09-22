package org.lucentininicolas.controllers;

import org.lucentininicolas.dtos.TandaDto;
import org.lucentininicolas.services.TandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public List<TandaDto> getAll(){
        return tandaService.getAll();
    }

}
