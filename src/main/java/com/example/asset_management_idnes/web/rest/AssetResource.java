package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.Asset;
import com.example.asset_management_idnes.model.request.CreateAssetRequest;
import com.example.asset_management_idnes.model.request.UpdateAssetRequest;
import com.example.asset_management_idnes.repository.AssetRepository;
import com.example.asset_management_idnes.service.AssetService;
import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/asset")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetResource {

    AssetService assetService;
    AssetRepository assetRepository;

    // Lấy toàn bộ danh sách sản phẩm
    @GetMapping("/get-all-asset")
    public ResponseEntity<Page<Asset>> getAllAssets(Pageable pageable) {
        try {
            Page<Asset> assetResponses = assetService.getAllAssets(pageable);
            return new ResponseEntity<>(assetResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Tạo mới sp
    @PostMapping("/create-asset")
    public ResponseEntity<?> createAsset(@RequestBody CreateAssetRequest request) {
        assetService.createAsset(request);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // Xóa sp
    @DeleteMapping("/delete-asset/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id){
        assetService.deleteAsset(id);
        return new ResponseEntity<>("Xóa asset thành công", HttpStatus.ACCEPTED);
    }

    // Lấy asset theo id
    @GetMapping("/asset/{id}")
    public ResponseEntity<?> getAsset(@PathVariable("id") Long id) {
        return ResponseEntity.ok(assetService.findById(id));
    }

    // Cập nhật asset
    @PutMapping("/update-asset/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable("id") Long id, @RequestBody @Valid UpdateAssetRequest updateAssetRequest) {
        assetService.updateAsset(id, updateAssetRequest);
        return ResponseEntity.ok(null);
    }


    // Test send email
    @GetMapping("/send-warranty-expiration-notifications")
    public void sendWarrantyExpirationNotifications() throws MessagingException {
        assetService.sendWarrantyExpirationNotifications();
    }

    // Lấy asset từ shoppingPlanId
    @GetMapping("/get-asset-id-by-shopping-plan-id/{shoppingPlanId}")
    public ResponseEntity<Long> getAssetIdByShoppingPlanId(@PathVariable Long shoppingPlanId) {
        Long assetId = assetService.getAssetIdByShoppingPlanId(shoppingPlanId);

        if (assetId != null) {
            return new ResponseEntity<>(assetId, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/change-asset-status")
    public void changeAssetStatus(@RequestParam(value = "id") Long id) {
        assetService.changeAssetStatus(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Asset>> searchAllAsset(
            @RequestParam(required = false) String itemCode,
            @RequestParam(required = false) String nameAsset,
            @RequestParam(required = false) String nameOfPlanList,
            @RequestParam(required = false) String supplierName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate licenseDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate licenseDateEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate warrantyPeriodStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate warrantyPeriodEnd,
            @RequestParam(required = false) String assetStatus1,
            @RequestParam(required = false) String assetStatus2,
            Pageable pageable) {

        AssetStatus status1 = (assetStatus1 != null) ? AssetStatus.valueOf(assetStatus1) : null;
        AssetStatus status2 = (assetStatus2 != null) ? AssetStatus.valueOf(assetStatus2) : null;

        Page<Asset> assetStatus = assetRepository.searchAdvanced("%"+itemCode+"%", "%"+nameAsset+"%", "%"+nameOfPlanList+"%", "%"+supplierName+"%", licenseDateStart, licenseDateEnd, warrantyPeriodStart, warrantyPeriodEnd, status1, status2, pageable);

        return new ResponseEntity<>(assetStatus, HttpStatus.OK);
    }
}
