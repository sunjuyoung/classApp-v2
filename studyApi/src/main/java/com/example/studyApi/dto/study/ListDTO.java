package com.example.studyApi.dto.study;

import com.example.studyApi.domain.Tag;
import com.example.studyApi.domain.Zone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDTO {

    private String path;

    private String title;

    private String shortDescription;

    private Set<Tag> tags = new HashSet<>();

    private Set<Zone> zones = new HashSet<>();

}
