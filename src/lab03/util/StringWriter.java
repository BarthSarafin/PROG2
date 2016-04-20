package lab03.util;

import lab03.model.Picture;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Stefan R. Bachmann on  06/04/2016
 * @version v0.1 - lab03.util
 */
public class StringWriter {

	public Picture getPicFromString(String line) {
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
