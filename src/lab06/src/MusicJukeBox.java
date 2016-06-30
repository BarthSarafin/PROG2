import java.util.ArrayList;
import java.util.List;

public class MusicJukeBox implements JukeBox {
	private java.util.Hashtable<String, Song> playlist = new java.util.Hashtable<String, Song>();
	private Song actualSong;

	public Song getActualSong() {
		return actualSong;
	}

	public void addSong(Song song) {
		playlist.put(song.getTitle(), song);
	}

	public void playTitle(String songTitle) throws JukeBoxException {
		if (playlist.containsKey(songTitle)) {
			actualSong = playlist.get(songTitle);
			actualSong.start();
		} else {
			throw new JukeBoxException("No song found with title '" + songTitle
					+ "'");
		}
	}

	public List<Song> getPlayList() {
		ArrayList<Song> list = new ArrayList<Song>();
		for (Song song : playlist.values()) {
			list.add(song);
		}
		return list;
	}
}
