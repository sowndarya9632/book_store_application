package com.book_store_application.controller;

import com.book_store_application.requestdto.AddressRequestDTO;
import com.book_store_application.responsedto.AddressResponseDTO;
import com.book_store_application.serviceImpl.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody AddressRequestDTO addressRequest) {
        AddressResponseDTO savedAddress = addressService.saveAddress(addressRequest);
        return ResponseEntity.ok(savedAddress);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        List<AddressResponseDTO> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id) {
        AddressResponseDTO address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable Long id,
            @RequestBody AddressRequestDTO addressRequest) {
        AddressResponseDTO updatedAddress = addressService.updateAddress(id, addressRequest);
        return ResponseEntity.ok(updatedAddress);
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
