package com.auth.jwt.dto.utils.repository;

import com.auth.jwt.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public interface AddressRepo extends JpaRepository<Address, Long> {

}
