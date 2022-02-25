package com.luv4code.brewery.web.mappers;

import com.luv4code.brewery.domain.Beer;
import com.luv4code.brewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
