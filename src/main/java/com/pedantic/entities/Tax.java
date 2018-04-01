package com.pedantic.entities;


import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Tax extends AbstractEntity {


    private BigDecimal taxRate;


    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
