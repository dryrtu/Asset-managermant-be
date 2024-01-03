package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.*;
import com.example.asset_management_idnes.domain.Currency;
import com.example.asset_management_idnes.exception.BadRequestException;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.model.request.CreateAssetRequest;
import com.example.asset_management_idnes.model.request.CreateShoppingPlanRequest;
import com.example.asset_management_idnes.model.request.UpdateShoppingPlanRequest;
import com.example.asset_management_idnes.model.response.ListPlanListDetailResponse;
import com.example.asset_management_idnes.model.response.ListShoppingPlanResponse;
import com.example.asset_management_idnes.model.response.ListVersionUpdateByShoppingPlanResponse;
import com.example.asset_management_idnes.repository.*;
import com.example.asset_management_idnes.security.SecurityUtils;
import com.example.asset_management_idnes.statics.AssetStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Async
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShoppingPlanService {
    final ShoppingPlanRepository shoppingPlanRepository;

    final PlanListDetailService planListDetailService;

    final ProductTypeDetailService productTypeDetailService;

    final UnitService unitService;

    final CurrencyService currencyService;

    final PlanListDetailRepository planListDetailRepository;

    final ProductTypeDetailRepository productTypeDetailRepository;

    final UnitRepository unitRepository;

    final CurrencyRepository currencyRepository;

    final AssetRepository assetRepository;

    final SupplierService supplierService;

    final ShoppingPlanUpdatedHistoryRepository shoppingPlanUpdatedHistoryRepository;

    // Tạo shopping-plan
    public void createShoppingPlan(CreateShoppingPlanRequest createShoppingPlanRequest) throws BadRequestException {

//        String serial;
//        do {
//            serial = RandomStringUtils.randomAlphabetic(7).toUpperCase();
//        } while (isSerialExists(serial));

        Set<PlanListDetail> planListDetails = new HashSet<>();
        createShoppingPlanRequest.getPlanListDetailId().forEach(id -> {
            Optional<PlanListDetail> optionalPlanListDetail = planListDetailService.findById(id);
            optionalPlanListDetail.ifPresent(planListDetails::add);
        });


        Set<ProductTypeDetail> productTypeDetails = new HashSet<>();
        createShoppingPlanRequest.getProductTypeDetailId().forEach(id -> {
            Optional<ProductTypeDetail> optionalProductTypeDetail = productTypeDetailService.findById(id);
            optionalProductTypeDetail.ifPresent(productTypeDetails::add);
        });


        Set<Unit> units = new HashSet<>();
        createShoppingPlanRequest.getUnitId().forEach(id -> {
            Optional<Unit> optionalUnit = unitService.findById(id);
            optionalUnit.ifPresent(units::add);
        });


        Set<Currency> currencies = new HashSet<>();
        createShoppingPlanRequest.getCurrencyId().forEach(id -> {
            Optional<Currency> optionalCurrency = currencyService.findById(id);
            optionalCurrency.ifPresent(currencies::add);
        });

        ShoppingPlan shoppingPlan = ShoppingPlan.builder()
                .createdBy(SecurityUtils.getCurrentUserLogin().get())
                .createdDateTime(LocalDate.now())
//                .serial(serial)
                .productName(createShoppingPlanRequest.getProductName())
                .quantity(createShoppingPlanRequest.getQuantity())
                .description(createShoppingPlanRequest.getDescription())
                .status(AssetStatus.ACTIVE)
                .planListDetails(planListDetails)
                .currencies(currencies)
                .units(units)
                .expectedPrice(createShoppingPlanRequest.getExpectedPrice())
                .isDraft(true)
                .productTypeDetails(productTypeDetails)
                .build();
        shoppingPlanRepository.save(shoppingPlan);
    }

//    public boolean isSerialExists(String serial) {
//        // Kiểm tra xem có ShoppingPlan nào có serial trùng với serial được truyền vào hay không
//        return shoppingPlanRepository.existsBySerial(serial);
//    }

    public void changeProductStatus(Long id, String status) {
        Optional<ShoppingPlan> shoppingPlanOptional = shoppingPlanRepository.findById(id);

        if (shoppingPlanOptional.isPresent()) {
            ShoppingPlan shoppingPlan = shoppingPlanOptional.get();

            for (AssetStatus s : AssetStatus.values()) {
                if (s.getName().equals(status)) {
                    shoppingPlan.setStatus(s);
                    shoppingPlanRepository.save(shoppingPlan);
                    return;
                }
            }
        }
    }

    public void changeProductStatusToApproved(Long id, String status) {
        Optional<ShoppingPlan> shoppingPlanOptional = shoppingPlanRepository.findById(id);

        if (shoppingPlanOptional.isPresent()) {
            ShoppingPlan shoppingPlan = shoppingPlanOptional.get();

            for (AssetStatus s : AssetStatus.values()) {
                if (s.getName().equals(status)) {
                    shoppingPlan.setStatus(s);
                    shoppingPlan.setApprovedBy(SecurityUtils.getCurrentUserLogin().get());
                    shoppingPlanRepository.save(shoppingPlan);
                    return;
                }
            }
        }
    }

    public void changeProductStatusToDone(Long id, String status, CreateAssetRequest createAssetRequest) {
//        Optional<ShoppingPlan> shoppingPlanOptional = shoppingPlanRepository.findById(id);
//
//        if (shoppingPlanOptional.isPresent()) {
//            for (AssetStatus s : AssetStatus.values()) {
//                if (s.getName().equals(status)) {
//                    shoppingPlanOptional.get().setStatus(s);
//                    shoppingPlanRepository.save(shoppingPlanOptional.get());
//
//                    ShoppingPlan shoppingPlan = shoppingPlanOptional.get();
//
//                    Set<Supplier> suppliers = new HashSet<>();
//                    createAssetRequest.getSupplierId().forEach(ids -> suppliers.add(supplierService.findSupplierById(ids).get()));
//
//                    Asset asset = new Asset();
//                    asset.setShoppingPlan(shoppingPlan);
//                    asset.setItemCode(createAssetRequest.getItemCode());
//                    asset.setWarrantyPeriod(createAssetRequest.getWarrantyPeriod());
//                    asset.setWarrantyStartDate(createAssetRequest.getWarrantyStartDate());
//                    asset.setContractCode(createAssetRequest.getContractCode());
//                    asset.setContractName(createAssetRequest.getContractName());
//                    asset.setPrice(createAssetRequest.getPrice());
//                    asset.setSuppliers(suppliers);
//                    asset.setProductOrigin(createAssetRequest.getProductOrigin());
//
//                    assetRepository.save(asset);
//                    return;
//                }
//            }
//        }
    }


    // Thay đổi thông tin sản phẩm trong kế hoạch mua sắm
    public void updateShoppingPlanRequest(Long id, UpdateShoppingPlanRequest updateShoppingPlanRequest) {
        ShoppingPlan shoppingPlan = shoppingPlanRepository.findById(id).orElse(null);

        if (shoppingPlan != null) {
            ShoppingPlanUpdatedHistory shoppingPlanUpdatedHistory = new ShoppingPlanUpdatedHistory();
            shoppingPlanUpdatedHistory.setShoppingPlan(shoppingPlan);
            shoppingPlanUpdatedHistory.setProductName(shoppingPlan.getProductName());
            shoppingPlanUpdatedHistory.setQuantity(shoppingPlan.getQuantity());
            shoppingPlanUpdatedHistory.setModifiedBy(shoppingPlan.getModifiedBy());
            shoppingPlanUpdatedHistory.setUpdateDate(shoppingPlan.getModifiedDate());

            String typeDetail = shoppingPlan.getProductTypeDetails()
                    .stream()
                    .map(ProductTypeDetail::getTypeDetail)
                    .collect(Collectors.joining(","));
            shoppingPlanUpdatedHistory.setTypeDetail(typeDetail);

            String unit = shoppingPlan.getUnits()
                    .stream()
                    .map(Unit::getUnitName)
                    .collect(Collectors.joining(","));
            shoppingPlanUpdatedHistory.setUnit(unit);

            String currencyString = shoppingPlan.getCurrencies()
                    .stream()
                    .map(Currency::getCurrencyUnit)
                    .collect(Collectors.joining(","));
            shoppingPlanUpdatedHistory.setCurrency(currencyString);

            String planListDetail = shoppingPlan.getPlanListDetails()
                    .stream()
                    .map(PlanListDetail::getNameOfPlanList)
                    .collect(Collectors.joining(","));
            shoppingPlanUpdatedHistory.setPlanListDetail(planListDetail);

            shoppingPlanUpdatedHistory.setVersion(generateNextVersion(shoppingPlan));

            shoppingPlanUpdatedHistory.setModifiedBy(shoppingPlan.getModifiedBy());

            shoppingPlanUpdatedHistoryRepository.save(shoppingPlanUpdatedHistory);
        }

        Set<ProductTypeDetail> productTypeDetailSet = new LinkedHashSet<>();
        for (Long productTypeDetail : updateShoppingPlanRequest.getProductTypeDetailId()) {
            productTypeDetailSet.add(productTypeDetailRepository.findById(productTypeDetail).orElse(null));
        }

        Set<Unit> unitSet = new LinkedHashSet<>();
        for (Long unit : updateShoppingPlanRequest.getUnitId()) {
            unitSet.add(unitRepository.findById(unit).orElse(null));
        }

        Set<Currency> currencySet = new LinkedHashSet<>();
        for (Long currency : updateShoppingPlanRequest.getCurrencyId()) {
            currencySet.add(currencyRepository.findById(currency).orElse(null));
        }

        if (shoppingPlan != null) {
            shoppingPlan.setProductName(updateShoppingPlanRequest.getProductName());
            shoppingPlan.setQuantity(updateShoppingPlanRequest.getQuantity());
            shoppingPlan.setDescription(updateShoppingPlanRequest.getDescription());
            shoppingPlan.setProductTypeDetails(productTypeDetailSet);
            shoppingPlan.setUnits(unitSet);
            shoppingPlan.setCurrencies(currencySet);
            shoppingPlan.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
            shoppingPlan.setExpectedPrice(updateShoppingPlanRequest.getExpectedPrice());
            shoppingPlan.setModifiedDate(LocalDate.now());
            shoppingPlanRepository.save(shoppingPlan);
        }
    }


    private String generateNextVersion(ShoppingPlan shoppingPlan) {
        return String.format("%02d", Integer.parseInt(shoppingPlanUpdatedHistoryRepository.findLatestVersion(shoppingPlan.getId())) + 1);
    }


    // Lấy thông tin shopping-plan theo id
    public ShoppingPlan findById(Long id) {
        return shoppingPlanRepository.findById(id).orElse(null);
    }

    public void deleteShoppingPlan(Long id) throws NotFoundException {
        ShoppingPlan shoppingPlan = shoppingPlanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm này"));
        shoppingPlanRepository.delete(shoppingPlan);
    }


    // Lấy danh sách sản phẩm
    public Page<ShoppingPlan> getAll(Pageable pageable) {
        return shoppingPlanRepository.findAll(pageable);
    }


    // Tìm kiếm và phân trang
    public Page<ShoppingPlan> searchAllShoppingPlanByParam(String productName, Long planListId, Long planListDetailId, Long unitId, Long currencyId, Long productTypeId, Long productTypeDetailId, Pageable pageable) {

        Page<ShoppingPlan> shoppingPlanList = null;
        shoppingPlanList = shoppingPlanRepository.searchShoppingPlans('%' + productName + '%', planListId, planListDetailId, unitId, currencyId, productTypeId, productTypeDetailId,  pageable);

        return shoppingPlanList;
    }

    // Tìm kiếm và phân trang
    public Page<ShoppingPlan> searchByProductName(String productName, Pageable pageable) {
        Page<ShoppingPlan> shoppingPlanList = null;
        shoppingPlanList = shoppingPlanRepository.searchByProductName('%' + productName + '%', pageable);
        return shoppingPlanList;
    }

    public Page<ListShoppingPlanResponse> findByPlanListDetail(Long idPlanListDetail, Pageable pageable) {
        Page<ShoppingPlan> shoppingPlanPage = shoppingPlanRepository.findByPlanListDetails(idPlanListDetail, pageable);

        List<ListShoppingPlanResponse> shoppingPlanResponses = shoppingPlanPage.getContent()
                .stream()
                .map(sp -> ListShoppingPlanResponse.builder()
                        .id(sp.getId())
//                        .serial(sp.getSerial())
                        .productName(sp.getProductName())
                        .quantity(sp.getQuantity())
                        .description(sp.getDescription())
                        .status(sp.getStatus())
                        .planListDetails(sp.getPlanListDetails())
                        .currencies(sp.getCurrencies())
                        .units(sp.getUnits())
                        .expectedPrice(sp.getExpectedPrice())
                        .isDraft(sp.isDraft())
                        .productTypeDetails(sp.getProductTypeDetails())
                        .createdBy(sp.getCreatedBy())
                        .createdDateTime(sp.getCreatedDateTime())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(shoppingPlanResponses, pageable, shoppingPlanPage.getTotalElements());
    }

    public ShoppingPlanUpdatedHistory getShoppingPlanVersionByIdAndVersion(Long id, String version) {
        return shoppingPlanUpdatedHistoryRepository.findByShoppingPlanIdAndVersion(id, version);
    }


    public List<String> getVersionsByShoppingPlanId(Long shoppingPlanId) {
        return shoppingPlanUpdatedHistoryRepository.findVersionsByShoppingPlanId(shoppingPlanId);
    }

    public void changeIsDraft(Long idPlanListDetail) {
        List<ShoppingPlan> shoppingPlanList = shoppingPlanRepository.findByPlanListDetailsNotPagination(idPlanListDetail);
        for (ShoppingPlan shoppingPlan1  :shoppingPlanList) {
            shoppingPlan1.setDraft(false);
        }
        shoppingPlanRepository.saveAll(shoppingPlanList);
    }
}
