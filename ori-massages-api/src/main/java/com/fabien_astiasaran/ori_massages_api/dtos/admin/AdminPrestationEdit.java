package com.fabien_astiasaran.ori_massages_api.dtos.admin;

import com.fabien_astiasaran.ori_massages_api.validators.FileSize;
import com.fabien_astiasaran.ori_massages_api.validators.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class AdminPrestationEdit {
    @NotBlank private String name;
    @NotBlank private String typeName;
    @NotBlank private String durationLabel;
    @Positive private Integer price;
    @NotBlank private String description;
    @NotNull private Boolean active;
    @FileType(types = {
            MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE
    })
    @FileSize(max = FileSize.TWO_MB)
    private MultipartFile image;

    public AdminPrestationEdit() {
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @NotBlank String getTypeName() {
        return typeName;
    }

    public void setTypeName(@NotBlank String typeName) {
        this.typeName = typeName;
    }

    public @NotBlank String getDurationLabel() {
        return durationLabel;
    }

    public void setDurationLabel(@NotBlank String durationLabel) {
        this.durationLabel = durationLabel;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
