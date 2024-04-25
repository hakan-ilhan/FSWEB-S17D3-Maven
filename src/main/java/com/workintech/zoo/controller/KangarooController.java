package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Integer, Kangaroo> kangaroos;
    @PostConstruct
    public void init(){
        this.kangaroos = new HashMap<>();
    }
    @GetMapping("/")
    public List<Kangaroo> allKangaroos(){
        return kangaroos.values().stream().toList();
    }
    @GetMapping("/{id}")
    public Kangaroo getKangaroo(@PathVariable int id){
        if(id <= 0){
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        } if (!kangaroos.containsKey(id)){
            throw new ZooException("Id can't found: " + id, HttpStatus.NOT_FOUND);
        }

        return kangaroos.get(id);
    }

    @PostMapping("/")
    public Kangaroo addKangaroo(@RequestBody Kangaroo kangaroo) {
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }
    @PutMapping("/{id}")
    public Kangaroo updateKang(@PathVariable int id,@RequestBody Kangaroo kangaroo){
        if(id <= 0){
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        } if (!kangaroos.containsKey(id)){
            throw new ZooException("Id can't found: " + id, HttpStatus.NOT_FOUND);
        }
        kangaroos.replace(id,kangaroo);
        return kangaroo;
    }
    @DeleteMapping("/{id}")
    public Kangaroo deleteKang(@PathVariable int id){
        if(id <= 0){
            throw new ZooException("Id must be greater than 0", HttpStatus.BAD_REQUEST);
        } if (!kangaroos.containsKey(id)){
            throw new ZooException("Id can't found: " + id, HttpStatus.NOT_FOUND);
        }
        Kangaroo kangaroo = kangaroos.get(id);
        kangaroos.remove(id,kangaroo);
        return kangaroo;
    }
}

