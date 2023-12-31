package edu.lingnan.luckymall.modules.customer.controller;

import edu.lingnan.luckymall.modules.customer.entity.Customer;
import edu.lingnan.luckymall.modules.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * (Customer)表控制层
 *
 * @author makejava
 * @since 2020-06-14 11:02:45
 */
@Controller
@RequestMapping("customer")
public class CustomerController {
    /**
     * 服务对象
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Customer selectOne(Integer id) {
        return this.customerService.queryById(id);
    }


    @PostMapping("insert")
    public Customer insert(Customer customer) {

        return customerService.insert(customer);
    }


    @GetMapping("toIndex")
    public String toIndex() {


        return "index";
    }

    @GetMapping("toHome")
    public String toHome() {
        System.out.println("已经进来！！");
        return "home";
    }


    @GetMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("toRegister")
    public String toRegister() {
        return "register";
    }

    @PostMapping("loginByPhone")
    public String loginByPhone(String phone, String password, Model model, HttpSession session, HttpServletRequest request) {

        Customer loginBean = customerService.loginByPhone(phone, password);
        if (loginBean == null) {
            model.addAttribute("msg", "用户名或密码错误");
            return "login";
        }
        //获得系统当前时间,赋值给loginBean
        Date date = new Date();
        loginBean.setLastLoginDate(date);
        customerService.update(loginBean);
        session.setAttribute("loginBean", loginBean);
        return "home";
    }


    @PostMapping("register")
    public String register(Customer bean, String cfmpassword, Model model,
                           String quiz1, String quiz2, String quiz3, String desc) {

        //判断两次密码是否一致
        if (bean.getName().length() < 2 || bean.getPassword().length() > 6) {
            model.addAttribute("msg", " 用户名长度不符合要求，请重新输入！");
            //System.out.println("两次输入密码不一致");
            return "register";
        }

        //判断两次密码是否一致
        if (bean.getPassword().length() < 6 || bean.getPassword().length() > 11) {
            model.addAttribute("msg", " 密码长度不符合要求，请重新输入！");
            //System.out.println("两次输入密码不一致");
            return "register";
        }


        //判断两次密码是否一致
        if (!bean.getPassword().equals(cfmpassword)) {
            model.addAttribute("msg", "两次输入密码不一致！");
            //System.out.println("两次输入密码不一致");
            return "register";
        }

        //校验地址
        if (bean.getPhone().length() != 11) {
            model.addAttribute("msg", "手机号码格式错误!");

            System.out.println("手机号码格式错误!:::" + bean.getPhone().length() + ":::" + bean.getPhone());
            return "register";
        }


        //校验地址
        if (quiz1 == null && quiz2 == null && quiz3 == null && desc == null) {
            model.addAttribute("msg", "地址格式不正确!");
            //System.out.println("地址格式不正确");
            return "register";
        }

        String address = quiz1 + quiz2 + quiz3 + desc;


        //System.out.println(customerService.getByName(bean.getName()));

        //判断用户名是否重复
        if (customerService.getByName(bean.getName()) != null) {
            model.addAttribute("msg", "该用户名已经被使用,请更改用户名！");
            //System.out.println("该用户名已经被使用");
            return "register";
        }
        //判断号码是否重复
        if (customerService.getByPhone(bean.getPhone()) != null) {
            model.addAttribute("msg", "该号码已注册，请更改手机号码！");
            //System.out.println("该号码已注册！");
            return "register";
        }

        //获得系统当前时间,赋值给bean
        Date date = new Date();
        bean.setRegDate(date);

        //赋值地址
        bean.setAddress(address);

        bean.setImage("default_user_logo.jpg");

        //赋值权限值
        bean.setSsuper(2);

        Customer result = customerService.insert(bean);

        if (result != null) {
            model.addAttribute("msg", "注册成功请登陆!");
            //System.out.println("注册成功请登陆");
            return "login";
        }

        //System.out.println("注册成功请登陆");
        model.addAttribute("msg", "注册失败，请稍后重试！");
        return "register";

    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "home";
    }

    @GetMapping("toCustomerInfo")
    public String toCustomerInfo() {
        return "pages/customer_info";
    }

    @ResponseBody
    @RequestMapping("upload")
    public int upload(MultipartFile file, HttpServletRequest request, int id, HttpSession session) throws IOException {


        //设置图片上传路径,是目标文件夹的路径


        String filePath = "E:\\idea_workspace\\luckymall\\target\\classes\\static\\upload";//target文件的路径
        //String filePath = request.getSession().getServletContext().getRealPath("/static/upload");
        //String filePath="/usr/local/static/upload";

        // 获取原始图片的扩展名
        String originalFilename = file.getOriginalFilename();

        //获取最后一个.的位置
        int lastIndexOf = originalFilename.lastIndexOf(".");

        String suffix = originalFilename.substring(lastIndexOf);


        // 生成文件新的名字
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;


        // 封装上传文件位置的全路径
        File targetFile = new File(filePath, newFileName);
        file.transferTo(targetFile);


        // 保存到数据库
        Customer customer = customerService.queryById(id);
        customer.setImage(newFileName);

        customerService.update(customer);

        session.setAttribute("loginBean", customer);

        System.out.println(this.getClass().getName() + ":::" + customer.getImage());

        return 1;

    }


}