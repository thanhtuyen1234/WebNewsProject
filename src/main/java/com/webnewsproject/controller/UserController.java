package com.webnewsproject.controller;

import com.webnewsproject.domain.Roles;
import com.webnewsproject.domain.Users;
import com.webnewsproject.service.RolesService;
import com.webnewsproject.service.UploadService;
import com.webnewsproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RolesService rolesService;


    @GetMapping("/admin/manage-user")
    public String mangeUser(Model model){
        return listAllUser(1,"asc","fullname",null,model);
    }


    @GetMapping("/admin/manage-user/page/{pageNumber}")
    public String listAllUser(@PathVariable("pageNumber") Integer pageNumber,String sortDir, String sortField, String keyword, Model model){
        if (pageNumber != null){
            Page<Users> page = userService.findAllAndPaging(pageNumber-1,sortDir,sortField,keyword);

            String reverseDir = sortDir.equals("asc")? "dsc" : "asc";

            List<Users> users = page.getContent();
            int totalPage = page.getTotalPages();
            if (totalPage == 0) totalPage=1;
            int totalElement = (int) page.getTotalElements();

            model.addAttribute("users", users);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("totalElement", totalElement);
            model.addAttribute("currentPage", pageNumber);
            model.addAttribute("reverseDir", reverseDir);
            model.addAttribute("keyword", keyword);
            model.addAttribute("currentDir",sortDir);
            model.addAttribute("currentField", sortField);

            return "admin/adminManageUser";
        }
        return "error/404";
    }



    @GetMapping("/admin/add-user")
    public String addUserView(Model model){
        Users user = new Users();
        Set<Roles> rolesSet = new HashSet<>();
        model.addAttribute("user",user);
        model.addAttribute("roleSet",rolesSet);
        model.addAttribute("roles",rolesService.findAll());
        return "admin/adminCreateAccount";
    }

    @PostMapping("/admin/add-user")
    public String addNewUser(
            @ModelAttribute("user") Users user,
            @RequestParam("image")MultipartFile part,
            @RequestParam("roles") List<Integer> roles,
            @RequestParam("oldAvartar") String oldAvartarName,
            @RequestParam("oldPassword") String oldPassword
    ){
        user.setEnable(true);
        //add roles for user
        Set<Roles> rolesSet = new HashSet<>();
        roles.forEach(roleId ->{
            rolesSet.add(rolesService.findById(roleId));
        });
        user.setRoles(rolesSet);

        if (!oldPassword.equals("")){
            user.setPassword(oldPassword);
        }else{
            //encode password before save in database
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String passwordEncode = encoder.encode(user.getPassword());
            user.setPassword(passwordEncode);
        }

        //get file name from MultiPartFile
        String avartarFileName = StringUtils.cleanPath(part.getOriginalFilename());

        if (avartarFileName.equals("")){
            user.setAvartar(oldAvartarName);
        }else{
            user.setAvartar(avartarFileName);
            String uploadDir = "./uploads/AvarterUserUploadFolder";
            try {
                UploadService.uploadImage(part,avartarFileName,uploadDir);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        //save entity in database
        userService.save(user);

        return "redirect:/admin/manage-user";
    }

    @GetMapping("/admin/manage-user/delete/{id}")
    public String deleteUser(@PathVariable("id") int id){
        userService.deleteById(id);
        return "redirect:/admin/manage-user";
    }

    @GetMapping("/admin/manage-user/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model){
        if (id != null){
            Users user = userService.findById(id);
            if (user != null){
                model.addAttribute("user",user);
                model.addAttribute("roles", rolesService.findAll());
                model.addAttribute("roleSet",user.getRoles());
                return "admin/adminCreateAccount";
            }else{
                return "error/404";
            }
        }
        return "error/404";
    }

}
