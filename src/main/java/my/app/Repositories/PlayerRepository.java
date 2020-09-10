package my.app.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import my.app.Entities.Player;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    Optional<Player> findById(Long id);

    List<Player> findAll();

}