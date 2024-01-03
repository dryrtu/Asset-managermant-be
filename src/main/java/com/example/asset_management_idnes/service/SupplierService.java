package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.domain.Supplier;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.repository.CurrencyRepository;
import com.example.asset_management_idnes.repository.SupplierRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SupplierService {

    final SupplierRepository supplierRepository;

    public Supplier createSupplier(String code, String name, String address, String contact) throws RuntimeException {
        if (supplierRepository.existsBySupplierCode(code)) {
            throw new RuntimeException("Mã công ty này đã tồn tại");
        }
        Supplier supplier = new Supplier();
        supplier.setSupplierCode(code);
        supplier.setSupplierName(name);
        supplier.setAddress(address);
        supplier.setContact(contact);
        return supplierRepository.save(supplier);
    }

    public Optional<Supplier> findSupplierById(Long ids) {
        return supplierRepository.findById(ids);
    }


    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }


    public Page<Supplier> getAll(Pageable pageable) {
        return supplierRepository.findAll(pageable);
    }

    public Supplier updateSupplier(Long id, String supplierCode, String supplierName, String address,  String contact) {
        Supplier supplier = getSupplierById(id);

        if (supplier.getSupplierCode().equals(supplierCode)) {
            for (Supplier u: supplierRepository.findBySupplierName(supplierName)) {
                if (u.getSupplierName().equals(supplierName)) {
                    throw new RuntimeException("Tên nhà cung cấp đã tồn tại");
                }
            }
        } else if (supplier.getSupplierName().equals(supplierName)) {
            for (Supplier u: supplierRepository.findBySupplierCode(supplierCode)) {
                if (u.getSupplierCode().equals(supplierCode)) {
                    throw new RuntimeException("Mã nhà cung cấp đã tồn tại");
                }
            }
        }

        supplier.setSupplierCode(supplierCode);
        supplier.setSupplierName(supplierName);
        supplier.setAddress(address);
        supplier.setContact(contact);
        return supplierRepository.save(supplier);
    }


    public void deleteSupplier(Long id) throws BadRequestException {
        Optional<Supplier> supplierOptional = supplierRepository.findById(id);
        if (!supplierOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy loại sản phẩm này");
        }
        supplierRepository.deleteById(id);
    }
}
