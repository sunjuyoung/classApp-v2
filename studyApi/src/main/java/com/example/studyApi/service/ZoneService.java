package com.example.studyApi.service;


import com.example.studyApi.domain.Zone;
import com.example.studyApi.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    @PostConstruct
    public void initZone() throws IOException{
        if(zoneRepository.count() == 0){
            Resource resource = new ClassPathResource("zone_kr.csv");
            log.info(resource.getFile().toPath());

            List<Zone> collect = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8).stream()
                    .map(line -> {
                        String[] split = line.split(",");
                        return Zone.builder()
                                .city(split[0])
                                .localNameOfCity(split[1])
                                .province(split[2])
                                .build();

                    }).collect(Collectors.toList());
            zoneRepository.saveAll(collect);
        }
    }


}
