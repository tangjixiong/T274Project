package com.yk.controller;

import com.alibaba.fastjson.JSON;
import com.yk.entity.Role;
import com.yk.entity.User;
import com.yk.service.RoleService;
import com.yk.service.UserService;
import com.yk.util.Page;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/sys")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("/main.html")
    public String toMain(){
        return "frame";
    }

    /*//局部异常处理
    @ExceptionHandler(value = RuntimeException.class)
    public String handleException(RuntimeException e,HttpServletRequest request){
        request.setAttribute("e",e);
        return "error";
    }*/

    //跳转到用户列表
    @RequestMapping("/userlist.html")
    public String toUserList(Model model,Integer pageIndex,String queryname,String queryUserRole){
        //查询所有角色
        List<Role> list=roleService.getAllRole();
        model.addAttribute("roleList",list);

        HashMap<String,Object> hash=new HashMap<>();
        hash.put("curpage",pageIndex);
        hash.put("pagesize",5);
        hash.put("queryname",queryname);
        hash.put("queryUserRole",queryUserRole);
        //分页查询用户信息
        Page page=userService.pagationUser(hash);
        //保存数据到Model
        model.addAttribute("page",page);
        model.addAttribute("queryUserName",queryname);
        model.addAttribute("queryUserRole",queryUserRole);
        return "userlist";
    }
    //进入用户新增界面
    @RequestMapping(value = "/useradd.html",method = RequestMethod.GET)
    public String toUserAdd(@ModelAttribute("user")User user){
        return "useradd";
    }

    //保证新增的用户信息，单文件上传
    /*@RequestMapping(value = "/useradd.html",method = RequestMethod.POST)
    public String userAdd(@Valid User user, BindingResult bindingResult,HttpServletRequest request,
       @RequestParam(value ="attach", required = false)MultipartFile attach){
        if(bindingResult.hasErrors()){
            System.out.println("============数据校验不通过==================");
            return "useradd";
        }
        String idPicPath = null;
        //判断文件是否为空
        if(!attach.isEmpty()){
            String path = request.getSession().getServletContext().getRealPath("static"+ File.separator+"uploadfiles");
            System.out.println("uploadFile path ============== > "+path);
            String oldFileName = attach.getOriginalFilename();//原文件名
            System.out.println("uploadFile oldFileName ============== > "+oldFileName);
            String prefix= FilenameUtils.getExtension(oldFileName);//原文件后缀
            System.out.println("uploadFile prefix============> " + prefix);
            int filesize = 50000000;
            System.out.println("uploadFile size============> " + attach.getSize());
            if(attach.getSize() >  filesize){//上传大小不得超过 500k
                request.setAttribute("uploadFileError", " * 上传大小不得超过 500k");
                return "useradd";
            }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                    || prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
                String fileName = System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+"_Personal.jpg";
                System.out.println("new fileName======== " + attach.getName());
                File targetFile = new File(path, fileName);
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }
                //保存
                try {
                    attach.transferTo(targetFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("uploadFileError", " * 上传失败！");
                    return "useradd";
                }
                idPicPath = path+File.separator+fileName;
            }else{
                request.setAttribute("uploadFileError", " * 上传图片格式不正确");
                return "useradd";
            }
        }
        System.out.println("文件上传路径："+idPicPath);
        user.setIdPicPath(idPicPath);

        Integer result=userService.insertUser(user);
        if(result>0){
            return "redirect:userlist.html";
        }else{
            return "useradd";
        }
    }*/

    //新增用户，多文件上传
    @RequestMapping(value = "/useradd.html",method = RequestMethod.POST)
    public String userAdd(@Valid User user, BindingResult bindingResult,HttpServletRequest request,
                          @RequestParam(value ="attachs", required = false)MultipartFile [] attachs){
        if(bindingResult.hasErrors()){
            System.out.println("============数据校验不通过==================");
            return "useradd";
        }
        String idPicPath = null;
        String workPicPath = null;
        String errorInfo = null;
        boolean flag = true;
        String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
        System.out.println("uploadFile path ============== > "+path);
        for(int i = 0;i < attachs.length ;i++){
            MultipartFile attach = attachs[i];
            if(!attach.isEmpty()){
                if(i == 0){
                    errorInfo = "uploadFileError";
                }else if(i == 1){
                    errorInfo = "uploadWpError";
                }
                String oldFileName = attach.getOriginalFilename();//原文件名
                System.out.println("uploadFile oldFileName ============== > "+oldFileName);
                String prefix=FilenameUtils.getExtension(oldFileName);//原文件后缀
                System.out.println("uploadFile prefix============> " + prefix);
                int filesize = 500000;
                System.out.println("uploadFile size============> " + attach.getSize());
                if(attach.getSize() >  filesize){//上传大小不得超过 500k
                    request.setAttribute(errorInfo, " * 上传大小不得超过 500k");
                    flag = false;
                }else if(prefix.equalsIgnoreCase("jpg") || prefix.equalsIgnoreCase("png")
                        || prefix.equalsIgnoreCase("jpeg") || prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
                    String fileName = System.currentTimeMillis()+RandomUtils.nextInt(1000000)+"_Personal.jpg";
                    System.out.println("new fileName======== " + attach.getName());
                    File targetFile = new File(path, fileName);
                    if(!targetFile.exists()){
                        targetFile.mkdirs();
                    }
                    //保存
                    try {
                        attach.transferTo(targetFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        request.setAttribute(errorInfo, " * 上传失败！");
                        flag = false;
                    }
                    if(i == 0){
                        idPicPath = path+File.separator+fileName;
                    }else if(i == 1){
                        workPicPath = path+File.separator+fileName;
                    }
                    System.out.println("idPicPath: " + idPicPath);
                    System.out.println("workPicPath: " + workPicPath);

                }else{
                    request.setAttribute(errorInfo, " * 上传图片格式不正确");
                    flag = false;
                }
            }
        }
        System.out.println("文件上传路径："+idPicPath);
        user.setIdPicPath(idPicPath);
        user.setWorkPicPath(workPicPath);

        Integer result=userService.insertUser(user);
        if(result>0){
            return "redirect:userlist.html";
        }else{
            return "useradd";
        }
    }

    //进入修改页面
    @RequestMapping(value = "/updateUser.html",method = RequestMethod.GET)
    public String toUpdateUser(Integer id,Model model){
        //根据ID查询用户信息
        User user=userService.getUserById(id);
        model.addAttribute("user",user);
        return "usermodify";
    }

    //保存修改的数据
    @RequestMapping(value = "/updateUser.html",method = RequestMethod.POST)
    public String updateUser(User user){
        Integer result=userService.updateUser(user);
        if(result>0){
            return "redirect:/userlist.html";
        }else{
            return "redirect:/updateUser.html?id="+user.getId();
        }
    }

    //查看用户信息
 /*   @RequestMapping("/viewuser/{id}")
    public String viewUser(@PathVariable("id") Integer id, Model model){
        //根据ID查询用户信息
        User user=userService.getUserById(id);
        model.addAttribute("user",user);
        return "userview";
    }*/

    //验证userCode是否存在
    @RequestMapping("/checkUserCode")
    @ResponseBody //等同于out.print()
    public String chekcUserCode(String userCode){
        HashMap<String,Object> hashMap=new HashMap<>();
        User user=userService.getUserByCode(userCode);
        if(user==null){
            //可以使用
            hashMap.put("userCode","notexist");
        }else{
            //已存在
            hashMap.put("userCode","exist");
        }
        return JSON.toJSONString(hashMap);
    }

    @RequestMapping(value = "/userview.json")/*,produces = {"application/json;charset=utf-8"}*/
    @ResponseBody
    public Object viewUser(Integer id){
        //根据ID查询用户信息
        User user=userService.getUserById(id);
        if(user==null){
            return "nodata";
        }
       /* return JSON.toJSONString(user);*/
        return user;
    }
}
