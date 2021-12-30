package com.webnewsproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "shares")
public class Shares {
    @Id
    @Column(name = "share_id")
    private int shareId;

    @Column(name = "email")
    private String email;

    @Column(name = "share_date")
    private Date shareDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
}
