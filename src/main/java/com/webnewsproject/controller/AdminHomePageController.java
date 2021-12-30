package com.webnewsproject.controller;

import com.webnewsproject.model.TopPost;
import com.webnewsproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminHomePageController {
    @Autowired
    UserService userService;
    @Autowired
    NewsService newsService;
    @Autowired
    CommentService cmtService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    StatusService statusService;
    @Autowired
    LikeService likeService;


    @GetMapping("/admin")
    public String admin(Model model){

//        List<TopPost> topPostList = likeService.getTopPost();
//        topPostList.forEach( item -> {
//            System.out.println(item.getTitle());
//        });

//        model.addAttribute("topPostList",topPostList);
        model.addAttribute("numberOfUser",userService.countUser());
        model.addAttribute("numberOfCategory",categoryService.countCategory());
        model.addAttribute("numberOfPost", newsService.countNews());
        model.addAttribute("numberOfStatus", statusService.countStatus());
        model.addAttribute("numberOfComment", cmtService.countComment());
        return "admin/admin";
    }
}
