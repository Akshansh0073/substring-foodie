package com.substring.foodie.substring_foodie.dto;

import lombok.*;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class FileData {
//    private String fileName;
//    private String filePath;
//}

public record FileData(String fileName, String filePath) {
}
