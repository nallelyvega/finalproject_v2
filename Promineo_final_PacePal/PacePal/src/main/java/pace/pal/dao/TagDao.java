package pace.pal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pace.pal.entity.Tag;

@Service
public interface TagDao extends JpaRepository<Tag, Long> {

}
