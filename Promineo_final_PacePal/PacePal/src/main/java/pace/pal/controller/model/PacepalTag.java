package pace.pal.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pace.pal.entity.Tag;

@Data
@NoArgsConstructor
public class PacepalTag {
	private Long tagId;
	private Long playlistId;
	private String tagName;

	public PacepalTag(Tag tag) {
		tagId = tag.getTagId();
		playlistId = tag.getPlaylistId();
		tagName = tag.getTagName();
	}
}
