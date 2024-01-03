package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.*;
import com.example.asset_management_idnes.exception.NotFoundException;
import com.example.asset_management_idnes.model.request.CreateAssetRequest;
import com.example.asset_management_idnes.model.request.CreateManufacturerRequest;
import com.example.asset_management_idnes.model.request.CreatePlanListRequest;
import com.example.asset_management_idnes.model.request.UpdateAssetRequest;
import com.example.asset_management_idnes.model.response.AssetResponse;
import com.example.asset_management_idnes.repository.*;
import com.example.asset_management_idnes.statics.AssetStatus;
import com.example.asset_management_idnes.statics.ContractStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dockerjava.api.exception.BadRequestException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AssetService {

    AssetRepository assetRepository;

    final ObjectMapper objectMapper;

    private EmailService emailService;

    final PlanListService planListService;

    final UnitService unitService;

    final ProductTypeDetailRepository productTypeDetailRepository;

    final UnitRepository unitRepository;

    ContractRepository contractRepository;

    SupplierRepository supplierRepository;

    WarrantyInfoRepository warrantyInfoRepository;

    ManufacturerResponsitory manufacturerResponsitory;

    AssetUpdatedHistoryRepository assetUpdatedHistoryRepository;

    public List<AssetResponse> getAll() {
        List<Asset> assetList = assetRepository.findAll();
        if (!CollectionUtils.isEmpty(assetList)) {
            return assetList.stream().map(u -> objectMapper.convertValue(u, AssetResponse.class)).collect(Collectors.toList());
        }
        System.out.println("get all asset okkk");
        return Collections.emptyList();
    }


    public void createAsset(CreateAssetRequest request) throws BadRequestException {

        String itemCode = RandomStringUtils.randomAlphabetic(7).toUpperCase();


        Long shoppingPlanId = request.getShoppingPlanId();
        ShoppingPlan shoppingPlan = shoppingPlanRepository.findById(shoppingPlanId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm/hàng hóa"));

        Set<Manufacturer> manufacturers = new HashSet<>();
        request.getManufacturerIds().forEach(id -> {
            Optional<Manufacturer> manufacturerOptional = findManufacturerById(id);
            manufacturerOptional.ifPresent(manufacturers::add);
        });

        Set<Supplier> suppliers = new HashSet<>();
        request.getSupplierIds().forEach(id -> {
            Optional<Supplier> supplierOptional = findSupplierById(id);
            supplierOptional.ifPresent(suppliers::add);
        });

        Set<Unit> units = new HashSet<>();
        request.getUnitIds().forEach(id -> {
            Optional<Unit> optionalUnit = unitService.findById(id);
            optionalUnit.ifPresent(units::add);
        });

        Contract contract = new Contract();
        contract.setContractCode(request.getContractCode());
        contract.setContractName(request.getContractName());
        contract.setArchiveLink(request.getArchiveLink());
        contract.setContractStartDate(request.getContractStartDate());
        contract.setContractEndDate(request.getContractEndDate());
        contract.setWarrantyPeriod(request.getWarrantyPeriod());
        contract.setWarrantyStartDate(request.getWarrantyStartDate());
        contract.setWarrantyEndDate(request.getWarrantyEndDate());
        contract.setAttachedFiles(request.getAttachedFiles());
        contract.setContractStatus(ContractStatus.VALID);
        contract.setLookingUpInformation(request.getLookingUpInformation());
        contract.setSuppliers(suppliers);
        contractRepository.save(contract);

        Long contractId = contract.getId();
        request.setContractIds(Collections.singleton(contractId));

        WarrantyInfo warrantyInfo = new WarrantyInfo();
        warrantyInfo.setDescription(request.getDescription());
        warrantyInfo.setWarrantyDate(request.getWarrantyDate());
        warrantyInfoRepository.save(warrantyInfo);

        Long warrantyInfoId = warrantyInfo.getId();
        request.setWarrantyInfoIds(Collections.singleton(warrantyInfoId));


        Asset asset = Asset.assetBuilder()
                .itemCode(itemCode)
                .itemCodeKt(request.getItemCodeKt())
                .serial(request.getSerial())
                .shoppingPlan(shoppingPlan)
                .assetName(request.getAssetName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .assetStatus(AssetStatus.ACTIVE)
                .productOrigin(request.getProductOrigin())
                .specifications(request.getSpecifications())
                .description(request.getDescription())
                .assetLicenceStatus(request.getAssetLicenceStatus())
                .licenceDuration(request.getLicenceDuration())
                .dayStartedUsing(request.getDayStartedUsing())
                .manufacturers(manufacturers)
                .units(units)
                .contracts(request.getContractIds().stream().map(id -> {
                    Contract c = new Contract();
                    c.setId(id);
                    return c;
                }).collect(Collectors.toSet()))
                .warrantyInfos(request.getWarrantyInfoIds().stream().map(id -> {
                    WarrantyInfo c = new WarrantyInfo();
                    c.setId(id);
                    return c;
                }).collect(Collectors.toSet()))
                .build();

        assetRepository.save(asset);
    }

    public Optional<Supplier> findSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Optional<Manufacturer> findManufacturerById(Long id) {
        return manufacturerResponsitory.findById(id);
    }

    public void deleteAsset(Long id) throws BadRequestException {
        Optional<Asset> assetOptional = assetRepository.findById(id);
        if (!assetOptional.isPresent()) {
            throw new NotFoundException("Không tìm thấy dịch vụ này!");
        }

        // Todo: thêm điều kiện ko thể xóa user nếu user đó đang sử dụng tài sản
        assetRepository.deleteById(id);
    }


    // Tìm asset theo id
    public Asset findById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    public void updateAsset(Long id, UpdateAssetRequest updateAssetRequest) {
        Asset asset = assetRepository.findById(id).orElse(null);

        if (asset != null) {
            AssetUpdatedHistory assetUpdatedHistory = new AssetUpdatedHistory();
            assetUpdatedHistory.setAsset(asset);
            assetUpdatedHistory.setItemCodeKt(asset.getItemCodeKt());
            assetUpdatedHistory.setAssetName(asset.getAssetName());

            String typeDetail = asset.getShoppingPlan().getProductTypeDetails()
                    .stream()
                    .map(ProductTypeDetail::getTypeDetail)
                    .collect(Collectors.joining(","));
            assetUpdatedHistory.setTypeDetail(typeDetail);

            Set<ProductType> productTypes = asset.getShoppingPlan().getProductTypeDetails()
                    .stream()
                    .flatMap(productTypeDetail -> productTypeDetail.getProductTypes().stream())
                    .collect(Collectors.toSet());
            String type = productTypes.stream()
                    .map(ProductType::getType)
                    .collect(Collectors.joining(","));
            assetUpdatedHistory.setType(type);

            String unit = asset.getUnits()
                    .stream()
                    .map(Unit::getUnitName)
                    .collect(Collectors.joining(","));
            assetUpdatedHistory.setUnit(unit);

            assetUpdatedHistory.setSpecifications(asset.getSpecifications());
            assetUpdatedHistory.setProductOrigin(asset.getProductOrigin());
            assetUpdatedHistory.setSerial(asset.getSerial());
            assetUpdatedHistory.setDescription(asset.getDescription());
            assetUpdatedHistory.setQuantity(asset.getQuantity());
            assetUpdatedHistory.setPrice(asset.getPrice());
            assetUpdatedHistory.setAssetLicenceStatus(asset.getAssetLicenceStatus().toString());
            assetUpdatedHistory.setLicenceDuration(asset.getLicenceDuration());
            assetUpdatedHistory.setDayStartedUsing(asset.getDayStartedUsing());
//            assetUpdatedHistory.setLookingUpInformation(asset.getLookingUpInformation());

            String contractCode = asset.getContracts()
                    .stream()
                    .map(Contract::getContractCode)
                    .collect(Collectors.joining(","));
            assetUpdatedHistory.setContractCode(contractCode);

            String contractName = asset.getContracts()
                    .stream()
                    .map(Contract::getContractName)
                    .collect(Collectors.joining(","));
            assetUpdatedHistory.setContractName(contractName);

            String archiveLink = asset.getContracts()
                    .stream()
                    .map(Contract::getArchiveLink)
                    .collect(Collectors.joining(","));
            assetUpdatedHistory.setArchiveLink(archiveLink);

            Optional<LocalDate> contractStartDate = asset.getContracts()
                    .stream()
                    .map(Contract::getContractStartDate)
                    .findFirst();
            assetUpdatedHistory.setContractStartDate(contractStartDate.get());

            Optional<LocalDate> contractEndDate = asset.getContracts()
                    .stream()
                    .map(Contract::getContractEndDate)
                    .findFirst();
            assetUpdatedHistory.setContractEndDate(contractEndDate.get());

            Optional<LocalDate> warrantyStartDate = asset.getContracts()
                    .stream()
                    .map(Contract::getWarrantyStartDate)
                    .findFirst();
            assetUpdatedHistory.setWarrantyStartDate(warrantyStartDate.get());

            Optional<LocalDate> warrantyEndDate = asset.getContracts()
                    .stream()
                    .map(Contract::getWarrantyEndDate)
                    .findFirst();
            assetUpdatedHistory.setWarrantyEndDate(warrantyEndDate.get());

            Integer warrantyPeriod = asset.getContracts()
                    .stream()
                    .mapToInt(Contract::getWarrantyPeriod)
                    .sum();
            assetUpdatedHistory.setWarrantyPeriod(warrantyPeriod);

            String attachedFiles = asset.getContracts()
                    .stream()
                    .map(Contract::getAttachedFiles)
                    .collect(Collectors.joining(","));
            assetUpdatedHistory.setAttachedFiles(attachedFiles);

            assetUpdatedHistory.setVersion(generateNextVersion(asset));
            assetUpdatedHistory.setModifiedBy(asset.getModifiedBy());

            assetUpdatedHistoryRepository.save(assetUpdatedHistory);


            ShoppingPlan shoppingPlan = shoppingPlanRepository.findById(id).orElse(null);
            Set<ProductTypeDetail> productTypeDetailSet = new LinkedHashSet<>();
            for (Long productTypeDetail : updateAssetRequest.getProductTypeDetailId()) {
                productTypeDetailSet.add(productTypeDetailRepository.findById(productTypeDetail).orElse(null));
            }
            if (shoppingPlan != null) {
                shoppingPlan.setProductTypeDetails(productTypeDetailSet);
            }


            if (asset != null) {
                if (updateAssetRequest.getContractStatus() != null) {

                }
                asset.setItemCodeKt(updateAssetRequest.getItemCodeKt());


                asset.setItemCodeKt(updateAssetRequest.getItemCodeKt());
                asset.setItemCodeKt(updateAssetRequest.getItemCodeKt());
            }
        }
    }

    private String generateNextVersion(Asset asset) {
        return String.format("%02d", Integer.parseInt(assetUpdatedHistoryRepository.findLatestVersion(asset.getId())) + 1);
    }

    public Page<Asset> getAllAssets(Pageable pageable) {
        return assetRepository.findAll(pageable);
    }


    final ShoppingPlanRepository shoppingPlanRepository;


    // Gửi email thông báo khi sản phẩm hết hạn
    public void sendWarrantyExpirationNotifications() throws MessagingException {
//        List<Asset> assets = assetRepository.findAll(); // Lấy tất cả các tài sản
//
//        for (Asset asset : assets) {
//            if (asset.getWarrantyExpirationDate().isEqual(LocalDate.now())) {
//                String productName = asset.getProductName();
//
//                String subject = "Thông báo về sản phẩm hết hạn bảo hành";
//                String text = "Sản phẩm " + productName + " của bạn đã hết hạn bảo hành.";
//                emailService.sendEmailToRecipients(subject, text);
//            }
//        }
    }

    public Long getAssetIdByShoppingPlanId(Long shoppingPlanId) {
        Asset asset = assetRepository.findByShoppingPlanId(shoppingPlanId);

        if (asset != null) {
            return asset.getId();
        } else {
            return null;
        }
    }

    public void changeAssetStatus(Long id) {
        Optional<Asset> assetOptional = assetRepository.findById(id);

        if (assetOptional.isPresent()) {
            Asset asset = assetOptional.get();

            if (asset.getAssetStatus() == AssetStatus.ACTIVE) {
                asset.setAssetStatus(AssetStatus.INACTIVE);
            } else {
                asset.setAssetStatus(AssetStatus.ACTIVE);
            }

            assetRepository.save(asset);
        } else {
            throw new NotFoundException("ko tìm thấy asset");
        }
    }

}
