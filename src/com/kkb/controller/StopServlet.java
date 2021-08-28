package com.kkb.controller;

import com.kkb.bean.JSONMessage;
import com.kkb.bean.Message;
import com.kkb.util.SMSTask;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/stop")
public class StopServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("停止之前：" + Message.getInstance());
        SMSTask.end();
        System.out.println("停止之后：" + Message.getInstance());
        //引用地址不一样，停止成功
        JSONMessage json = new JSONMessage(0, "ok");
        response.getWriter().append(json.toJSON());
    }
}
