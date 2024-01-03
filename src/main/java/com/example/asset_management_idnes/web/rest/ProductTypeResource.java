package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.ProductType;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreatePlanListRequest;
import com.example.asset_management_idnes.model.request.CreateProductTypeRequest;
import com.example.asset_management_idnes.model.request.UpdatePlanListRequest;
import com.example.asset_management_idnes.model.request.UpdateProductTypeRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.repository.PlanListRepository;
import com.example.asset_management_idnes.repository.ProductTypeRepository;
import com.example.asset_management_idnes.service.PlanListService;
import com.example.asset_management_idnes.service.ProductTypeService;
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
@RequestMapping("/api/v1/product-type")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductTypeResource {

    ProductTypeService productTypeService;

    ProductTypeRepository productTypeRepository;


    // Tạo danh sách
    @PostMapping("/create-product-type")
    public ResponseEntity<?> createProductType(@RequestBody CreateProductTypeRequest createProductTypeRequest) {
        try {
            ProductType productType = productTypeService.createProductType(createProductTypeRequest.getType());
            return ResponseEntity.ok(productType);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Lấy danh sách
    @GetMapping("/get-all-product-type")
    public List<ProductType> getAllProductType() {
        return productTypeRepository.findAll();
    }


    // Lấy danh sách có phân trang
    @GetMapping("/get-product-type-pageable")
    public ResponseEntity<Page<ProductType>> getAllProductTypePageable(Pageable pageable) {
        Page<ProductType> getAllProductType = productTypeService.getAll(pageable);
        return new ResponseEntity<>(getAllProductType, HttpStatus.OK);
    }

    // Xóa danh sách
    @DeleteMapping("delete-product-type/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable("id") Long id) throws BadRequestException {
        productTypeService.deleteProductType(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // Lấy danh sách theo id
    @GetMapping("/get-product-type/{id}")
    public ResponseEntity<?> getProductTypeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(productTypeService.getProductTypeById(id));
    }

    // Cập nhật danh sách
    @PutMapping("/update-product-type/{id}")
    public ResponseEntity<?> updateProductType(@PathVariable("id") Integer id, @RequestBody UpdateProductTypeRequest updateProductTypeRequest) {
        try {
            ProductType productType = productTypeService.updateProductType(id, updateProductTypeRequest.getType());
            return ResponseEntity.ok(productType);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
