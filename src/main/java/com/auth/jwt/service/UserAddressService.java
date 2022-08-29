package com.auth.jwt.service;


import com.auth.jwt.model.Address;
import com.auth.jwt.dto.utils.repository.AddressRepo;
import com.auth.jwt.dto.utils.repository.UserRepo;
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

    public Address saveAddress(Long user_id, String completeAddress, String phoneNumber, String postalCode ){
        var user = userRepo.findById(user_id)
                .orElseThrow(RuntimeException::new);
        Address address = new Address();
        if(address.getUser() != null){
            address.setCompleteAddress(completeAddress);
            address.setPhoneNumber(phoneNumber);
            address.setPostalCode(postalCode);
            address.setUser(user);
            user.setAddress(address);
        }
        return addressRepo.save(address);
    }


}
