package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private Map<Integer, Koala> koalas;


    @PostConstruct
    public void init(){
        this.koalas = new HashMap<>();
        koalas.put(1, new Koala(1,"Haydar", 44D, 20, "Male"));
        koalas.put(2, new Koala(2,"Hüsniye", 35D, 14, "Female"));
    }

    @GetMapping("")
    public ResponseEntity<List<Koala>> findAllKoalas(){
        return new ResponseEntity<>(koalas.values().stream().toList(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Koala> getKoalaById(@PathVariable int id){
        if(id <= 0) {
            throw new ZooException("Id must be greater then 0.", HttpStatus.BAD_REQUEST);
        }

        if(!koalas.containsKey(id)){
            throw new ZooException("Koala with given id is not exist.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(koalas.get(id),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addKoala(@RequestBody Koala koala){
        if(koala.getId() <= 0) {
            throw new ZooException("Id must be greater than 0.", HttpStatus.BAD_REQUEST);
        }

        if(koalas.containsKey(koala.getId())){
            throw new ZooException("Resource with given ID already exists. Use PUT to update.", HttpStatus.CONFLICT);
        }

        Koala kangaroo1 = koalas.put(koala.getId(), koala);
        return new ResponseEntity<>(kangaroo1,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Koala> updateKangaroo(@PathVariable int id, @RequestBody Koala koala){
        if(id <= 0) {
            throw new ZooException("Id must be greater then 0.", HttpStatus.BAD_REQUEST);
        }

        if(!koalas.containsKey(id)){
            throw new ZooException("Koala with given id is not exist.",HttpStatus.BAD_REQUEST);
        }

        koalas.put(koala.getId(), koala);

        return new ResponseEntity<>(koalas.get(koala.getId()),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKangaroo(@PathVariable int id){
        if(id <= 0) {
            throw new ZooException("Id must be greater then 0.", HttpStatus.BAD_REQUEST);
        }

        if(!koalas.containsKey(id)){
            throw new ZooException("Kangaroo with given id is not exist.",HttpStatus.NOT_FOUND);
        }

        koalas.remove(id, koalas.get(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
