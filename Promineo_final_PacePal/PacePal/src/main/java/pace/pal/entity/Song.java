package pace.pal.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long songId;
	private String songName;
	private String songArtist;
	private String songDuration;
	private String songGenre;
	private Long songTag;

	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	// @JoinColumn(name = "playlistId" )
	private Playlist playLists;

}
