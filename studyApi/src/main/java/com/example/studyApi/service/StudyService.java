package com.example.studyApi.service;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import com.example.studyApi.dto.StudyDTO;
import com.example.studyApi.dto.TagDTO;
import com.example.studyApi.dto.ZoneDTO;

import java.util.List;

public interface StudyService {

    String createStudy(StudyDTO studyDTO,String nickname);

    StudyDTO getStudy(String path);

    TagDTO addStudyTag(String path, TagDTO tagDTO);

    List<Tag> getTags(String path);

    List<Zone> getZones(String path);

    void addZone(List<ZoneDTO> zoneData, String path);
}
