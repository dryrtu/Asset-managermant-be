package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Manufacturer;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreateManufacturerRequest;
import com.example.asset_management_idnes.model.request.UpdateManufacturerRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.repository.ManufacturerResponsitory;
import com.example.asset_management_idnes.service.ManufacturerService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/manufacturer")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ManufacturerResource {
    ManufacturerService manufacturerService;

    ManufacturerResponsitory manufacturerResponsitory;

    // Tạo danh sách
    @PostMapping("/create-manufacturer")
    public ResponseEntity<?> createManufacturer(@RequestBody CreateManufacturerRequest createManufacturerRequest) {
        try {
            Manufacturer manufacturer = manufacturerService.createManufacturer(createManufacturerRequest.getManufacturer(), createManufacturerRequest.getAddress(), createManufacturerRequest.getContacts(), createManufacturerRequest.getNote());
            return ResponseEntity.ok(manufacturer);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    // Lấy danh sách
        @GetMapping("/get-all-manufacturer")
    public List<Manufacturer> getAllManufacturer() {
        return manufacturerResponsitory.findAll();
    }


    // Lấy danh sách có phân trang
    @GetMapping("/get-all-manufacturer-pageable")
    public ResponseEntity<Page<Manufacturer>> getAllManufacturerPageable(Pageable pageable) {
        Page<Manufacturer> getAllCurrency = manufacturerService.getAll(pageable);
        return new ResponseEntity<>(getAllCurrency, HttpStatus.OK);
    }


    // Lấy danh sách theo id
    @GetMapping("/get-manufacturer/{id}")
    public ResponseEntity<?> getManufacturerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(manufacturerService.getManufacturerById(id));
    }


    // Cập nhật danh sách
    @PutMapping("/update-manufacturer/{id}")
    public ResponseEntity<?> updateManufacturer(@PathVariable("id") Long id, @RequestBody UpdateManufacturerRequest updateManufacturerRequest) {
        try {
            Manufacturer manufacturer = manufacturerService.updateManufacturer(updateManufacturerRequest.getId(), updateManufacturerRequest.getManufacturer(), updateManufacturerRequest.getAddress(), updateManufacturerRequest.getContacts(), updateManufacturerRequest.getNote());
            return ResponseEntity.ok(manufacturer);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    // Xóa danh sách
    @DeleteMapping("delete-manufacturer/{id}")
    public ResponseEntity<?> deleteManufacturer(@PathVariable("id") Long id) throws BadRequestException {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
