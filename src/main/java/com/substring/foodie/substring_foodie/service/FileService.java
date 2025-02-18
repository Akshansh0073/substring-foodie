package com.substring.foodie.substring_foodie.service;

import com.substring.foodie.substring_foodie.dto.FileData;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

   FileData uploadFile(MultipartFile file, String path) throws IOException;

   void deleteFile(String path);

   Resource loadFile(String path);

}
