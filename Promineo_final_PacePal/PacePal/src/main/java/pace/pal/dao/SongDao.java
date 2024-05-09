package pace.pal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pace.pal.entity.Song;

@Service
public interface SongDao extends JpaRepository<Song, Long> {

}
