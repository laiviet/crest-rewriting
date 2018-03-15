package rewriting;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "ApiRewriteServlet", urlPatterns = "/api")
public class ApiRewriteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        File dataDir = new File(request.getSession().getServletContext().getRealPath("/data"));
        File files[] = dataDir.listFiles();

        String id = (String) request.getParameter("id");

        if (id == null) {
            System.out.println("Null id");
            return;
        }
        String name = id + ".txt";

        System.out.println(name);

        Article a=null;
        for (File f : files) {
            if (f.getName().compareTo(name) == 0) {
                a = Utils.readArticle(f.getPath());
                break;
            }
        }
        //System.out.println("AAAAAAAAA");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        if (a == null) {
            //System.out.println(Article.emptyJsonObject());
            response.getWriter().write(Article.emptyJsonObject());
        } else {
            //System.out.println(a.toJson());
            response.getWriter().write(a.toJson());
        }

    }
}
