package com.fabien_astiasaran.ori_massages_api.services;

import com.fabien_astiasaran.ori_massages_api.dtos.admin.AdminDurationResponse;
import com.fabien_astiasaran.ori_massages_api.dtos.DurationCreate;
import com.fabien_astiasaran.ori_massages_api.entities.Duration;
import com.fabien_astiasaran.ori_massages_api.mappers.DurationMapper;
import com.fabien_astiasaran.ori_massages_api.repositories.DurationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DurationService {

    private final DurationRepository durationRepository;

    public DurationService(DurationRepository durationRepository) {
        this.durationRepository = durationRepository;
    }

    public List<AdminDurationResponse> getDurations(){
        List<Duration> durations = durationRepository.findAll();
        return DurationMapper.toAdminResponse(durations);
    }

    public AdminDurationResponse createDuration(DurationCreate durationCreate){
        Duration duration = new Duration();
        duration.setValue(durationCreate.value());
        duration.setLabel(durationCreate.label());
        duration.setBreakTime(durationCreate.breakTime());
        return DurationMapper.toAdminResponse(durationRepository.save(duration));
    }

    public AdminDurationResponse editDuration(Long id, DurationCreate durationCreate){
        Duration toModify = durationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no Duration with this id"));
        toModify.setValue(durationCreate.value());
        toModify.setLabel(durationCreate.label());
        toModify.setBreakTime(durationCreate.breakTime());
        return DurationMapper.toAdminResponse(durationRepository.save(toModify));
    }

    public void delete(Long id){
        durationRepository.deleteById(id);
    }
}
