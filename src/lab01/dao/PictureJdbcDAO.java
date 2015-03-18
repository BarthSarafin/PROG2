package lab01.dao;


import lab01.model.Picture;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
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
            String sql = "UPDATE picture SET (id,date,longitude,latitude,altitude,title,comment,url) = (?,?,?,?,?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getId());
            ps.setDate(2,new java.sql.Date(item.getDate().getTime()));
            ps.setFloat(3, item.getLongitude());
            ps.setFloat(4, item.getLatitude());
            ps.setFloat(5, item.getAltitude());
            ps.setString(6, item.getTitle());
            ps.setString(7, item.getComment());
            ps.setString(8, item.getUrl().toString());

            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("DB operation failed: update(picture): "+e.getMessage());
        }
    }

    @Override
    public void delete(Picture item) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "DELETE FROM picture WHERE title = ? AND longitude = ? AND latitude = ? and comment = ? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, item.getTitle());
            ps.setFloat(2,item.getLongitude());
            ps.setFloat(3,item.getLatitude());
            ps.setString(4,item.getUrl().toString());
            ps.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }

    @Override
    public Picture findById(int id) {
        System.out.println("find by id mit id"+id);
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM picture WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            //Der Cursor im Resultset steht am Anfange BEVOR der
            //ersten reihe
            rs.next();
            Picture newPic = null;
                 newPic = new Picture(rs.getInt(1),
                        new URL(rs.getString(8)),
                        new Date(rs.getDate(2).getTime()),
                        rs.getString(6), rs.getString(7),
                        rs.getFloat(3),
                        rs.getFloat(4),
                        rs.getFloat(5));
                System.out.println("Found "+newPic.getTitle()+" as ID "+id);

            return newPic;

        } catch (SQLException | MalformedURLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }

    @Override
    public Collection<Picture> findAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM picture ;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            Collection<Picture> pictures = new ArrayList<>();
            
            //Der Cursor im Resultset steht am Anfange BEVOR der
            //ersten reihe
            while(rs.next()) {
                Picture newPic = new Picture(rs.getInt(1),
                        new URL(rs.getString(8)),
                        new Date(rs.getDate(2).getTime()),
                        rs.getString(6), rs.getString(7),
                        rs.getFloat(3),
                        rs.getFloat(4),
                        rs.getFloat(5));
                
                pictures.add(newPic);
                
            }

            return pictures;

        } catch (SQLException | MalformedURLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }

    public int getHighestId() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM picture ORDER BY id DESC;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }
    @Override
    public int count() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT COUNT(*) FROM picture;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }

    @Override
    public Collection<Picture> findByPosition(float longitude, 
            float latitude, float deviation) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM picture wHERE longitude >= ? AND longitude <= ? AND latitude >= ? AND latitude <= ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setFloat(1,longitude-deviation);
            ps.setFloat(2,longitude+deviation);
            ps.setFloat(3,latitude-deviation);
            ps.setFloat(4,latitude+deviation);
            
            ResultSet rs = ps.executeQuery();

            Collection<Picture> pictures = new ArrayList<>();

            //Der Cursor im Resultset steht am Anfange BEVOR der
            //ersten reihe
            while(rs.next()) {
                Picture newPic = new Picture(rs.getInt(1),
                        new URL(rs.getString(8)),
                        new Date(rs.getDate(2).getTime()),
                        rs.getString(6), rs.getString(7),
                        rs.getFloat(3),
                        rs.getFloat(4),
                        rs.getFloat(5));

                pictures.add(newPic);

            }

            return pictures;

        } catch (SQLException | MalformedURLException e) {
            throw new RuntimeException("DB operation failed: Select * FROM picture", e);
        }
    }

}
