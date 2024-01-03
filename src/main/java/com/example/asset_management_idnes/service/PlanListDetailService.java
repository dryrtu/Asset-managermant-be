package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.*;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.model.request.CreatePlanListDetailRequest;
import com.example.asset_management_idnes.model.request.UpdatePlanListDetailRequest;
import com.example.asset_management_idnes.model.response.ListPlanListDetailResponse;
import com.example.asset_management_idnes.repository.PlanListDetailRepository;
import com.example.asset_management_idnes.repository.PlanListRepository;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.PlanListDetailStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlanListDetailService {

    final PlanListService planListService;

    final PlanListDetailRepository planListDetailRepository;

    final PlanListRepository planListRepository;


    public void createPlanListDetail(CreatePlanListDetailRequest createPlanListDetailRequest) throws BadRequestException {

                Set<PlanList> planListSet = new HashSet<>();
        createPlanListDetailRequest.getPlanListId().forEach(id -> planListSet.add(planListService.findById(id).get()));

        PlanListDetail planListDetail = PlanListDetail.builder()
                .nameOfPlanList(createPlanListDetailRequest.getNameOfPlanList())
                .planList(planListSet)
                .planListDetailStatus(PlanListDetailStatus.DRAFT)
                .createdDate(LocalDate.now())
                .build();
        planListDetailRepository.save(planListDetail);
    }

    public Optional<PlanListDetail> findById(Long id) {
        return planListDetailRepository.findById(id);
    }

    public Page<PlanListDetail> getAllPlanListDetailPageable(Pageable pageable) {
        return planListDetailRepository.findAll(pageable);
    }

    public PlanListDetail getPlanListDetailById(Long id) {
        return planListDetailRepository.findById(id).orElse(null);
    }

    public void updatePlanListDetail(Long id, UpdatePlanListDetailRequest updatePlanListDetailRequest) {
        PlanListDetail planListDetail = planListDetailRepository.findById(id).orElse(null);

        if (planListDetail != null) {
            planListDetail.setNameOfPlanList(updatePlanListDetailRequest.getNameOfPlanList());
            planListDetailRepository.save(planListDetail);
        }
    }

//    public void deletePlanListDetail(Long id) {
//        Optional<PlanListDetail> planListDetailOptional = planListDetailRepository.findById(id);
//        if (!planListDetailOptional.isPresent()) {
//            throw new NotFoundException("Không tìm thấy danh sách này");
//        }
//        planListDetailRepository.deleteById(id);
//    }

    public void deletePlanListDetail(Long id) throws NotFoundException {
        PlanListDetail planListDetail = planListDetailRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh sách này"));
        planListDetailRepository.delete(planListDetail);
    }

    public List<ListPlanListDetailResponse> findByPlanList(Long idPlanList) {
        List<PlanListDetail> planListDetails = planListDetailRepository.findByPlanList(idPlanList);
        List<ListPlanListDetailResponse> planListDetailResponses = new ArrayList<>();
        for (PlanListDetail pl: planListDetails) {
            ListPlanListDetailResponse planListDetailResponse = ListPlanListDetailResponse.builder()
                    .id(pl.getId())
                    .nameOfPlanList(pl.getNameOfPlanList())
                    .planList(pl.getPlanList())
                    .createdDate(pl.getCreatedDate())
                    .approvedDate(pl.getApprovedDate())
                    .approvedBy(pl.getApprovedBy())
                    .planListDetailStatus(pl.getPlanListDetailStatus())
                    .build();
            planListDetailResponses.add(planListDetailResponse);
        }
        return  planListDetailResponses;
    }

    public void changeProductStatus(Long id, String status) {
        Optional<PlanListDetail> planListDetailOptional = planListDetailRepository.findById(id);

        if (planListDetailOptional.isPresent()) {
            PlanListDetail planListDetail = planListDetailOptional.get();

            for (PlanListDetailStatus s : PlanListDetailStatus.values()) {
                if (s.getName().equals(status)) {
                    planListDetail.setPlanListDetailStatus(s);

                    if ("Đã phê duyệt".equals(status)) {
                        planListDetail.setApprovedDate(LocalDate.now());
                    }

                    planListDetailRepository.save(planListDetail);
                    return;
                }
            }
        }
    }

//    public Page<PlanListDetail> searchAdvanced(Long planListID, String nameOfPlanList, LocalDate createdDateStart, LocalDate createdDateEnd, LocalDate approvedDateStart, LocalDate approvedDateEnd, PlanListDetailStatus[] planListDetailStatus, Pageable pageable) {
//
//        Page<PlanListDetail> planListDetail = null;
//        planListDetail = planListDetailRepository.searchAdvanced(planListID,
//                '%' + nameOfPlanList + '%',
//                createdDateStart, createdDateEnd, approvedDateStart,
//                approvedDateEnd, planListDetailStatus,  pageable);
//
//        return planListDetail;
//    }


}
