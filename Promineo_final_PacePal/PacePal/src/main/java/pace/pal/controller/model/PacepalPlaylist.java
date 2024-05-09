package pace.pal.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pace.pal.entity.Playlist;
import pace.pal.entity.Song;
import pace.pal.entity.Tag;

@Data
@NoArgsConstructor
public class PacepalPlaylist {
	private Long playlistId;
	private String playlistName;

	private Set<PacepalTag> tags = new HashSet<>();
	private Set<PacepalSong> songs = new HashSet<>();

	public PacepalPlaylist(Playlist PlayList) {
		playlistId = PlayList.getPlaylistId();
		playlistName = PlayList.getPlaylistName();

		for (Tag tag : PlayList.getTags()) {
			tags.add(new PacepalTag(tag));
		}

		for (Song song : PlayList.getSongs()) {
			songs.add(new PacepalSong(song));
		}
	}
}