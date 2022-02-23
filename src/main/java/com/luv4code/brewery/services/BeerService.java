package com.luv4code.brewery.services;

import com.luv4code.brewery.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);
}
