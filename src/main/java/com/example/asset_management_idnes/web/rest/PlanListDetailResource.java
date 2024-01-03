package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.PlanListDetail;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.model.request.CreatePlanListDetailRequest;
import com.example.asset_management_idnes.model.request.UpdatePlanListDetailRequest;
import com.example.asset_management_idnes.model.request.UpdateProductTypeDetailRequest;
import com.example.asset_management_idnes.model.response.ListPlanListDetailResponse;
import com.example.asset_management_idnes.repository.PlanListDetailRepository;
import com.example.asset_management_idnes.service.PlanListDetailService;
import com.example.asset_management_idnes.statics.PlanListDetailStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/plan-list-detail")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class PlanListDetailResource {

    PlanListDetailService planListDetailService;

    PlanListDetailRepository planListDetailRepository;

        // Tạo mới danh sách
    @PostMapping("/create-plan-list-detail")
    public ResponseEntity<?> createPlanListDetail(@RequestBody CreatePlanListDetailRequest createPlanListDetailRequest) throws BadRequestException {
        planListDetailService.createPlanListDetail(createPlanListDetailRequest);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/change-status")
    public void changeStatusToPending(@RequestParam(value = "id") Long id, @RequestParam("status") String status) {
        planListDetailService.changeProductStatus(id, status);
    }

    // Lấy danh sách có phân trang
    @GetMapping("/get-all-plan-list-detail-pageable")
    public ResponseEntity<Page<PlanListDetail>> getAllPlanListDetailPageable(Pageable pageable) {
        Page<PlanListDetail> planListDetails = planListDetailService.getAllPlanListDetailPageable(pageable);
        return new ResponseEntity<>(planListDetails, HttpStatus.OK);
    }


//    // Lấy danh sách ko phân trang
//    @GetMapping("/get-all-plan-list-detail")
//    public List<PlanListDetail> getAllPlanListDetail() {
//        return planListDetailRepository.findAll();
//    }


    // Lấy danh sách theo id
    @GetMapping("/get-plan-list-detail/{id}")
    public ResponseEntity<?> getPlanListDetailById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(planListDetailService.getPlanListDetailById(id));
    }

    // Cập nhật danh sách
    @PutMapping("/update-plan-list-detail/{id}")
    public ResponseEntity<?> updatePlanListDetail(@PathVariable("id") Long id, @RequestBody UpdatePlanListDetailRequest updatePlanListDetailRequest) {
        planListDetailService.updatePlanListDetail(id, updatePlanListDetailRequest);
        return ResponseEntity.ok(null);
    }

    // Xóa danh sách
    @DeleteMapping("/delete-plan-list-detail/{id}")
    public ResponseEntity<?> deletePlanListDetail(@PathVariable("id") Long id) throws BadRequestException {
        planListDetailService.deletePlanListDetail(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // Lấy danh sách PlanListDetail by PlanList
    @GetMapping("/get-plan-list-detail-by-plan-list")
    public ResponseEntity<?> getPlanListDetailByPlanList(@RequestParam Long idPlanList){
        List<ListPlanListDetailResponse> result = planListDetailService.findByPlanList(idPlanList);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlanListDetail>> searchAllPlanListDetail(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) Long planListID,
            @RequestParam(required = false) String nameOfPlanList,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDateEnd,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate approvedDateStart,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate approvedDateEnd,
            @RequestParam(required = false) String planListDetailStatus1,
            @RequestParam(required = false) String planListDetailStatus2,
            @RequestParam(required = false) String planListDetailStatus3,
            @RequestParam(required = false) String planListDetailStatus4,
            Pageable pageable) {

        PlanListDetailStatus status1 = (planListDetailStatus1 != null) ? PlanListDetailStatus.valueOf(planListDetailStatus1) : null;
        PlanListDetailStatus status2 = (planListDetailStatus2 != null) ? PlanListDetailStatus.valueOf(planListDetailStatus2) : null;
        PlanListDetailStatus status3 = (planListDetailStatus3 != null) ? PlanListDetailStatus.valueOf(planListDetailStatus3) : null;
        PlanListDetailStatus status4 = (planListDetailStatus4 != null) ? PlanListDetailStatus.valueOf(planListDetailStatus4) : null;

        Page<PlanListDetail> planListDetail = planListDetailRepository.searchAdvanced("%"+year+"%" ,planListID , "%"+nameOfPlanList+"%", createdDateStart, createdDateEnd, approvedDateStart, approvedDateEnd, status1, status2, status3, status4, pageable);

        return new ResponseEntity<>(planListDetail, HttpStatus.OK);
    }
}
