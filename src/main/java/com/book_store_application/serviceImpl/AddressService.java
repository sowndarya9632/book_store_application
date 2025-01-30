package com.book_store_application.serviceImpl;

import com.book_store_application.requestdto.AddressRequestDTO;
import com.book_store_application.responsedto.AddressResponseDTO;
import com.book_store_application.model.Address;
import com.book_store_application.respository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public AddressResponseDTO saveAddress(AddressRequestDTO addressRequest) {
        Address address = new Address();
        address.setStreetAddress(addressRequest.getStreetAddress());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setCountry(addressRequest.getCountry());
        address.setPincode(addressRequest.getPincode());
        address.setAddressType(addressRequest.getAddressType());
        address.setPhoneNumber(addressRequest.getPhoneNumber());

        Address savedAddress = addressRepository.save(address);
        return mapToResponseDTO(savedAddress);
    }

    public List<AddressResponseDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public AddressResponseDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        return mapToResponseDTO(address);
    }

    public AddressResponseDTO updateAddress(Long id, AddressRequestDTO addressRequest) {
        Address existingAddress = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        existingAddress.setStreetAddress(addressRequest.getStreetAddress());
        existingAddress.setCity(addressRequest.getCity());
        existingAddress.setState(addressRequest.getState());
        existingAddress.setCountry(addressRequest.getCountry());
        existingAddress.setPincode(addressRequest.getPincode());
        existingAddress.setAddressType(addressRequest.getAddressType());

        Address updatedAddress = addressRepository.save(existingAddress);
        return mapToResponseDTO(updatedAddress);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    private AddressResponseDTO mapToResponseDTO(Address address) {
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setAddressId(address.getAddressId());
        responseDTO.setStreetAddress(address.getStreetAddress());
        responseDTO.setCity(address.getCity());
        responseDTO.setState(address.getState());
        responseDTO.setCountry(address.getCountry());
        responseDTO.setPincode(address.getPincode());
        responseDTO.setAddressType(address.getAddressType());
        responseDTO.setPhoneNumber(address.getPhoneNumber());
        return responseDTO;
    }
}
