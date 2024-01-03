package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.Supplier;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreateCurrencyRequest;
import com.example.asset_management_idnes.model.request.CreateSupplierRequest;
import com.example.asset_management_idnes.model.request.UpdateSupplierRequest;
import com.example.asset_management_idnes.model.request.UpdateUnitRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.repository.CurrencyRepository;
import com.example.asset_management_idnes.repository.SupplierRepository;
import com.example.asset_management_idnes.service.CurrencyService;
import com.example.asset_management_idnes.service.SupplierService;
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
@RequestMapping("/api/v1/supplier")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class SupplierResource {

    SupplierService supplierService;

    SupplierRepository supplierRepository;

    // Tạo danh sách
//    @PostMapping("/create-supplier")
//    public ResponseEntity<?> createSupplier(@RequestBody CreateSupplierRequest createSupplierRequest) {
//        try {
//            Supplier supplier = supplierService.createSupplier(createSupplierRequest.getSupplierCode(), createSupplierRequest.getSupplierName());
//            return ResponseEntity.ok(supplier);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//    }


    // Lấy danh sách
    @GetMapping("/get-all-supplier")
    public List<Supplier> getAllSupplier() {
        return supplierRepository.findAll();
    }

    // Lấy danh sách có phân trang
    @GetMapping("/get-all-supplier-pageable")
    public ResponseEntity<Page<Supplier>> getAllSupplierPageable(Pageable pageable) {
        Page<Supplier> getAllSupplierPageable = supplierService.getAll(pageable);
        return new ResponseEntity<>(getAllSupplierPageable, HttpStatus.OK);
    }

    // Lấy danh sách theo id
    @GetMapping("/get-supplier/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    // Cập nhật danh sách
//    @PutMapping("/update-supplier/{id}")
//    public ResponseEntity<?> updateSupplier(@PathVariable("id") Long id, @RequestBody UpdateSupplierRequest updateSupplierRequest) {
//        try {
//            Supplier supplier = supplierService.updateSupplier(id, updateSupplierRequest.getSupplierCode(), updateSupplierRequest.getSupplierName());
//            return ResponseEntity.ok(supplier);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
//        }
//    }

    // Xóa danh sách
    @DeleteMapping("delete-supplier/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable("id") Long id) throws BadRequestException {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
