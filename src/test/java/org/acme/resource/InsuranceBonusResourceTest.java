package org.acme.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.UserDataDtoJson;
import org.acme.entity.UserInsuranceBonus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
class InsuranceBonusResourceTest {

    @Inject
    ObjectMapper objectMapper;

    @Test
    void testInsuranceBonusCustomerEndpoint() {
        Map<String, Object> formParams = Map.of(
                "userId", UUID.randomUUID(),
                "distancePerYearInKm", 100_000,
                "zipCodeOfVehicleRegistration", 53797,
                "carType", "Chevvy"
        );

        given()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .formParams(formParams)
                .accept(ContentType.HTML)
                .when().post("/insurancebonus")
        .then()
                .statusCode(200);
    }
    @Test
    void testInsuranceBonusSystemEndpoint() throws JsonProcessingException {

        UUID userId = UUID.randomUUID();
        UserDataDtoJson userDataDtoJson = new UserDataDtoJson();
        userDataDtoJson.setUserId(userId);
        userDataDtoJson.setDistancePerYearInKm(100_000);
        userDataDtoJson.setZipCodeOfVehicleRegistration(53797);
        userDataDtoJson.setCarType("Chevvy");

        String userDataDtoJsonAsString = objectMapper.writeValueAsString(userDataDtoJson);

        UserInsuranceBonus result = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDataDtoJsonAsString)
                .accept(ContentType.JSON)
                .when().post("/insurancebonus")
        .then()
                .statusCode(200)
                .extract()
                .as(UserInsuranceBonus.class);

        Assertions.assertEquals(userId, result.userId);
        Assertions.assertEquals(100_000, result.distancePerYearInKm);
        Assertions.assertEquals(53797, result.zipCodeOfVehicleRegistration);
        Assertions.assertEquals("Chevvy", result.carType);
        Assertions.assertEquals(3.8, result.insuranceBonus);
    }

}