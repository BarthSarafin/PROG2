package lab03.dao;

import lab03.model.Picture;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PictureFileDAOTest {

    private PictureFileDAO dao ;
    private int currentPictureCount = 0;
    private float longitude = 1000.5f;
    private float latitude = 1000.5f;
    private int testId = 222;
    private Picture testPic;

    @Before
    public void createDAO () {
        File file = new File("data.csv");
        dao = new PictureFileDAO(file);

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
    public void testFindById() throws Exception {
        Picture readPic = dao.findById(testId);
        Assert.assertEquals(testId, readPic.getId());
        Assert.assertEquals("Comment", readPic.getComment());
        Assert.assertEquals("TestTitle", readPic.getTitle());
        Assert.assertEquals(longitude, readPic.getLongitude(), 0.5f);
    }

    @Test
    public void testCount() throws Exception {
        Assert.assertEquals(currentPictureCount, dao.count());
    }

    @Test
    public void testFindByPosition() throws Exception {

        ArrayList<Picture> pictures = (ArrayList<Picture>) dao.findByPosition(longitude,latitude,2f);
        Assert.assertEquals(1, pictures.size());
    }
}