package com.itheima.web.servlet;

import com.itheima.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/examServlet")
public class ExamServlet extends HttpServlet {
    private UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        username=new String(username.getBytes("ISO-8859-1"),"UTF-8");
        System.out.println(username);
        boolean f=service.selectUser(username);
        //回应内容自己定，让前端知道就行。
        response.getWriter().write("havenUser");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
