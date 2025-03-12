package org.acme.service;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class InsuranceBonusCalculationServiceTest {

    @Inject
    InsuranceBonusCalculationService insuranceBonusCalculationService;

    @Test
    public void testDistanceFactor() {
        Assertions.assertEquals(0.5d, insuranceBonusCalculationService.getDistanceFactor(0));
        Assertions.assertEquals(0.5d, insuranceBonusCalculationService.getDistanceFactor(5_000));
        Assertions.assertEquals(1.0d, insuranceBonusCalculationService.getDistanceFactor(5_001));
        Assertions.assertEquals(1.0d, insuranceBonusCalculationService.getDistanceFactor(10_000));
        Assertions.assertEquals(1.5d, insuranceBonusCalculationService.getDistanceFactor(10_001));
        Assertions.assertEquals(1.5d, insuranceBonusCalculationService.getDistanceFactor(20_000));
        Assertions.assertEquals(2.0d, insuranceBonusCalculationService.getDistanceFactor(20_001));
        Assertions.assertEquals(2.0d, insuranceBonusCalculationService.getDistanceFactor(50_001));
        Assertions.assertEquals(0.5d, insuranceBonusCalculationService.getDistanceFactor(-5_000));
        Assertions.assertEquals(0.5d, insuranceBonusCalculationService.getDistanceFactor(-20_000));
        Assertions.assertEquals(2.0d, insuranceBonusCalculationService.getDistanceFactor(Integer.MAX_VALUE));
        Assertions.assertEquals(0.5d, insuranceBonusCalculationService.getDistanceFactor(Integer.MIN_VALUE));
    }
}
