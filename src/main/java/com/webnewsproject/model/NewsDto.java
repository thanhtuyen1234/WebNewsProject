package com.webnewsproject.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Data
public class NewsDto {
    private int newsId;
    private String title;
    private String content;
    private String author;
    private Date dateUpload;
    private int statusId;
    private int categoryId;



}
