package com.kidsart.library.dto;

import com.kidsart.library.model.Category;
import com.kidsart.library.model.Image;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    @Min(value = 0, message = "Current quantity must be a non-negative integer")
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
    @NotEmpty(message = "Please upload at least one image")
    private List<Image> images;
    private Category category;
    private boolean activated;
    private boolean deleted;
}
