package com.markp.importer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyMessage {

    private String productName;

    private String productCategory;

    private String productDescription;

    private BigDecimal productPrice;

    private int productStock;

    private String productImageFilepath;
}