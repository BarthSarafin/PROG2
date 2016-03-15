package lab03.dao;

import java.util.*;

import lab03.model.Picture;

public interface PictureDAO extends WritableDAO<Picture> {
    Collection<Picture>findByPosition(float longitude, float latitude, float deviation);
}
