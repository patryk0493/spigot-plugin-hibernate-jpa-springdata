package my.app.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.app.Entities.Player;
import my.app.Repositories.PlayerRepository;

@Service("playerService")
public class PlayerService {

    @Autowired
    private PlayerRepository repository;

    public void addPlayer(String username) {
        Player player = new Player();

        player.setUsername(username);

        repository.save(player);
    }

    public List<Player> findAll() {
        return repository.findAll();
    }

}