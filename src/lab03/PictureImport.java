package lab03;

import java.sql.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.sql.DataSource;

import lab03.model.Picture;
import lab03.util.SimpleDataSource;

/* This test-application reads some picture data from terminal, 
 * saves it to the DB, read it from the DB and prints the result
 */

public class PictureImport {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static PrintWriter out = new PrintWriter(System.out, true);
    private DataSource ds;

    public static void main(String[] args) {
        String dbUser = prompt("Username: ");
        String dbPasswd = prompt("Password: ");
        String dbURL = "jdbc:postgresql://dublin.zhaw.ch/" + dbUser;
        PictureImport importer = new PictureImport(dbURL, dbUser, dbPasswd);
        /*Picture pict = importer.createPicture();
        importer.addPicture(pict);
        Picture pict2 = importer.getPicture(pict.getId());
        if (pict2 != null) {
            out.println("The following pictures has been saved: ");
            out.println(pict2);
        } else {
            out.println("Picture with id=" + pict.getId() + " not found.");
        }*/
        /* Exercise 1.b) */
         String fileName = prompt("File: ");
        File file = new File(fileName);
         importer.importFile(file);
    }

    PictureImport(String dbUrl, String dbUser, String dbPasswd) {
        this.ds = new SimpleDataSource(dbUrl, dbUser, dbPasswd);
    }

    public Picture createPicture() {
        // asks the values for the objects
        out.println("** Create a new picture **");
        String urlString = prompt("Picture URL: ");
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        String title = prompt("Picture title: ");
        String comment = prompt("Picture comment: ");


        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date(); // now
        try {
            date = df.parse(prompt("Picture time (" + DATE_FORMAT + "): "));
        } catch (ParseException e) {
            out.println("Unknown date format. Using " + date.toString());
        }

        float longitude = 0.0f;
        try {
            longitude = Float.parseFloat(prompt("Picture position longitude: "));
        } catch (NumberFormatException e) {
            out.println("Unknown number format. Using " + longitude);
        }
        float latitude = 0.0f;
        try {
            latitude = Float.parseFloat(prompt("Picture position latitude: "));
        } catch (NumberFormatException e) {
            out.println("Unknown number format. Using " + latitude);
        }
        float altitude = 0.0f;
        try {
            altitude = Float.parseFloat(prompt("Picture position altitude: "));
        } catch (NumberFormatException e) {
            out.println("Unknown number format. Using " + altitude);
        }
        return new Picture(url, new Date(), title, comment, longitude, latitude, altitude);
    }

    public void addPicture(Picture picture) {
        // you can get a connection to the DB from the dataSource using ds.getConnection()
        try (Connection conn = ds.getConnection()) {

            String sql = "INSERT INTO picture (date,longitude,latitude,altitude,title,comment,url) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new java.sql.Date(picture.getDate().getTime())); // mit ps.setDate muss ein java.sql.date verwendet werden.
            ps.setFloat(2, picture.getLongitude());
            ps.setFloat(3, picture.getLatitude());
            ps.setFloat(4, picture.getAltitude());
            ps.setString(5, picture.getTitle());
            ps.setString(6, picture.getComment());
            ps.setString(7, picture.getUrl().toString());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Couldn't create Picture entry");
            }
            ResultSet newKeys = ps.getGeneratedKeys();
            if (newKeys.next()) {
                picture.setId(newKeys.getInt(1));
            } else {
                throw new SQLException("there was no entry");
            }
        } catch (SQLException e) {
            throw new RuntimeException("DB operation failed: insert(picture): " + picture, e);
        }
    }

    public Picture getPicture(int id) {

        try (Connection conn = ds.getConnection()) {
            String sql = "SELECT * FROM picture WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            //Der Cursor im Resultset steht am Anfange BEVOR der
            //ersten reihe
            rs.next();


            Picture newPic = new Picture(rs.getInt(1), new URL(rs.getString(8)), new Date(rs.getDate(2).getTime()), rs.getString(6), rs.getString(7), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5));

            return newPic;

        } catch (SQLException | MalformedURLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }

    public void importFile(File file) {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            do {
                line = br.readLine();
                System.out.println(line);
                Picture newPic = getPicFromString(line);
                addPicture(newPic);
            } while (br.ready());
        }catch (IOException e) {
            System.out.println("Exception "+e);
        }
        System.out.println("File Closed");
    }

    private Picture getPicFromString(String line) {
        String[] fragments = line.split(";");
        SimpleDateFormat datumUndUhrzeit = new
                 SimpleDateFormat("YYYY-MM-DD HH:MM:SS");

        Picture newPicture;
        try {

            Date date = datumUndUhrzeit.parse(fragments[0]);
            newPicture = new Picture(new URL(fragments[6]),
                                                date,
                                                fragments[4],
                                                fragments[5],
                                                Float.valueOf(fragments[1]),
                                                Float.valueOf(fragments[2]),
                                                Float.valueOf(fragments[3]));
            return newPicture;
        } catch (MalformedURLException |ParseException e) {
            e.printStackTrace();
            return null;
        }
        //public Picture(URL url, Date date, String title, String comment,
        //float longitude, float latitude, float altitude) {
    }

    // prompt function -- to read input string
    static String prompt(String prompt) {
        try {
            StringBuffer buffer = new StringBuffer();
            System.out.print(prompt);
            System.out.flush();
            InputStreamReader in = new InputStreamReader(System.in);
            int c = in.read();
            while (c != '\n' && c != -1) {
                buffer.append((char) c);
                c = in.read();
            }
            return buffer.toString().trim();
        } catch (IOException e) {
            return "";
        }
    }
}

