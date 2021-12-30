package com.webnewsproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "status")
@Data
public class Status {
    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "status")
    private List<News> news;
}
