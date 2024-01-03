package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.ProductType;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.repository.CurrencyRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyService {

    final CurrencyRepository currencyRepository;

        public Currency createCurrency(String name, String description) throws RuntimeException {
        if (currencyRepository.existsByCurrencyUnit(name)) {
            throw new RuntimeException("Đơn vị tiền tệ này đã tồn tại");
        }
        Currency currency = new Currency();
        currency.setCurrencyUnit(name);
        currency.setDescription(description);
        return currencyRepository.save(currency);
    }

    public Currency getCurrencyById(Long id) {
        return currencyRepository.findById(id).orElse(null);
    }

    public Page<Currency> getAll(Pageable pageable) {
        return currencyRepository.findAll(pageable);
    }

    public Optional<Currency> findById(Long id) {
        return currencyRepository.findById(id);
    }

    public Currency updateCurrency(Long id, String currencyUnit, String description) {
        Currency currency = getCurrencyById(id);
        if (!currency.getCurrencyUnit().equals(currencyUnit)) {
            for (Currency u : currencyRepository.findAll()) {
                if (u.getCurrencyUnit().equals(currencyUnit)) {
                    throw new RuntimeException("Tên currency đã tồn tại");
                }
            }
        }

        currency.setCurrencyUnit(currencyUnit);
        currency.setDescription(description);
        return currencyRepository.save(currency);
    }


    public void deleteCurrency(Long id) throws BadRequestException {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        if (!currencyOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy đơn vị tiền tệ này");
        }
        currencyRepository.deleteById(id);
    }
}
