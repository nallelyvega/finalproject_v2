package pace.pal.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pace.pal.controller.model.PacepalPlaylist;
import pace.pal.controller.model.PacepalSong;
import pace.pal.controller.model.PacepalTag;
import pace.pal.service.PacePalService;

@RestController
@RequestMapping("/pacepal")
@Slf4j

public class PacePalController {

	@Autowired
	private PacePalService pacepalService;

	// creates new playlist

	@PostMapping("/{playlistId}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PacepalPlaylist insertPlayList(@RequestBody PacepalPlaylist pacepalPlaylist) {
		log.info("Create playlist: {}", pacepalPlaylist);
		return pacepalService.savePlaylist(pacepalPlaylist);
	}

	// updates playlist name
	@PutMapping("/{playlistId}")
	public PacepalPlaylist updatePlaylist(@PathVariable Long playlistId, @RequestBody PacepalPlaylist pacepalPlaylist) {
		pacepalPlaylist.setPlaylistId(playlistId);

		System.out.println("Updating playlist with ID: " + playlistId);

		return pacepalService.savePlaylist(pacepalPlaylist);
	}

	/*
	 * creates song within playlist NEEDS TESTING
	 * 
	 * @PostMapping("/{playlistId}/[songId]")
	 * 
	 * @ResponseStatus(code = HttpStatus.CREATED) public PacepalSong
	 * addSongToPlaylist(@PathVariable Long songId, @RequestBody PacepalSong
	 * songData) { log.info("Adding song to playlist with ID {}:{}", songId,
	 * songData); return pacepalService.saveSong(songId, songData); }
	 */

	// NEEDS TESTING creates tag for playlist
	@PostMapping("/{playlistId}/[tagId]")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PacepalTag addTagToPlaylist(@PathVariable Long playlistId, @RequestBody PacepalTag tagData) {
		log.info("Adding tag to playlist with ID {}:{}", playlistId, tagData);
		return pacepalService.saveTag(playlistId, tagData);
	}

	@GetMapping
	public List<PacepalPlaylist> getAllPlaylists() {
		log.info("Retrieving all playlists");
		return pacepalService.retrieveAllPlaylists();
	}

	@GetMapping("/{playlistId}")
	public PacepalPlaylist getPlaylistById(@PathVariable Long playlistId) {
		log.info("Retrieving playlists with ID {}", playlistId);
		return pacepalService.retrievePlaylistById(playlistId);
	}

	@DeleteMapping("/{playlistId}")
	public Map<String, String> deletePlaylistById(@PathVariable Long playlistId) {
		log.info("Deleting playlist with ID {}", playlistId);
		pacepalService.deletePlaylistByID(playlistId);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Playlist with ID " + playlistId + " deleted successfully");
		return response;
	}

	// gets all songs
	@GetMapping("/songs")
	public List<PacepalSong> getAllSongs() {
		log.info("Retrieving all playlists");
		return pacepalService.retrieveAllSongs();
	}

	/*
	 * get song by ID (songId)
	 * 
	 * @GetMapping("/songs/{songId}") public PacepalPlaylist
	 * getSongById(@PathVariable Long songId) {
	 * log.info("Retrieving playlists with ID {}", songId); return
	 * pacepalService.retrieveSongById(songId);
	 */
}
