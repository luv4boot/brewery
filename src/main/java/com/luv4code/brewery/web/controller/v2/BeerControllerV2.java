package com.luv4code.brewery.web.controller.v2;

import com.luv4code.brewery.services.v2.BeerServiceV2;
import com.luv4code.brewery.web.model.v2.BeerDtoV2;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/beer")
@AllArgsConstructor
public class BeerControllerV2 {
    private final BeerServiceV2 beerServiceV2;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> getBeer(@PathVariable("beerId") UUID beerId) {
        return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDtoV2> handlePost(@Valid @RequestBody BeerDtoV2 beerDto) {
        BeerDtoV2 saveDto = beerServiceV2.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v2/beer/" + saveDto.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> updateBeer(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDtoV2 beerDto) {
        beerServiceV2.updateBeer(beerId, beerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId) {
        beerServiceV2.deleteBeer(beerId);
    }


}
