package pace.pal.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pace.pal.entity.Song;

@Data
@NoArgsConstructor
public class PacepalSong {
	private Long songId;
	private String songName;
	private String songArtist;
	private String songDuration;
	private String songGenre;
	private Long songTag;

	public PacepalSong(Song song) {
		songId = song.getSongId();
		songName = song.getSongName();
		songArtist = song.getSongArtist();
		songDuration = song.getSongDuration();
		songGenre = song.getSongGenre();
		songTag = song.getSongTag();
	}
}
