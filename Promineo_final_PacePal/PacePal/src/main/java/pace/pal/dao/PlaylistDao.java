package pace.pal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pace.pal.entity.Playlist;

@Service
public interface PlaylistDao extends JpaRepository<Playlist, Long> {
}
