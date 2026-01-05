package com.fabien_astiasaran.ori_massages_api.dtos;

import com.fabien_astiasaran.ori_massages_api.validators.FileSize;
import com.fabien_astiasaran.ori_massages_api.validators.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class LocationCreate {

        @NotBlank
        private String name;

        @NotNull
        private boolean atHome;

        private String address;

        @FileType(types = {
                MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE
        })
        @FileSize(max = FileSize.TWO_MB)
        private MultipartFile image;

        public LocationCreate() {
        }

        public @NotBlank String getName() {
                return name;
        }

        public void setName(@NotBlank String name) {
                this.name = name;
        }

        @NotNull
        public boolean isAtHome() {
                return atHome;
        }

        public void setAtHome(@NotNull boolean atHome) {
                this.atHome = atHome;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public MultipartFile getImage() {
                return image;
        }

        public void setImage(MultipartFile image) {
                this.image = image;
        }
}
