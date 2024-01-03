package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Manufacturer;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.repository.ManufacturerResponsitory;
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
public class ManufacturerService {

    final ManufacturerResponsitory manufacturerResponsitory;

    public Manufacturer createManufacturer(String name, String address, List<Long> contact, String note) throws RuntimeException {
        if (manufacturerResponsitory.existsByManufacturer(name)) {
            throw new RuntimeException("Hãng sản xuất này đã tồn tại");
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturer(name);
        manufacturer.setAddress(address);
        manufacturer.setContacts(contact);
        manufacturer.setNote(note);
        return manufacturerResponsitory.save(manufacturer);
    }

    public Manufacturer getManufacturerById(Long id) {
        return manufacturerResponsitory.findById(id).orElse(null);
    }

    public Page<Manufacturer> getAll(Pageable pageable) {
        return manufacturerResponsitory.findAll(pageable);
    }

    public Optional<Manufacturer> findById(Long id) {
        return manufacturerResponsitory.findById(id);
    }

    public Manufacturer updateManufacturer(Long id, String name, String address, List<Long> contacts, String note) {
        Manufacturer manufacturer = getManufacturerById(id);
        if (!manufacturer.getManufacturer().equals(manufacturer)) {
            for (Manufacturer u : manufacturerResponsitory.findAll()) {
                if (u.getManufacturer().equals(manufacturer)) {
                    throw new RuntimeException("Tên hãng sản xuất đã tồn tại");
                }
            }
        }

        manufacturer.setManufacturer(name);
        manufacturer.setAddress(address);
        manufacturer.setContacts(contacts);
        manufacturer.setNote(note);
        return manufacturerResponsitory.save(manufacturer);
    }

    public void deleteManufacturer(Long id) throws BadRequestException {
        Optional<Manufacturer> manufacturerOptional = manufacturerResponsitory.findById(id);
        if (!manufacturerOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy hãng sản xuất này");
        }
        manufacturerResponsitory.deleteById(id);
    }
}
