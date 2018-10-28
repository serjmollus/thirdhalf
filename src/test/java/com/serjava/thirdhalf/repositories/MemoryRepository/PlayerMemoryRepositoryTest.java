package com.serjava.thirdhalf.repositories.MemoryRepository;

import com.serjava.thirdhalf.domain.Player;
import com.serjava.thirdhalf.errorhandling.ThirdHalfException;
import com.serjava.thirdhalf.repositories.MemoryRepository.PlayerMemoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlayerMemoryRepositoryTest {

    @Autowired
    private PlayerMemoryRepository playerRepository;

    private final static String USER = "Sergio";
    private Player serj;

    @Before
    public void initTests(){
        playerRepository = new PlayerMemoryRepository();
        serj = new Player();
        serj.setDefense(8);
        serj.setGoalkeeping(1);
        serj.setHeading(5);
        serj.setKick(6);
        serj.setName("Serj");
        serj.setPass(7);
        serj.setSpeed(3);
        playerRepository.savePlayer(USER, serj);
        Player player = new Player();
        player.setDefense(5);
        player.setGoalkeeping(1);
        player.setHeading(5);
        player.setKick(6);
        player.setName("Marc");
        player.setPass(10);
        player.setSpeed(3);
        playerRepository.savePlayer(USER, player);
    }

    //GET FUNCTIONALITY

    @Test
    public void shouldGetNumberOfPlayersOfUser() throws ThirdHalfException{
        assertThat(playerRepository.getNumberOfPlayers(USER), equalTo(2));
    }

    @Test( expected = ThirdHalfException.class)
    public void shouldThrowExceptionWhenGettingNumberOfPlayersOfNonExistingUser() throws ThirdHalfException{
        String nonExistingUser = "OtherUser";
        playerRepository.getNumberOfPlayers(nonExistingUser);
    }

    @Test
    public void shouldGetPlayerList() throws ThirdHalfException{
        assertThat(playerRepository.getPlayers(USER).size(), equalTo(2));
    }

    @Test( expected = ThirdHalfException.class)
    public void shouldThrowExceptionWhenGetPlayerListOfNonExistingUser() throws ThirdHalfException{
        playerRepository.getPlayers("nonExistingUser");
    }

    @Test
    public void shouldGetPlayer() throws ThirdHalfException{
        assertThat(playerRepository.getPlayer(USER, serj.getName()), equalTo(serj));
    }

    @Test( expected = ThirdHalfException.class)
    public void shouldThrowExceptionWhenGetPlayerNonExisting() throws ThirdHalfException{
        playerRepository.getPlayer(USER,"unknown name");
    }


    //SAVING FUNCIONALITY
    @Test
    public void shouldSaveNonExistingPlayer() throws ThirdHalfException{
        Player player = new Player();
        player.setDefense(8);
        player.setGoalkeeping(1);
        player.setHeading(5);
        player.setKick(6);
        player.setName("NewPlayer");
        player.setPass(7);
        player.setSpeed(3);
        playerRepository.savePlayer(USER, player);
        assertThat(playerRepository.getNumberOfPlayers(USER), equalTo(3));
    }

    @Test
    public void shouldUpdateExistingPlayer() throws ThirdHalfException{
        Player player = new Player();
        player.setDefense(6);
        player.setGoalkeeping(1);
        player.setHeading(5);
        player.setKick(6);
        player.setName("Serj");
        player.setPass(10);
        player.setSpeed(3);
        playerRepository.savePlayer(USER, player);
        assertThat(playerRepository.getNumberOfPlayers(USER), equalTo(2));
        Player serjPlayer = playerRepository.getPlayers(USER).stream().filter(x -> "Serj".equals(x.getName())).findAny().orElse(null);
        assertThat(serjPlayer.getDefense(),equalTo(6));
    }

    //DELETE FUNCTIONALITY
    @Test
    public void shouldDeletePlayer() throws ThirdHalfException{
        playerRepository.deletePlayer(USER,serj.getName());
        assertThat(playerRepository.getNumberOfPlayers(USER),equalTo(1));
        assertThat(playerRepository.getPlayers(USER).stream().filter(player -> player.getName().equals(serj.getName())).findAny().orElse(null),equalTo(null));
    }

    @Test( expected = ThirdHalfException.class)
    public void shouldThrowExceptionWhenDeletingNonExistingPlayer() throws ThirdHalfException{
        playerRepository.deletePlayer(USER,"nonExistingName");
    }

    @Test( expected = ThirdHalfException.class)
    public void shouldThrowExceptionWhenDeletingNonExistingUser() throws ThirdHalfException{
        playerRepository.deletePlayer("nonExistingUser",serj.getName());
    }
}
