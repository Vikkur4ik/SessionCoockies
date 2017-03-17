import com.sun.corba.se.impl.presentation.rmi.DynamicMethodMarshallerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class LogIn extends HttpServlet {



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String pass = req.getParameter("password");


        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(true);

        if (!session.isNew()) {
            if (login.equals("")) {
                if (pass.equals("")) {
                        out.write("<h1> Hello again, " + session.getAttribute("login") + "<h1>");
                        out.write("<h1> your pass: " + session.getAttribute("password") + "<h1>");
                        //out.write("<h1>"+ session.getAttribute("check")+"</h1>" );
                } else {
                    out.write("<h1> Incorrect input! <h1> ");
                }
            } else {
                if (ValidUser(login, pass) == 2) {
                    out.write("<h1>Welcome " + login + "</h1>");
                    session.setAttribute("login", req.getParameter("login"));
                    session.setAttribute("password", req.getParameter("password"));
                }
                if (ValidUser(login, pass) == 1) {
                    out.write("Invalid password. Pls try again");
                }
                if (ValidUser(login, pass) == 0) {
                    resp.sendRedirect("Registration.html");
                }
            }
        }else{
            if (ValidUser(login, pass) == 2) {
                out.write("<h1>Welcome " + login + "</h1>");
            }
            if (ValidUser(login, pass) == 1) {
                out.write("Invalid password. Pls try again");
            }
            if (ValidUser(login, pass) == 0) {
                resp.sendRedirect("Registration.html");
            }
        }

    }



    public int ValidUser(String login, String pass){

        int check = 0;
        String[] keys;
        String string;
        Map<String, String> users = new TreeMap<>();

        String path = "//C://Users//USER//IdeaProjects//SessionCoockies//SessionCoockies//user.txt";
        File file = null;
        try {
            file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                string = scanner.nextLine();
                keys = string.split(" ");
                int i = 0;
                String value = (keys[i + 1]);
                users.put(keys[i], value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (users.containsKey(login)){
            if (users.get(login).equals(pass)){
                check = 2;
            }else{
                check = 1;
            }
        }else{
            check = 0;
        }

        return check;


    }


}


