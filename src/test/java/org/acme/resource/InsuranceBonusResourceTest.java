package org.acme.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.UserDataDtoJson;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

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
        UserDataDtoJson userDataDtoJson = new UserDataDtoJson();
        userDataDtoJson.setUserId(UUID.randomUUID());
        userDataDtoJson.setDistancePerYearInKm(100_000);
        userDataDtoJson.setZipCodeOfVehicleRegistration(53797);
        userDataDtoJson.setCarType("Cevvy");

        String userDataDtoJsonAsString = objectMapper.writeValueAsString(userDataDtoJson);

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDataDtoJsonAsString)
                .accept(ContentType.JSON)
                .when().post("/insurancebonus")
        .then()
                .statusCode(200);
    }

}