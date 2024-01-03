package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.*;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.model.request.CreatePlanListDetailRequest;
import com.example.asset_management_idnes.model.request.CreateProductTypeDetailRequest;
import com.example.asset_management_idnes.model.request.UpdateProductTypeDetailRequest;
import com.example.asset_management_idnes.model.response.ListPlanListDetailResponse;
import com.example.asset_management_idnes.model.response.ListProductTypeDetailResponse;
import com.example.asset_management_idnes.repository.PlanListDetailRepository;
import com.example.asset_management_idnes.repository.ProductTypeDetailRepository;
import com.example.asset_management_idnes.repository.ProductTypeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductTypeDetailService {

    final ProductTypeService productTypeService;

    final ProductTypeDetailRepository productTypeDetailRepository;

    final ProductTypeRepository productTypeRepository;

    public void createProductTypeDetail(CreateProductTypeDetailRequest createProductTypeDetailRequest) throws BadRequestException {

                Set<ProductType> productTypeSet = new HashSet<>();
        createProductTypeDetailRequest.getProductTypeId().forEach(id -> productTypeSet.add(productTypeService.findById(id).get()));

        ProductTypeDetail productTypeDetail = ProductTypeDetail.builder()
                .typeDetail(createProductTypeDetailRequest.getTypeDetail())
                .description(createProductTypeDetailRequest.getDescription())
                .productTypes(productTypeSet)
                .build();
        productTypeDetailRepository.save(productTypeDetail);
    }

    public Optional<ProductTypeDetail> findById(Long id) {
        return productTypeDetailRepository.findById(id);
    }

    public Page<ProductTypeDetail> getAllProductTypeDetailPageable(Pageable pageable) {
        return productTypeDetailRepository.findAll(pageable);

    }

    public ProductTypeDetail getProductTypeDetailById(Long id) {
        return productTypeDetailRepository.findById(id).orElse(null);
    }

    public void updateProductTypeDetail(Long id, UpdateProductTypeDetailRequest updateProductTypeDetailRequest) {
        ProductTypeDetail productTypeDetail = productTypeDetailRepository.findById(id).orElse(null);


        if (productTypeDetail != null) {
            productTypeDetail.setTypeDetail(updateProductTypeDetailRequest.getTypeDetail());
            productTypeDetail.setDescription(updateProductTypeDetailRequest.getDescription());
            productTypeDetailRepository.save(productTypeDetail);
        }
    }

    public void deleteProductTypeDetail(Long id) {
        Optional<ProductTypeDetail> productTypeDetailOptional = productTypeDetailRepository.findById(id);
        if (!productTypeDetailOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy loại này");
        }
        productTypeDetailRepository.deleteById(id);
    }

    public List<ListProductTypeDetailResponse> findByProductType(Long idProductType) {
        List<ProductTypeDetail> productTypeDetails = productTypeDetailRepository.findByProductType(idProductType);
        List<ListProductTypeDetailResponse> productTypeDetailResponses = new ArrayList<>();
        for (ProductTypeDetail pl: productTypeDetails) {
            ListProductTypeDetailResponse productTypeDetailResponse = ListProductTypeDetailResponse.builder()
                    .id(pl.getId())
                    .typeDetail(pl.getTypeDetail())
                    .productTypeId(pl.getProductTypes())
                    .build();
            productTypeDetailResponses.add(productTypeDetailResponse);
        }
        return  productTypeDetailResponses;
    }
}
