package com.webnewsproject.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "email")
    private String email;
    @Column(name = "enable")
    private boolean enable;
    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @Column(name = "avatar")
    private String avartar;

    @Transient
    private String avartarPath;

    @OneToMany(mappedBy = "user")
    private List<Likes> likes;

    @OneToMany(mappedBy = "user")
    private List<Comments> comments;

    @OneToMany(mappedBy = "user")
    private List<Shares> shares;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Roles> roles;

    public String getAvartarPath(){
        if (avartar == null) return null;
        return "/uploads/AvarterUserUploadFolder/"+avartar;
    }

    public boolean hasRoles(String roleName){
        Iterator<Roles> iterator = this.roles.iterator();
        while (iterator.hasNext()){
            Roles role = iterator.next();
            if (role.getRoleName().equals(roleName)){
                return true;
            }
        }
        return false;
    }
}
