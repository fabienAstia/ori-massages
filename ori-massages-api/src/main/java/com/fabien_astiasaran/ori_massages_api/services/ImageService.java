package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.utils.ImageCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageService {

    private String upLoadsLoc;
    private String upLoadsPrest;

    public ImageService(@Value("${uploads.storage.path}/locations")String upLoadsLoc,
                        @Value("${uploads.storage.path}/prestations")String upLoadsPrest) {
        this.upLoadsLoc = upLoadsLoc;
        this.upLoadsPrest = upLoadsPrest;
    }

    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    public String buildImageId(MultipartFile image) {
        UUID uuid = UUID.randomUUID();
        String name = image.getOriginalFilename();
        int index = name.lastIndexOf('.');
        String ext = name.substring(index, name.length());
        return uuid + ext;
    }

    public void storeImage(MultipartFile image, String imageId, ImageCategory
                           imageCategory){
        String folder = getFolder(imageCategory);
        try{
            String dest = String.format("%s/%s", folder, imageId);
            File file = new File(dest);
            image.transferTo(file);
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public boolean deletePreviousImage(String imagePath, ImageCategory imageCategory){
        String folder = getFolder(imageCategory);
        String oldImagePath = String.format("%s/%s", folder, imagePath);
        File fileToDelete = new File(oldImagePath);
        boolean deleted = fileToDelete.delete();
        if(!deleted) {
            log.warn("Deletion failed for image {} (category : {})", imagePath, imageCategory);
        }
        return deleted;
    }

    private String getFolder(ImageCategory imageCategory) {
        return switch (imageCategory){
            case LOCATION -> upLoadsLoc;
            case PRESTATION -> upLoadsPrest;
        };
    }
}
