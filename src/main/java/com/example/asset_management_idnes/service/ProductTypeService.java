package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.ProductType;
import com.example.asset_management_idnes.domain.ProductTypeDetail;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.repository.ProductTypeDetailRepository;
import com.example.asset_management_idnes.repository.ProductTypeRepository;
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
public class ProductTypeService {

    final ProductTypeRepository productTypeRepository;

    final ProductTypeDetailRepository productTypeDetailRepository;

    public ProductType createProductType(String name) throws RuntimeException {
        if (productTypeRepository.existsByType(name)) {
            throw new RuntimeException("Loại này đã tồn tại");
        }
        ProductType productType = new ProductType();
        productType.setType(name);
        return productTypeRepository.save(productType);
    }

    public Optional<ProductType> findById(Long id) {
        return productTypeRepository.findById(id);
    }


    // Xóa plan list
    public void deleteProductType(Long id) throws BadRequestException {
        Optional<ProductType> productTypeOptional = productTypeRepository.findById(id);
        if (!productTypeOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy loại sản phẩm này");
        }
        List<ProductTypeDetail> planListDetails = productTypeDetailRepository.findAllByProductTypes(productTypeOptional.get());
        if (planListDetails.size() > 0) {
            throw new BadRequestException("Không thể xóa vì đang có phân loại cấp thấp hơn trong danh sách");   // có cần thiết ko?
        }
        productTypeRepository.deleteById(id);
    }

    public ProductType getProductTypeById(Integer id) {
        return productTypeRepository.findById(id.longValue()).orElse(null);
    }

    public ProductType updateProductType(Integer id, String name) throws RuntimeException {
        ProductType productType = getProductTypeById(id);

        if (name != null) {
            for (ProductType pt : productTypeRepository.findAll()) {
                if (pt.getType() != null && pt.getType().equals(name)) {
                    throw new RuntimeException("Tên type đã tồn tại");
                }
            }
            productType.setType(name);
        }

        return productTypeRepository.save(productType);
    }

    public Page<ProductType> getAll(Pageable pageable) {
        return productTypeRepository.findAll(pageable);
    }
}
