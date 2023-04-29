package com.itheima.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/*
替换HTTPServlet。根据请求的最后一段路径进行方法分发
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求路径
        String uri=req.getRequestURI();// /brand-case/brand/selectAll
        //获取路径最后一段值
        int index=uri.lastIndexOf('/');
        String methodName=uri.substring(index+1);//selectAll
        //2．执行方法
        //2.1 获取BrandServlet /UserServlet字节码对象Class/谁调用我(this 所在的方法)，我(this)代表谁
        //system.out.println(this); // BrandServlet? HttpServlet? BrandServlet !
        Class<? extends BaseServlet> cls = this.getClass();

        //获取方法Method对象
        try {
            Method method=cls.getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            //2.3执行方法
            method.invoke(this,req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
