package lab01.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import lab01.model.Picture;

public class PictureFileDAO implements PictureDAO {
    private File dataSource;

    public PictureFileDAO(File dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Picture item) {
        // TODO Implement method
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void update(Picture item) {
        // TODO Implement method
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void delete(Picture item) {
        // TODO Implement method
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Picture findById(int id) {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader(dataSource));
            String line;
            do {
                line = br.readLine();
                System.out.println(line);
                Picture newPic = getPicFromString(line);
                if(newPic.getId() == id){
                    return newPic;
                }
            } while (br.ready());
        }catch (IOException e) {
            System.out.println("Exception "+e);
            return null;
        }
        System.out.println("File Closed");
        return null;
    }

    @Override
    public Collection<Picture> findAll() {
        // TODO Implement method
        return null;
    }

    @Override
    public int count() {
        BufferedReader br;
        int count = 0;

        try {
            br = new BufferedReader(new FileReader(dataSource));
            do {
                count++;
            } while (br.ready());
        } catch (IOException e) {
            System.out.println("Exception "+e);
            return 0;
        }
        System.out.println("File Closed");
        return count;
    }

    @Override
    public Collection<Picture> findByPosition(float longitude, float latitude,
            float deviation) {
        BufferedReader br;
        ArrayList<Picture> pictures = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(dataSource));
            String line;
            do {
                line = br.readLine();
                System.out.println(line);
                Picture newPic = getPicFromString(line);
                if(newPic.getLatitude() >= latitude - deviation && newPic.getLatitude() <= latitude + deviation && newPic.getLongitude() >= longitude - deviation && newPic.getLongitude() <= longitude - deviation){
                    pictures.add(newPic);
                }
            } while (br.ready());
        }catch (IOException e) {
            System.out.println("Exception "+e);
            return null;
        }
        System.out.println("File Closed");
        return pictures;
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

}
