package org.example.servlet.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeZone1 extends HttpServlet {
//    /time?timezone=Poland
//    /time?timezone=Etc/GMT
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

      //  resp.setContentType("text/html; charset=utf-8");
        HttpSession session = req.getSession(false);

        String CT = "";

        String value = req.getParameter("timezone");

        if (value == null && session == null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT"));
            CT = dateFormat.format(new Date());

        } else  { if(value != null) {
                session = req.getSession(true);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                dateFormat.setTimeZone(TimeZone.getTimeZone(value));
                CT = dateFormat.format(new Date());

                Cookie cookie = new Cookie("lestTimezone", value);
                resp.addCookie(cookie);

                 } else {

                Cookie[] cookies = req.getCookies();
                for (int i = 0; i < cookies.length; i++) {
                    if (cookies[i].getName().equals("lestTimezone")) {
                        value = cookies[i].getValue();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                        dateFormat.setTimeZone(TimeZone.getTimeZone(value));
                        CT = dateFormat.format(new Date());
                    }
                }
            }

        }

        resp.getWriter().write(CT);
        resp.getWriter().close();
    }
}