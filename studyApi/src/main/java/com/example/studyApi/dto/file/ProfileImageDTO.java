package com.example.studyApi.dto.file;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProfileImageDTO {

    private MultipartFile file;
}
