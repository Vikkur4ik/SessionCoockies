import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import static javax.xml.ws.Endpoint.create;

public class CreateNewUser extends HttpServlet{


    String path = "//C://Users//USER//IdeaProjects//SessionCoockies//SessionCoockies//user.txt";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("RegName");
        String password = req.getParameter("RegPass");
        PrintWriter out = resp.getWriter();
        if(UserExists(login)==false) {
            if (create(login, password) == 1) {
                out.write("<h1> Welcome " + login + "</h1>");
                out.write("<h1>User created</h1>");
                out.write("<h1> You password " + password + "</h1>");
            } else {
                out.write("Something wrong. Sorry");
            }
        }else {
            out.write("User exists");
        }
    }

    public boolean UserExists(String regName){

        boolean check = false;

        String[] keys;
        String string;
        Map<String, String> users = new TreeMap<>();


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
        if (users.containsKey(regName)){
            check=true;
        }

        return check;

    }

    public int create(String regName, String regPass) {

        int status=0;

        try {
            FileWriter writer = new FileWriter(path, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write("\r\n" + regName+" "+regPass);
            bufferWriter.close();
            status=1;
        }
        catch (IOException e) {
            e.printStackTrace();

        }

        return status;

    }
}
