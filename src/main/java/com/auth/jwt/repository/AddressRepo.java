package com.auth.jwt.repository;

import com.auth.jwt.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface AddressRepo extends JpaRepository<Address, Long> {

}
