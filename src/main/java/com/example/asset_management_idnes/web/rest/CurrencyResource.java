package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreateCurrencyRequest;
import com.example.asset_management_idnes.model.request.UpdateCurrencyRequest;
import com.example.asset_management_idnes.model.request.UpdateUnitRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.repository.CurrencyRepository;
import com.example.asset_management_idnes.service.CurrencyService;
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
@RequestMapping("/api/v1/currency")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class CurrencyResource {

    CurrencyService currencyService;

    CurrencyRepository currencyRepository;

        // Tạo danh sách
    @PostMapping("/create-currency")
    public ResponseEntity<?> createCurrency(@RequestBody CreateCurrencyRequest createCurrencyRequest) {
        try {
            Currency currency = currencyService.createCurrency(createCurrencyRequest.getCurrencyUnit(), createCurrencyRequest.getDescription());
            return ResponseEntity.ok(currency);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


        // Lấy danh sách
    @GetMapping("/get-all-currency")
    public List<Currency> getAllCurrency() {
        return currencyRepository.findAll();
    }


    // Lấy danh sách có phân trang
    @GetMapping("/get-all-currency-pageable")
    public ResponseEntity<Page<Currency>> getAllCurrencyPageable(Pageable pageable) {
        Page<Currency> getAllCurrency = currencyService.getAll(pageable);
        return new ResponseEntity<>(getAllCurrency, HttpStatus.OK);
    }


    // Lấy danh sách theo id
    @GetMapping("/get-currency/{id}")
    public ResponseEntity<?> getCurrencyById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(currencyService.getCurrencyById(id));
    }


    // Cập nhật danh sách
    @PutMapping("/update-currency/{id}")
    public ResponseEntity<?> updateCurrency(@PathVariable("id") Long id, @RequestBody UpdateCurrencyRequest updateCurrencyRequest) {
        try {
            Currency currency = currencyService.updateCurrency(id, updateCurrencyRequest.getCurrencyUnit(), updateCurrencyRequest.getDescription());
            return ResponseEntity.ok(currency);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    // Xóa danh sách
    @DeleteMapping("delete-currency/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable("id") Long id) throws BadRequestException {
        currencyService.deleteCurrency(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
