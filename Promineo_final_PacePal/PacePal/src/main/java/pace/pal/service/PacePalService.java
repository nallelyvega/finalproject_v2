package pace.pal.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pace.pal.controller.model.PacepalPlaylist;
import pace.pal.controller.model.PacepalSong;
import pace.pal.controller.model.PacepalTag;
import pace.pal.dao.PlaylistDao;
import pace.pal.dao.SongDao;
import pace.pal.dao.TagDao;
import pace.pal.entity.Playlist;
import pace.pal.entity.Song;
import pace.pal.entity.Tag;

@Service
public class PacePalService {

	@Autowired
	private PlaylistDao playlistDao;

	public List<PacepalPlaylist> retrieveAllPlaylists() {
		List<Playlist> playlists = playlistDao.findAll();
		return playlists.stream().map(this::convertTopacepalPlaylist).collect(Collectors.toList());
	}

	private PacepalPlaylist convertTopacepalPlaylist(Playlist playlist) {
		PacepalPlaylist playlistData = new PacepalPlaylist();
		playlistData.setPlaylistId(playlist.getPlaylistId());
		playlistData.setPlaylistName(playlist.getPlaylistName());
		return playlistData;
	}

	public PacepalPlaylist savePlaylist(PacepalPlaylist playlistData) {
		Playlist playlist = findOrCreatePlaylist(playlistData.getPlaylistId());

		copyPlaylistFields(playlist, playlistData);

		Playlist savedPlaylist = playlistDao.save(playlist);
		return new PacepalPlaylist(savedPlaylist);
	}

	private Playlist findOrCreatePlaylist(Long playlistId) {
		if (playlistId == null) {
			return new Playlist();
		} else {
			return findPlaylistById(playlistId);

		}
	}

	private Playlist findPlaylistById(Long playlistId) {
		return playlistDao.findById(playlistId)
				.orElseThrow(() -> new NoSuchElementException("Playlist with ID not found"));
	}

	private void copyPlaylistFields(Playlist playlist, PacepalPlaylist playlistData) {
		playlist.setPlaylistId(playlistData.getPlaylistId());
		playlist.setPlaylistName(playlistData.getPlaylistName());
	}

	@Autowired
	SongDao songDao;

	@Transactional(readOnly = false)

	public Song findSongById(Long playlistId, Long songId) {
		Song song = songDao.findById(songId).orElseThrow(NoSuchElementException::new);
		if (!song.getPlayLists().getPlaylistId().equals(playlistId)) {
			throw new IllegalArgumentException(
					"Song with ID " + songId + " does not belong to the playlist with ID " + playlistId);
		}
		return song;
	}

	public Song findOrCreateSong(Long SongId, Long songId) {
		if (songId == null) {
			return new Song();
		} else {
			return findSongById(SongId, songId);
		}
	}

	public void copySongFields(Song song, PacepalSong pacepalSong) {
		pacepalSong.setSongId(song.getSongId());
		pacepalSong.setSongName(song.getSongName());
		pacepalSong.setSongArtist(song.getSongArtist());
		pacepalSong.setSongDuration(song.getSongDuration());
		pacepalSong.setSongGenre(song.getSongGenre());
		pacepalSong.setSongTag(song.getSongTag());
	}

	public PacepalSong saveSong(Long SongId, PacepalSong playlistSong) {
		Playlist playlist = findOrCreatePlaylist(SongId);
		Song song = findOrCreateSong(SongId, playlistSong.getSongId());
		copySongFields(song, playlistSong);

		song.setPlayLists(playlist);
		playlist.getSongs().add(song);

		Song savedSong = songDao.save(song);

		return convertToPacepalSong(savedSong);
	}

	public PacepalSong convertToPacepalSong(Song song) {
		PacepalSong pacepalSong = new PacepalSong();
		pacepalSong.setSongId(song.getSongId());
		pacepalSong.setSongName(song.getSongName());
		pacepalSong.setSongArtist(song.getSongName());
		pacepalSong.setSongDuration(song.getSongDuration());
		pacepalSong.setSongGenre(song.getSongGenre());
		pacepalSong.setSongTag(song.getSongTag());
		return pacepalSong;
	}

	@Autowired
	TagDao tagDao;

	@Transactional(readOnly = false)

	public Tag findTagById(Long TagId, Long tagId) {
		Tag tag = tagDao.findById(tagId).orElseThrow(NoSuchElementException::new);

		boolean found = false;
		for (Playlist playlist : tag.getPlayLists()) {
			if (playlist.getPlaylistId() == tagId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"Tag with Id = " + tagId + " not a member of this playlist Id = " + tag.getPlaylistId());
		}
		return tag;

	}

	public Tag findOrCreateTag(Long songId, Long tagId) {
		if (tagId == null) {
			return new Tag();
		} else {
			return findTagById(songId, tagId);
		}
	}

	public void copyTagFields(Tag tag, PacepalTag playlistTag) {
		tag.setTagName(playlistTag.getTagName());
	}

	public PacepalTag saveTag(Long tagId, PacepalTag playlistTag) {
		Playlist playlist = findOrCreatePlaylist(tagId);
		Tag tag = findOrCreateTag(tagId, playlistTag.getTagId());
		copyTagFields(tag, playlistTag);
		playlist.getTags().add(tag);
		tag.getPlayLists().add(playlist);

		Tag savedTag = tagDao.save(tag);

		return convertToPacepalTag(savedTag);

		// return new PacepalTag(savedTag);
	}

	public PacepalTag convertToPacepalTag(Tag tag) {
		PacepalTag playlistTag = new PacepalTag();
		playlistTag.setTagId(tag.getTagId());
		playlistTag.setTagName(tag.getTagName());
		return playlistTag;
	}

	@Transactional(readOnly = true)
	public PacepalPlaylist retrievePlaylistById(Long playlistId) {

		return new PacepalPlaylist(findPlaylistById(playlistId));
	}

	@Transactional
	public void deletePlaylistByID(Long playlistId) {
		Playlist playlist = findPlaylistById(playlistId);
		playlistDao.delete(playlist);
	}

	public List<PacepalSong> retrieveAllSongs() {
		List<Song> songs = songDao.findAll();
		return songs.stream().map(this::convertTopacepalSong).collect(Collectors.toList());
	}

	private PacepalSong convertTopacepalSong(Song song) {
		PacepalSong songData = new PacepalSong();
		songData.setSongId(song.getSongId());
		songData.setSongName(song.getSongName());
		songData.setSongArtist(song.getSongArtist());
		songData.setSongDuration(song.getSongDuration());
		songData.setSongGenre(song.getSongGenre());
		songData.setSongTag(song.getSongTag());
		return songData;
	}

}
