package com.luv4code.brewery.web.mappers;

import com.luv4code.brewery.domain.Customer;
import com.luv4code.brewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
