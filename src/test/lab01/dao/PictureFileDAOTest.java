package lab01.dao;

import lab01.model.Picture;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PictureFileDAOTest {

    private PictureFileDAO dao ;
    private int currentPictureCount = 0;
    private float longitude = 1000.5f;
    private float latitude = 1000.5f;
    private int testId = 222;
    private Picture testPic;

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