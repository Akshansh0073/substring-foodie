package com.substring.foodie.substring_foodie.service.ServiceImpl;

import com.substring.foodie.substring_foodie.Exception.InvalidFilePathException;
import com.substring.foodie.substring_foodie.dto.FileData;
import com.substring.foodie.substring_foodie.service.FileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileUploadService implements FileService {

    private Logger log = LoggerFactory.getLogger(FileUploadService.class);

    @Override
    public FileData uploadFile(MultipartFile file, String path) throws IOException {

        if(path.isBlank()) {
            throw new InvalidFilePathException("Invalid upload path !!");
        }

        //uploads/abc.png
        Path folderPath = Paths.get(path.substring(0,path.lastIndexOf("/") + 1));
        log.info(folderPath.toString());

        // If Folder not exist then folder is created
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
//----------------------------------------------------------------------------------------------------------------------
        // condition 1 . check file
        String fileName = path.substring(path.lastIndexOf("/") + 1);

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if(extension.equals("jpg")||extension.equals("jpeg")||
                extension.equals("png")||extension.equals("gif")){

        } else {
            throw new InvalidFilePathException("Invalid file extension. Only .jpg, .jpeg, .png, .gif are allowed !!");
        }

        // condition 2. check content
        String content = file.getContentType();
        if (content.equals("image/jpg")||content.equals("image/jpeg")||
                content.equals("image/png")||content.equals("image/gif")){

        } else {
            throw new InvalidFilePathException("Invalid file content. Only images are allowed !!");
        }

//--------------------------------------------------------------------------------------------------------------------------------

        Path filePath = Paths.get(path);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileData fileData = new FileData(fileName,path);

        log.info(fileData.fileName());
        log.info(fileData.filePath());

        return fileData;
    }

    @Override
    public void deleteFile(String path) {

    }

    @Override
    public Resource loadFile(String path) {
        return null;
    }
}
