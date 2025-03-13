package org.acme.resource;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.dto.UserDataDto;
import org.acme.dto.UserDataDtoJson;
import org.acme.entity.UserInsuranceBonus;
import org.acme.service.InsuranceBonusCalculationService;

@ApplicationScoped
@Path("/insurancebonus")
public class InsuranceBonusResource {

    @Inject
    InsuranceBonusCalculationService insuranceBonusCalculationService;

    @Location("index.html")
    Template index;

    /**
     * Endpoint to display a simple HTML form for the user to input their data.
     *
     * @return HTML form template
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance displayUserForm() {
        return index.data("insuranceBonus", null);
    }

    /**
     * API for posting the user data to calculate the insurance bonus.
     *
     * @param userDataDto passed DTO containing user data
     * @return HTML website with the form and output of calculated insurance bonus
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public TemplateInstance calculateInsuranceBonusFromUserInput(UserDataDto userDataDto) {
        // TODO: Sanitize user input, use validator
        UserInsuranceBonus userInsuranceBonus = insuranceBonusCalculationService.calculateAndStoreInsuranceBonus(
                userDataDto.getUserId(),
                userDataDto.getDistancePerYearInKm(),
                userDataDto.getZipCodeOfVehicleRegistration(),
                userDataDto.getCarType());
        return displayUserForm().data("insuranceBonus", userInsuranceBonus.insuranceBonus);
    }

    /**
     * M2M JSON API.
     * It should return a DTO instead of an entity in a real application.
     *
     * @param userDataDtoJson passed DTO containing user data in JSON format
     * @return {@link UserInsuranceBonus} containing the calculated insurance bonus alongside the passed data
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public UserInsuranceBonus calculateInsuranceBonusFromOtherSystem(UserDataDtoJson userDataDtoJson) {
        // TODO: Sanitize user input, use validator

        return insuranceBonusCalculationService.calculateAndStoreInsuranceBonus(
                userDataDtoJson.getUserId(),
                userDataDtoJson.getDistancePerYearInKm(),
                userDataDtoJson.getZipCodeOfVehicleRegistration(),
                userDataDtoJson.getCarType());
    }
}
