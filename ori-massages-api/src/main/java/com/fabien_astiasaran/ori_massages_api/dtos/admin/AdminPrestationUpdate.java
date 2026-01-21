package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.validators.FileSize;
import com.fabien_astiasaran.ori_massages_api.validators.FileType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;


public class AdminPrestationUpdate {
    @NotBlank private String name;
    @NotNull private Long typeId;
    @NotNull private Long durationId;
    @NotNull @Positive private Integer price;
    @NotBlank private String description;
    @NotNull @Min(value = 1) private Integer displayOrder;
    @NotNull private Boolean active;
    @FileType(types = {
            MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE
    })
    @FileSize(max = FileSize.TWO_MB)
    private MultipartFile image;

    public AdminPrestationUpdate() {
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotNull Long getTypeId() {
        return typeId;
    }

    public void setTypeId(@NotNull Long typeId) {
        this.typeId = typeId;
    }

    public @NotNull Long getDurationId() {
        return durationId;
    }

    public void setDurationId(@NotNull Long durationId) {
        this.durationId = durationId;
    }

    public @Positive Integer getPrice() {
        return price;
    }

    public void setPrice(@Positive Integer price) {
        this.price = price;
    }

    public @NotBlank String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public @NotNull Boolean getActive() {
        return active;
    }

    public void setActive(@NotNull Boolean active) {
        this.active = active;
    }

    public @NotNull @Positive Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(@NotNull @Positive Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
