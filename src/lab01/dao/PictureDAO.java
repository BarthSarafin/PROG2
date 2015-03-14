package lab01.dao;

import java.util.*;

import lab01.model.Picture;

public interface PictureDAO extends WritableDAO<Picture> {
    Collection<Picture>findByPosition(float longitude, float latitude, float deviation);
}
