package com.itheima.web.servlet;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.BrandService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet {

        private  BrandService service = new BrandService();
        public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
            //1. 调用BrandService完成查询
            List<Brand> brands = service.selectAll();
            //2. 将集合转换为JSON数据   序列化
            String jsonString = JSON.toJSONString(brands);
            System.out.println(jsonString);

            //3. 响应数据
            resp.setContentType("text/json;charset=utf-8");
            resp.getWriter().write(jsonString);
        }
        public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
            //处理POST请求的乱码问题
            req.setCharacterEncoding("utf-8");

            //1. 接收表品牌数据
            BufferedReader br=req.getReader();
            String params=br.readLine();
            System.out.println("填的是："+params);
            //转为Brand对象
            Brand brand=JSON.parseObject(params,Brand.class);
            String brandName=brand.getBrandName();
            String compangName=brand.getCompanyName();

            if(brand.getBrandName()==null||brand.getCompanyName()==null||brandName.trim().length()==0||compangName.trim().length()==0){
                //3. 响应失败的标识
                resp.getWriter().write("addBrandFailed");
                return;
            }

            //2. 调用service 完成添加
            service.add(brand);

            //3. 响应成功的标识
            resp.getWriter().write("addBrandSuccess");
        }
    public void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
            //1．接收数据[1,2,3]
            BufferedReader br = req.getReader();
            String params = br.readLine();//json字符串
            //转为int[]
            int[] ids = JSON.parseObject(params,int[ ].class) ;
            //2．调用service删除
            service.deleteByIds(ids);
            //3．响应成功的标识
            resp.getWriter().write("deleteSuccess");
    }
    public void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //1．接收 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=req.getParameter("currentPage");
        String _pageSiez=req.getParameter("pageSize");
        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSiez);

        PageBean<Brand> pageBean=service.selectByPage(currentPage,pageSize);
        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        System.out.println(jsonString);
        //3. 响应数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
    public void selectByPageAndCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //1．接收 当前页码和每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage=req.getParameter("currentPage");
        String _pageSiez=req.getParameter("pageSize");

        int currentPage=Integer.parseInt(_currentPage);
        int pageSize=Integer.parseInt(_pageSiez);

        //获取查询条件对象
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串

        //转为Brand
        Brand brand= JSON.parseObject(params,Brand.class) ;
        System.out.println(brand.toString());
        PageBean<Brand> pageBean=service.selectByPageAndCondition(currentPage,pageSize,brand);

        //2. 将集合转换为JSON数据   序列化
        String jsonString = JSON.toJSONString(pageBean);
        System.out.println(jsonString);
        //3. 响应数据
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }
    public void updata(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //处理POST请求的乱码问题
        req.setCharacterEncoding("utf-8");

        //1. 接收表品牌数据
        BufferedReader br=req.getReader();
        String params=br.readLine();
        System.out.println(params);
        //转为Brand对象
        Brand brand=JSON.parseObject(params,Brand.class);
        if(brand.getBrandName()==null||brand.getCompanyName()==null){
            //3. 响应失败的标识
            resp.getWriter().write("updataBrandFailed");
            return;
        }

        //2. 调用service 完成添加
        service.update(brand);

        //3. 响应成功的标识
        resp.getWriter().write("updataBrandSuccess");
    }
}