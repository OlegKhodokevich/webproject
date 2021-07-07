package by.khodokevich.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;

@WebServlet(name = "helloServlet", value = "/hello_servlet")
public class HelloServlet extends HttpServlet {


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String numStr = request.getParameter("num");
//        int number = Integer.parseInt(numStr) + 100;
//        request.setAttribute("res", number);
//        request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
    }

    public void destroy() {
    }
}