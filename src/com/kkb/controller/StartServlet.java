package com.kkb.controller;

import com.kkb.bean.JSONMessage;
import com.kkb.util.SMSTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/start")
public class StartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2. 参数获取 姓名，手机号，城市
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String city = request.getParameter("city");
        //3. 任务启动
        boolean flag = SMSTask.start(1000 * 60 * 60 * 24, name, phoneNumber, city);
        //4. 按照接口标准，给前端回复消息
        JSONMessage msg = null;
        if (flag) {
            msg = new JSONMessage(0, "ok");
        } else {
            msg = new JSONMessage(-1, "error");
        }
        System.out.println(msg.toJSON());
        response.getWriter().append(msg.toJSON());
    }
}
