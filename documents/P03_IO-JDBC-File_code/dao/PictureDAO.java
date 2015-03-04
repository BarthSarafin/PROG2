package dao;

import java.util.*;

import model.Picture;

public interface PictureDAO extends WritableDAO<Picture> {
    Collection<Picture>findByPosition(float longitude, float latitude, float deviation);
}
