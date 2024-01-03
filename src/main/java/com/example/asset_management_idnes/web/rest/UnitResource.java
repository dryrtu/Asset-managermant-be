package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.ProductType;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreateCurrencyRequest;
import com.example.asset_management_idnes.model.request.CreateUnitRequest;
import com.example.asset_management_idnes.model.request.UpdateProductTypeRequest;
import com.example.asset_management_idnes.model.request.UpdateUnitRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.repository.CurrencyRepository;
import com.example.asset_management_idnes.repository.UnitRepository;
import com.example.asset_management_idnes.service.CurrencyService;
import com.example.asset_management_idnes.service.UnitService;
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
@RequestMapping("/api/v1/unit")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class UnitResource {

    UnitService unitService;

    UnitRepository unitRepository;

        // Tạo danh sách
    @PostMapping("/create-unit")
    public ResponseEntity<?> createUnit(@RequestBody CreateUnitRequest createUnitRequest) {
        try {
            Unit unit = unitService.createUnit(createUnitRequest.getUnitName(), createUnitRequest.getDescription());
            return ResponseEntity.ok(unit);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

        // Lấy danh sách có phân trang
    @GetMapping("/get-all-unit-pageable")
    public ResponseEntity<Page<Unit>> getAllUnitPageable(Pageable pageable) {
        Page<Unit> getAllUnit = unitService.getAll(pageable);
        return new ResponseEntity<>(getAllUnit, HttpStatus.OK);
    }


    // Lấy danh sách ko phân trang
    @GetMapping("/get-all-unit")
    public List<Unit> getAllUnit() {
        return unitRepository.findAll();
    }



    // Lấy danh sách theo id
    @GetMapping("/get-unit/{id}")
    public ResponseEntity<?> getUnitById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    // Cập nhật danh sách
    @PutMapping("/update-unit/{id}")
    public ResponseEntity<?> updateUnit(@PathVariable("id") Long id, @RequestBody UpdateUnitRequest updateUnitRequest) {
        try {
            Unit unit = unitService.updateUnit(id, updateUnitRequest.getUnitName(), updateUnitRequest.getDescription());
            return ResponseEntity.ok(unit);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa danh sách
    @DeleteMapping("delete-unit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable("id") Long id) throws BadRequestException {
        unitService.deleteUnit(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
