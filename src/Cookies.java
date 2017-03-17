import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class Cookies extends HttpServlet {

    Cookie[] cookies = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession(true);
        cookies = req.getCookies();



        if (login.equals("")) {
            if (pass.equals("")) {
                if (cookies!=null) {
                    login = search("LoginCookie", cookies).getValue();
                    pass = search("PassCookie", cookies).getValue();
                    out.write("<h1> Hello again, " + login + "<h1>");
                    out.write("<h1> your pass: " + pass + "<h1>");
                } else {
                    out.write(" <h1> YO!) Empty Field  <h1>");
                }
            } else {
                out.write("<h1> Something wrong <h1> ");
            }
        }else {
            if (ValidUser(login, pass) == 2) {
                out.write("<h1>Welcome " + login + "</h1>");
                Cookie LoginCookie = new Cookie("LoginCookie", req.getParameter("login"));
                Cookie PassCookie = new Cookie("PassCookie", req.getParameter("password"));

                LoginCookie.setMaxAge(60*60*24);
                PassCookie.setMaxAge(60*60*24);
                resp.addCookie(LoginCookie);
                resp.addCookie(PassCookie);

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
    public static Cookie search(String cookieName,Cookie[] cookies) {
        Cookie cookie = null;
        for (Cookie c:cookies) {
            if (c.getName().equals(cookieName)){
                cookie = c;
            }

        }
        return cookie;
    }


}
