package com.webnewsproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopPost {
    private String title;
    private Long likeNumber;
    private Date latestDate;
    private Date oldestDate;
}
