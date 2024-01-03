package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.domain.ShoppingPlan;
import com.example.asset_management_idnes.domain.ShoppingPlanUpdatedHistory;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreateAssetRequest;
import com.example.asset_management_idnes.model.request.CreateShoppingPlanRequest;
import com.example.asset_management_idnes.model.request.UpdateShoppingPlanRequest;
import com.example.asset_management_idnes.model.response.ListPlanListDetailResponse;
import com.example.asset_management_idnes.model.response.ListShoppingPlanResponse;
import com.example.asset_management_idnes.model.response.ListVersionUpdateByShoppingPlanResponse;
import com.example.asset_management_idnes.repository.ShoppingPlanRepository;
import com.example.asset_management_idnes.service.ShoppingPlanService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/shopping-plan")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShoppingPlanResource {

//    AssetService assetService;

    ShoppingPlanService shoppingPlanService;

    ShoppingPlanRepository shoppingPlanRepository;


    // Lấy toàn bộ sản phẩm kế hoạch mua sắm
    @GetMapping("/get-all-shopping-plan")
    public ResponseEntity<Page<ShoppingPlan>> getAllShoppingPlan(Pageable pageable) {
        Page<ShoppingPlan> getAllShoppingPlanResponse = shoppingPlanService.getAll(pageable);
        return new ResponseEntity<>(getAllShoppingPlanResponse, HttpStatus.OK);
    }

    @GetMapping("/get-all-shopping-plan-not-pageable")
    public List<ShoppingPlan> getAllShoppingPlanNotPageable() {
        return shoppingPlanRepository.findAll();
    }


    // Tìm kiếm kế hoạch và hiển thị có phân trang
    @GetMapping("/search")
    public ResponseEntity<Page<ShoppingPlan>> searchAllShoppingPlan(@RequestParam(required = false) String productName,
                                                                    @RequestParam(required = false) Long planListId,
                                                                    @RequestParam(required = false) Long planListDetailId,
                                                                    @RequestParam(required = false) Long unitId,
                                                                    @RequestParam(required = false) Long currencyId,
                                                                    @RequestParam(required = false) Long productTypeId,
                                                                    @RequestParam(required = false) Long productTypeDetailId,
                                                                    Pageable pageable) {
        Page<ShoppingPlan> shoppingPlans = shoppingPlanService.searchAllShoppingPlanByParam(productName, planListId, planListDetailId, unitId, currencyId, productTypeId, productTypeDetailId, pageable);
        return new ResponseEntity<>(shoppingPlans, HttpStatus.OK);
    }

    // Tìm kiếm kế hoạch và hiển thị có phân trang
    @GetMapping("/search-by-product-name")
    public ResponseEntity<Page<ShoppingPlan>> searchByProductName(@RequestParam(required = false) String productName,
                                                                    Pageable pageable) {
        Page<ShoppingPlan> shoppingPlans = shoppingPlanService.searchByProductName(productName, pageable);
        return new ResponseEntity<>(shoppingPlans, HttpStatus.OK);
    }

    // Tạo mới sản phẩm (bản nháp)
    @PostMapping("/create-shopping-plan")
    public ResponseEntity<?> createShoppingPlan(@RequestBody CreateShoppingPlanRequest createShoppingPlanRequest) throws BadRequestException {
        shoppingPlanService.createShoppingPlan(createShoppingPlanRequest);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // Lấy sản phẩm theo id
    @GetMapping("get-shopping-plan/{id}")
    public ResponseEntity<?> getShoppingPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(shoppingPlanService.findById(id));
    }

    // Cập nhật sản phẩm
    @PutMapping("/update-shopping-plan/{id}")
    public ResponseEntity<?> updateShoppingPlan(@PathVariable("id") Long id, @RequestBody @Valid UpdateShoppingPlanRequest updateShoppingPlanRequest) {
        shoppingPlanService.updateShoppingPlanRequest(id, updateShoppingPlanRequest);
        return ResponseEntity.ok(null);
    }

    // Thay đổi trạng thái sản phẩm từ CREATED --> PENDING
    @PostMapping("/change-product-status-to-pending")
    public void changeProductStatusToPending(@RequestParam(value = "id") Long id, @RequestParam("status") String status) {
        shoppingPlanService.changeProductStatus(id, status);
    }

    // ADMIN thay đổi trạng thái từ PENDING --> APPROVED
    @PostMapping("/change-product-status-to-approved")
    public void changeProductStatusToApproved(@RequestParam(value = "id") Long id, @RequestParam("status") String status) {
        shoppingPlanService.changeProductStatusToApproved(id, status);
    }

    // Thay đổi trạng thái từ APPROVED --> DONE và chuyển vào Tài sản
    @PostMapping("/change-product-status-to-done")
    public void changeProductStatusToDone(@RequestParam(value = "id") Long id, @RequestParam("status") String status, @RequestBody CreateAssetRequest createAssetRequest) {
        shoppingPlanService.changeProductStatusToDone(id, status, createAssetRequest);
    }

    // ADMIN thay đổi trạng thái sang CANCELLED
    @PostMapping("/change-product-status-to-cancelled")
    public void changeProductStatusToCancelled(@RequestParam(value = "id") Long id, @RequestParam("status") String status) {
        shoppingPlanService.changeProductStatus(id, status);
    }

    // Xóa kế hoạch
    @DeleteMapping("delete-shopping-plan/{id}")
    public ResponseEntity<?> deleteShoppingPlan(@PathVariable("id") Long id) {
        shoppingPlanService.deleteShoppingPlan(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy danh sách sản phẩm từ plan list detail
    @GetMapping("/get-shopping-plan-by-plan-list-detail")
    public ResponseEntity<?> getShoppingPlanByPlanListDetail(@RequestParam Long idPlanListDetail, Pageable pageable){
        Page<ListShoppingPlanResponse> result = shoppingPlanService.findByPlanListDetail(idPlanListDetail, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Lấy danh sách sản phẩm từ plan list detail và chuyển isDraft thành false
    @PutMapping("/update-draft-status")
    public void updateDraftStatus(@RequestParam Long idPlanListDetail){
        shoppingPlanService.changeIsDraft(idPlanListDetail);
    }


    // Lấy danh sách sản phẩm từ plan list detail ko có phân trang
    @GetMapping("/get-shopping-plan-by-plan-list-detail-not-pagination")
    public List<ShoppingPlan> getShoppingPlanByPlanListDetailNotPageable(@RequestParam Long idPlanListDetail) {
        return shoppingPlanRepository.findByPlanListDetailsNotPagination(idPlanListDetail);
    }

    // Lấy lịch sử cập nhật của sản phẩm theo version
    @GetMapping("get-shopping-plan-updated-history/id/{shoppingPlanId}/version/{version}")
    public ResponseEntity<?> getShoppingPlanByVersion(@PathVariable("shoppingPlanId") Long id, @PathVariable("version") String version) {
        return  ResponseEntity.ok(shoppingPlanService.getShoppingPlanVersionByIdAndVersion(id, version));
    }

    // Lấy danh sách version
    @GetMapping("get-versions-by-shopping-plan-id/{id}")
    public ResponseEntity<List<String>> getShoppingPlanVersions(@PathVariable Long id) {
        List<String> versions = shoppingPlanService.getVersionsByShoppingPlanId(id);

        if (!versions.isEmpty()) {
            return new ResponseEntity<>(versions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
