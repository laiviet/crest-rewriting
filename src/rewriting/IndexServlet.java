package rewriting;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


@WebServlet(name = "IndexServlet", urlPatterns = "")
public class IndexServlet extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        File dataDir = new File(request.getSession().getServletContext().getRealPath("/data"));
        File files[] = dataDir.listFiles();

        ArrayList<String> ids = new ArrayList<>();

        // System.out.println("| Available AMR doc: "+ files.length);
        for (File f : files) {
            int length = f.getName().length();
            ids.add(f.getName().substring(0, length - 4));
        }

        Collections.sort(ids);

        request.setAttribute("ids", ids);

        RequestDispatcher view = getServletContext().getRequestDispatcher("/index.jsp");
        view.forward(request,response);
    }
}
