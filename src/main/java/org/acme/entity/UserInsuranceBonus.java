package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class UserInsuranceBonus extends PanacheEntityBase {

    @Id
    public UUID userId;

    public Integer distancePerYearInKm;

    public Integer zipCodeOfVehicleRegistration;

    public String carType;

    public Double insuranceBonus;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserInsuranceBonus that)) return false;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return "UserInsuranceBonus{" +
                "userId=" + userId +
                ", insuranceBonus=" + insuranceBonus +
                '}';
    }
}
