package lab01.dao;

import java.io.File;
import java.util.Collection;

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
        // TODO Implement method
        return null;
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
    public Collection<Picture> findByPosition(float longitude, float latitude,
            float deviation) {
        // TODO Implement method
        return null;
    }

}
