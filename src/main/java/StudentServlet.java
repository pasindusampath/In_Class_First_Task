import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentServlet extends HttpServlet {
    Connection con=null;
    @Override
    public void init() throws ServletException {
        try {
            Class.forName(getServletContext().getInitParameter("mysql-driver"));
            con = DriverManager.getConnection(getServletContext().getInitParameter("mysql-url"),
                    getServletContext().getInitParameter("user-name"),
                    getServletContext().getInitParameter("password"));

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");

        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO student VALUES(?,?)");
            ps.setString(1,id);
            ps.setString(2,name);
            int i = ps.executeUpdate();
            if(i>0){
                resp.setStatus(200);
            }else {
                resp.setStatus(HttpServletResponse.SC_REQUEST_TIMEOUT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }

    }
}
