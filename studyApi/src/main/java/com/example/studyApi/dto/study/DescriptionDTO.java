package com.example.studyApi.dto.study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionDTO {

    @NotBlank
    @Length(min = 2,max = 60)
    private String shortDescription;

    private String fullDescription;
}
