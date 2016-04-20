package lab03.dao;

import lab03.model.Picture;
import lab03.util.StringWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PictureFileDAO implements PictureDAO {
    private File dataSource;
	private lab03.util.StringWriter stringWriter = new StringWriter();
	private static int id = 3;

	List<Picture> pictures = new ArrayList<>();

    public PictureFileDAO(File dataSource) {
        super();
        this.dataSource = dataSource;
    }

    @Override
    public void insert(Picture item) { // für ID wäre auch UUID möglich. Aber dann müssten alle types geändert werden or item.setId(item.hashCode());
        // TODO Implement method
		item.setId(id++);
		pictures.add(item);
		writeToFile();
    }

    @Override
    public void update(Picture item) {
        // TODO Implement method
        List<Picture> updatedFile = findAll();
		for (Picture pictureToCheck : updatedFile) {
			if (item.getId() == pictureToCheck.getId()) {
				pictureToCheck.setComment(item.getComment());
				pictureToCheck.setAltitude(item.getAltitude());
				pictureToCheck.setTitle(item.getTitle());
				pictureToCheck.setLatitude(item.getLatitude());
				pictureToCheck.setLongitude(item.getLongitude());
				pictureToCheck.setUrl(item.getUrl());
			}
		}
		pictures = updatedFile;
		writeToFile();
    }

    @Override
    public void delete(Picture item) {
        // TODO Implement method
        List<Picture> updatedFile = findAll();
		for(Picture pictureToCheck : updatedFile){
			if(item.getId() == pictureToCheck.getId()){
				updatedFile.remove(pictureToCheck);
			}
		}
		pictures = updatedFile;
		writeToFile();
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
                Picture newPic = stringWriter.getPicFromString(line);
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
    public List<Picture> findAll() {
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(dataSource));
			String line;
			do {
				line = br.readLine();
				System.out.println(line);
				Picture newPic = stringWriter.getPicFromString(line);
				pictures.add(newPic);
			} while (br.ready());
		}catch (IOException e) {
			System.out.println("Exception "+e);
			return null;
		}
		System.out.println("File Closed");
		return pictures;
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
        }
        System.out.println("File Closed");
        return count;
    }

    @Override
    public Collection<Picture> findByPosition(float longitude, float latitude,
            float deviation) {
        BufferedReader br;
        List<Picture> pictures = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(dataSource));
            String line;
            do {
                line = br.readLine();
                System.out.println(line);
                Picture newPic = stringWriter.getPicFromString(line);
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

	private void writeToFile(){
		Writer writer = null;
		String fileName = "data.csv";

		try {
			writer = new FileWriter(fileName);

			for (Picture picture : pictures) {
				writer.append(picture.toStringWithComma());
			}
			System.out.println("CSV file was created successfully !!!");
		} catch (IOException e) {
			System.out.println("Error while flushing/closing fileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}

	}

}
