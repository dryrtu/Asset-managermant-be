package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.ProductTypeDetail;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreatePlanListDetailRequest;
import com.example.asset_management_idnes.model.request.CreateProductTypeDetailRequest;
import com.example.asset_management_idnes.model.request.UpdateProductTypeDetailRequest;
import com.example.asset_management_idnes.model.request.UpdateUnitRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.model.response.ListPlanListDetailResponse;
import com.example.asset_management_idnes.model.response.ListProductTypeDetailResponse;
import com.example.asset_management_idnes.repository.ProductTypeDetailRepository;
import com.example.asset_management_idnes.service.PlanListDetailService;
import com.example.asset_management_idnes.service.ProductTypeDetailService;
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
@RequestMapping("/api/v1/product-type-detail")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ProductTypeDetailResource {

    ProductTypeDetailService productTypeDetailService;

    ProductTypeDetailRepository productTypeDetailRepository;

        // Tạo mới danh sách
    @PostMapping("/create-product-type-detail")
    public ResponseEntity<?> createProductTypeDetail(@RequestBody CreateProductTypeDetailRequest createProductTypeDetailRequest) throws BadRequestException {
        productTypeDetailService.createProductTypeDetail(createProductTypeDetailRequest);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }


    // Lấy danh sách có phân trang
    @GetMapping("/get-all-product-type-detail-pageable")
    public ResponseEntity<Page<ProductTypeDetail>> getAllProductTypeDetailPageable(Pageable pageable) {
        Page<ProductTypeDetail> productTypeDetailPage = productTypeDetailService.getAllProductTypeDetailPageable(pageable);
        return new ResponseEntity<>(productTypeDetailPage, HttpStatus.OK);
    }


    // Lấy danh sách ko phân trang
    @GetMapping("/get-all-product-type-detail")
    public List<ProductTypeDetail> getAllProductTypeDetail() {
        return productTypeDetailRepository.findAll();
    }


    // Lấy danh sách theo id
    @GetMapping("/get-product-type-detail/{id}")
    public ResponseEntity<?> getProductTypeDetailById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productTypeDetailService.getProductTypeDetailById(id));
    }


    // Cập nhật danh sách
    @PutMapping("/update-product-type-detail/{id}")
    public ResponseEntity<?> updateProductTypeDetail(@PathVariable("id") Long id, @RequestBody UpdateProductTypeDetailRequest updateProductTypeDetailRequest) {
            productTypeDetailService.updateProductTypeDetail(id, updateProductTypeDetailRequest);
            return ResponseEntity.ok(null);
    }


    // Xóa danh sách
    @DeleteMapping("delete-product-type-detail/{id}")
    public ResponseEntity<?> deleteProductTypeDetail(@PathVariable("id") Long id) throws BadRequestException {
        productTypeDetailService.deleteProductTypeDetail(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


    // Lấy danh sách by ProductType
    @GetMapping("/get-product-type-detail-by-type")
    public ResponseEntity<?> getProductTypeDetailByType(@RequestParam Long idProductType){
        List<ListProductTypeDetailResponse> result = productTypeDetailService.findByProductType(idProductType);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
