package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.acme.entity.UserInsuranceBonus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class InsuranceBonusCalculationService {

    @Named("regionData")
    public Map<Integer, List<String>> regionData;

    private static final Map<Integer, Double> DISTANCE_FACTOR_LOOKUP = Map.of(
            0, 0.5d,
            1, 0.5d,
            2, 1.0d,
            3, 1.5d,
            4, 1.5d,
            5, 2.0d
    );

    private static final Map<String, Double> REGION_CODE_FACTOR_LOOKUP = new HashMap<>(16);
    static {
        REGION_CODE_FACTOR_LOOKUP.put("DE-BW", 1.0);
        REGION_CODE_FACTOR_LOOKUP.put("DE-BY", 1.1);
        REGION_CODE_FACTOR_LOOKUP.put("DE-BE", 1.2);
        REGION_CODE_FACTOR_LOOKUP.put("DE-BB", 1.3);
        REGION_CODE_FACTOR_LOOKUP.put("DE-HB", 1.4);
        REGION_CODE_FACTOR_LOOKUP.put("DE-HH", 1.5);
        REGION_CODE_FACTOR_LOOKUP.put("DE-HE", 1.6);
        REGION_CODE_FACTOR_LOOKUP.put("DE-MV", 1.7);
        REGION_CODE_FACTOR_LOOKUP.put("DE-NI", 1.8);
        REGION_CODE_FACTOR_LOOKUP.put("DE-NW", 1.9);
        REGION_CODE_FACTOR_LOOKUP.put("DE-RP", 2.0);
        REGION_CODE_FACTOR_LOOKUP.put("DE-SL", 2.1);
        REGION_CODE_FACTOR_LOOKUP.put("DE-SN", 2.2);
        REGION_CODE_FACTOR_LOOKUP.put("DE-ST", 2.3);
        REGION_CODE_FACTOR_LOOKUP.put("DE-SH", 2.4);
        REGION_CODE_FACTOR_LOOKUP.put("DE-TH", 2.5);
    }

    public UserInsuranceBonus calculateAndStoreInsuranceBonus(UUID userId, int distanceInKm, int zipCode, String carType) {
        double insuranceBonus = calculateInsuranceBonus(distanceInKm, zipCode, carType);

        UserInsuranceBonus userInsuranceBonus = new UserInsuranceBonus();
        userInsuranceBonus.userId = userId;
        userInsuranceBonus.distancePerYearInKm = distanceInKm;
        userInsuranceBonus.zipCodeOfVehicleRegistration = zipCode;
        userInsuranceBonus.carType = carType;
        userInsuranceBonus.insuranceBonus = insuranceBonus;
        upsertUserInsuranceBonus(userInsuranceBonus);
        return userInsuranceBonus;
    }

    @Transactional
    void upsertUserInsuranceBonus(UserInsuranceBonus updatedUserInsuranceBonus) {
        // Upsert
        Optional<UserInsuranceBonus> userInsuranceBonusOpt = UserInsuranceBonus.findByIdOptional(updatedUserInsuranceBonus.userId);
        UserInsuranceBonus userInsuranceBonus = userInsuranceBonusOpt.orElse(updatedUserInsuranceBonus);
        userInsuranceBonus.insuranceBonus = updatedUserInsuranceBonus.insuranceBonus;
        userInsuranceBonus.persist();
    }

    private double calculateInsuranceBonus(int distanceInKm, int zipCode, String carType) {
        double distanceFactor = getDistanceFactor(distanceInKm);
        double carTypeFactor = getCarTypeFactor(carType);
        double regionFactor = getRegionFactor(zipCode);
        return distanceFactor * carTypeFactor * regionFactor;
    }

    private double getRegionFactor(int zipCode) {
        List<String> currentRegionData = regionData.get(zipCode);
        String iso31661Alpha2RegionCode = currentRegionData.get(1);
        return REGION_CODE_FACTOR_LOOKUP.getOrDefault(iso31661Alpha2RegionCode, 1.0);
    }

    private double getCarTypeFactor(String carType) {
        return 1.0;
    }

    /**
     * Calculate factor for distance, based on lookup-table:
     * <pre>
     *         0 bis 5.000 km: 0.5
     *         5.001 km bis 10.000 km: 1.0
     *         10.001 km bis 20.000 km: 1.5
     *         ab 20.000km: 2.0
     * </pre>
     * @param distanceInKm distance in kilometers
     * @return distance factor
     */
    public double getDistanceFactor(int distanceInKm) {
        int distanceCategory = Math.ceilDiv(distanceInKm, 5_000);
        distanceCategory = Math.min(5, distanceCategory);  // Limit max category
        distanceCategory = Math.max(0, distanceCategory);  // Limit min category (avoid negative)
        return DISTANCE_FACTOR_LOOKUP.get(distanceCategory);
    }
}
