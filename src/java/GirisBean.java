
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;

@ManagedBean(name = "GirisBean")
@SessionScoped

public class GirisBean {

    String email;
    String sifre;

    public String girisYap() throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/EBiletDB", "admin", "admin");

        if (connection == null) {
            throw new SQLException("Unable to connect to DataSource");
        }

        try {
            PreparedStatement getEntry = connection.prepareStatement(
                    "SELECT * FROM CUSTOMER "
                    + "WHERE USERNAME = ? AND PASSWORD = ?");

            getEntry.setString(1, getEmail());
            getEntry.setString(2, getSifre());

            ResultSet rs = getEntry.executeQuery();

            if (rs.next()) { // login başarılı
                return "index.xhtml?faces-redirect=true";
            } else {
                return "sss.xhtml?faces-redirect=true";
            }

        } finally {
            connection.close();
        }
    }

    public String getEmail() {
        return email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String response() throws ClassNotFoundException, SQLException {
        if (email != null && sifre != null) {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/EBiletDB", "admin", "admin");

            if (connection == null) {
                throw new SQLException("Unable to connect to DataSource");
            }

            try {
                PreparedStatement getEntry = connection.prepareStatement(
                        "SELECT * FROM CUSTOMER "
                        + "WHERE USERNAME = ? AND PASSWORD = ?");

                getEntry.setString(1, getEmail());
                getEntry.setString(2, getSifre());

                ResultSet rs = getEntry.executeQuery();

                if (rs == null) {
                    return "<a href=\"/EBilet/faces/login.xhtml\"><button class=\"form-inline my-2 my-lg-0 btn btn-primary\" type=\"button\" aria-expanded=\"false\" style=\"background-color:#2e4c6d\">Giriş Yap</button></a>";
                }
                
                rs.next();
                return "Hoşgeldin, " + rs.getString(2);

            } finally {
                connection.close();
            }

        } else {
            return "<a href=\"/EBilet/faces/login.xhtml\"><button class=\"form-inline my-2 my-lg-0 btn btn-primary\" type=\"button\" aria-expanded=\"false\" style=\"background-color:#2e4c6d\">Giriş Yap</button></a>";
        }
    }
}
