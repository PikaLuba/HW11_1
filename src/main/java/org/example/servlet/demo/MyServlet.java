package org.example.servlet.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.servlet.app.config.TemplateConfig;

import org.thymeleaf.context.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(name = "MyServlet", urlPatterns = {"/", "/storeUser"})
public class MyServlet extends HttpServlet {
    private TemplateConfig templateConfig;

    @Override
    public void init() throws ServletException {
        templateConfig = new TemplateConfig();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT-3"));
        String CT = dateFormat.format(new Date());

        HttpSession session = req.getSession(false);
        String curentdate = (session != null) ? (String) session.getAttribute("curentdate") : CT;

        Context context = new Context();
        context.setVariable("curentdate", curentdate);
        templateConfig.process("index", context, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String curentdate = req.getParameter("curentdate");

        if (curentdate != null && !curentdate.trim().isEmpty()) {
            req.getSession().setAttribute("curentdate", curentdate);
        }

        resp.sendRedirect("/");
    }
}

