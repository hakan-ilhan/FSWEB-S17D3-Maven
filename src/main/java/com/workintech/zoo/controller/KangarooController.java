package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        kangaroos = new HashMap<>();
        kangaroos.put(3, new Kangaroo(3,"Mike", 200, 100, "Male", true));
        kangaroos.put(2, new Kangaroo(2,"Emily", 188, 93, "Female", false));
    }

    @GetMapping("")
    public List<Kangaroo> allKangaroos(){
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo getKangarooById(@PathVariable int id){
        if(id <= 0) {
            throw new ZooException("Id must be greater then 0.", HttpStatus.BAD_REQUEST);
        }

        if(!kangaroos.containsKey(id)){
            throw new ZooException("Kangaroo with given id is not exist.", HttpStatus.NOT_FOUND);
        }

       return kangaroos.get(id);
    }

    @PostMapping("")
    public ResponseEntity<Kangaroo> addKangaroo(@RequestBody Kangaroo kangaroo){
        if(kangaroo.getId() <= 0) {
            throw new ZooException("Id must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        if(kangaroos.containsKey(kangaroo.getId())){
            throw new ZooException("Resource with given ID already exists. Use PUT to update.", HttpStatus.OK);
        }

        kangaroos.put(kangaroo.getId(), kangaroo);
        return new ResponseEntity<>(kangaroos.get(kangaroo.getId()),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Kangaroo> updateKangaroo(@PathVariable int id, @RequestBody Kangaroo kangaroo){
        if(id <= 0) {
            throw new ZooException("Id must be greater then 0.", HttpStatus.BAD_REQUEST);
        }

        if(!kangaroos.containsKey(id)){
            throw new ZooException("Kangaroo with given id is not exist.",HttpStatus.BAD_REQUEST);
        }

       kangaroos.put(kangaroo.getId(), kangaroo);

        return new ResponseEntity<>(kangaroo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKangaroo(@PathVariable int id){
        if(id <= 0) {
            throw new ZooException("Id must be greater then 0.", HttpStatus.BAD_REQUEST);
        }

        if(!kangaroos.containsKey(id)){
            throw new ZooException("Kangaroo with given id is not exist.",HttpStatus.NOT_FOUND);
        }

        Kangaroo deleted =   kangaroos.remove(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
