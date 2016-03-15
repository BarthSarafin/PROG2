package lab03.dao;

import lab03.model.Picture;
import lab03.util.SimpleDataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PictureJdbcDAOTest {

    private PictureJdbcDAO dao ;
    private int currentPictureCount = 0;
    private float longitude = 1300.5f;
    private float latitude = 1020.5f;
    private int testId = 222;
    private Picture testPic;



   @Before
   public void createDAO () {
       SimpleDataSource sds = new SimpleDataSource("jdbc:postgresql://dublin.zhaw.ch/camenfa1","camenfa1","root");
       dao = new PictureJdbcDAO(sds);

       currentPictureCount = dao.count();

       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = new Date(); // now
       try {
           date = df.parse("2015-02-01 19:29:22");
       } catch (ParseException e){}
       try {
           testPic= new Picture(testId,
                   new URL("http://this.is.a.url.com"),
                   date,
                   "TestTitle", "Comment",
                   longitude,
                   latitude,
                   0f);
       } catch (MalformedURLException e) {
           e.printStackTrace();
       }
       System.out.println("Anzahl Bilder in der TAbelle:" + currentPictureCount);
   }


    @Test
    public void testInsert() throws Exception {


       dao.insert(testPic);
        Picture pic = dao.findById(dao.getHighestId());
        Assert.assertEquals("TestTitle", pic.getTitle());
        dao.delete(testPic);
    }

    @Test
    public void testUpdate() throws Exception {

        dao.insert(testPic);
        testPic.setTitle("UpdateTitle");
        dao.update(testPic);
        Assert.assertEquals("UpdateTitle", dao.findById(dao.getHighestId()).getTitle());
        testPic.setTitle("TestTitle");
        dao.update(testPic);
        Assert.assertEquals("TestTitle", dao.findById(testId).getTitle());
        dao.delete(testPic);

    }


    @Test
    public void testFindById() throws Exception {
        Picture readPic = dao.findById(dao.getHighestId());
        Assert.assertEquals(dao.getHighestId(), readPic.getId());
        Assert.assertEquals("Comment", readPic.getComment());
        Assert.assertEquals("TestTitle", readPic.getTitle());
        Assert.assertEquals(longitude, readPic.getLongitude(), 0.5f);

    }

    @Test
    public void testFindAll() throws Exception {

        ArrayList<Picture> pictures = (ArrayList<Picture>) dao.findAll();
        for(Picture pic : pictures){
            System.out.println(pic.getTitle());
        }

        Assert.assertEquals(currentPictureCount , pictures.size());
    }

    @Test
    public void testCount() throws Exception {

        Assert.assertEquals(currentPictureCount , dao.count());
    }

    @Test
    public void testFindByPosition() throws Exception {
        ArrayList<Picture> pictures = (ArrayList<Picture>) dao.findByPosition(longitude,latitude,2f);

        dao.insert(testPic);
        Assert.assertEquals(1, pictures.size());
        dao.delete(testPic);

    }
    @Test
    public void testDelete() throws Exception {

        dao.insert(testPic);
        dao.delete(testPic);
        Assert.assertEquals(currentPictureCount, dao.count());

    }
}