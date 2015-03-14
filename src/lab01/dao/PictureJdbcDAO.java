package lab01.dao;


import lab01.model.Picture;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Collection;


public class PictureJdbcDAO implements PictureDAO {
    private DataSource dataSource;

    public PictureJdbcDAO(DataSource dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Picture picture) {
        // you can get a connection to the DB from the dataSource using ds.getConnection()
        try (Connection conn = dataSource.getConnection()) {

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

    @Override
    public void update(Picture item) {
        try (Connection conn = dataSource.getConnection()) {

        } catch (SQLException e){
            System.out.println("DB operation failed: update(picture): "+e.getMessage());
        }
    }

    @Override
    public void delete(Picture item) {
        // TODO Implement method
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Picture findById(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM picture WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            //Der Cursor im Resultset steht am Anfange BEVOR der
            //ersten reihe
            rs.next();


            Picture newPic = new Picture(rs.getInt(1),
                    new URL(rs.getString(8)),
                    new Date(rs.getDate(2).getTime()),
                    rs.getString(6), rs.getString(7),
                    rs.getFloat(3),
                    rs.getFloat(4),
                    rs.getFloat(5));

            return newPic;

        } catch (SQLException | MalformedURLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }

    @Override
    public Collection<Picture> findAll() {
        // TODO Implement method
        return null;
    }

    @Override
    public int count() {
        // TODO Implement method
        return 0;
    }

    @Override
    public Collection<Picture> findByPosition(float longitude, 
            float latitude, float deviation) {
        // TODO Implement method
        return null;
    }

}
