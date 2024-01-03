package com.example.asset_management_idnes.web.rest;

import com.example.asset_management_idnes.domain.PlanList;
import com.example.asset_management_idnes.domain.Unit;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.model.request.CreatePlanListRequest;
import com.example.asset_management_idnes.model.request.UpdatePlanListRequest;
import com.example.asset_management_idnes.model.response.ErrorResponse;
import com.example.asset_management_idnes.repository.PlanListRepository;
import com.example.asset_management_idnes.service.PlanListService;
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
@RequestMapping("/api/v1/plan-list")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlanListResource {

    PlanListService planListService;

    PlanListRepository planListRepository;

    // Tạo danh sách
    @PostMapping("/create-plan-list")
    public ResponseEntity<?> createPlanList(@RequestBody CreatePlanListRequest createPlanListRequest) {
        try {
            PlanList planList = planListService.createList(createPlanListRequest.getYear());
            return ResponseEntity.ok(planList);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Lấy danh sách
    @GetMapping("/get-all-plan-list")
    public List<PlanList> getAllPlanList() {
        return planListRepository.findAll();
    }

    // Lấy danh sách có phân trang
    @GetMapping("/get-all-plan-list-pageable")
    public ResponseEntity<Page<PlanList>> getAllPlanListPageable(Pageable pageable) {
        Page<PlanList> getAllPlanListPageable = planListService.getAll(pageable);
        return new ResponseEntity<>(getAllPlanListPageable, HttpStatus.OK);
    }


    // Xóa danh sách
    @DeleteMapping("delete-plan-list/{id}")
    public ResponseEntity<?> deletePlanList(@PathVariable("id") Long id) throws BadRequestException {
        planListService.deletePlanList(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    // Lấy danh sách theo id
    @GetMapping("/get-plan-list/{id}")
    public ResponseEntity<?> getPlanListById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(planListService.getPlanListById(id));
    }

    // Cập nhật danh sách
    @PutMapping("/update-plan-list/{id}")
    public ResponseEntity<?> updatePlanList(@PathVariable("id") Integer id, @RequestBody UpdatePlanListRequest updatePlanListRequest) {
        try {
            PlanList planList = planListService.updatePlanList(id, updatePlanListRequest.getYear());
            return ResponseEntity.ok(planList);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
