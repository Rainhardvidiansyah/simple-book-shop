package com.auth.jwt.service;


import com.auth.jwt.model.Address;
import com.auth.jwt.repository.AddressRepo;
import com.auth.jwt.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAddressService {

    private final AddressRepo addressRepo;
    private final UserRepo userRepo;

    private String newPhoneWithIndonesiaCode(String phoneNumber){
        String newPhoneNumber = phoneNumber.replaceAll("^08", "+62");
        return newPhoneNumber;
    }

    public Address saveAddress(Long user_id, Address address){
        var user = userRepo.findById(user_id)
                .orElseThrow(RuntimeException::new);
        address.setUser(user);
        address.setPhoneNumber(newPhoneWithIndonesiaCode(address.getPhoneNumber()));
        return addressRepo.save(address);
    }


}
