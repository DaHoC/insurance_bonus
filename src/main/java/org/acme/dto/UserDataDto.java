package org.acme.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.jboss.resteasy.reactive.RestForm;

import java.util.UUID;

@RegisterForReflection
public class UserDataDto {

    @RestForm("userId")
    private UUID userId;
    @RestForm("distancePerYearInKm")
    private Integer distancePerYearInKm;
    @RestForm("zipCodeOfVehicleRegistration")
    private Integer zipCodeOfVehicleRegistration;
    @RestForm("carType")
    private String carType;

    public UserDataDto() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getDistancePerYearInKm() {
        return distancePerYearInKm;
    }

    public void setDistancePerYearInKm(Integer distancePerYearInKm) {
        this.distancePerYearInKm = distancePerYearInKm;
    }

    public Integer getZipCodeOfVehicleRegistration() {
        return zipCodeOfVehicleRegistration;
    }

    public void setZipCodeOfVehicleRegistration(Integer zipCodeOfVehicleRegistration) {
        this.zipCodeOfVehicleRegistration = zipCodeOfVehicleRegistration;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "UserDataDto{" +
                "userId=" + userId +
                ", distancePerYearInKm=" + distancePerYearInKm +
                ", zipCodeOfVehicleRegistration=" + zipCodeOfVehicleRegistration +
                ", carType='" + carType + '\'' +
                '}';
    }
}
