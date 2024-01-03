package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.ProductType;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.repository.CurrencyRepository;
import com.example.asset_management_idnes.repository.UnitRepository;
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
public class UnitService {

    final UnitRepository unitRepository;

        public Unit createUnit(String name, String description) throws RuntimeException {
        if (unitRepository.existsByUnitName(name)) {
            throw new RuntimeException("Đơn vị tính này đã tồn tại");
        }
            Unit unit = new Unit();
            unit.setUnitName(name);
            unit.setDescription(description);
        return unitRepository.save(unit);
    }

    public Unit getUnitById(Long id) {
        return unitRepository.findById(id).orElse(null);
    }

    public Optional<Unit> findById(Long id) {
        return unitRepository.findById(id);
    }

    public Page<Unit> getAll(Pageable pageable) {
        return unitRepository.findAll(pageable);
    }

    public Unit updateUnit(Long id, String unitName, String description)  throws RuntimeException{
        Unit unit = getUnitById(id);
        if (!unit.getUnitName().equals(unitName)) {
            for (Unit u : unitRepository.findAll()) {
                if (u.getUnitName().equals(unitName)) {
                    throw new RuntimeException("Tên unit đã tồn tại");
                }
            }
        }
        unit.setUnitName(unitName);
        unit.setDescription(description);
        return unitRepository.save(unit);
    }

    public void deleteUnit(Long id) throws BadRequestException {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (!unitOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy loại sản phẩm này");
        }
        unitRepository.deleteById(id);
    }
}
