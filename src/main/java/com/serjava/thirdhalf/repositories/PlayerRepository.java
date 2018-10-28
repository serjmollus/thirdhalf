package com.serjava.thirdhalf.repositories;

import com.serjava.thirdhalf.domain.Player;
import com.serjava.thirdhalf.errorhandling.ThirdHalfException;

import java.util.List;

public interface PlayerRepository {

    public void savePlayer(String user, Player player);
    public void deletePlayer(String user, String playerName) throws ThirdHalfException;
    public Player getPlayer(String user, String playerName) throws ThirdHalfException;
    public List<Player> getPlayers(String user) throws ThirdHalfException;
    public int getNumberOfPlayers(String user) throws ThirdHalfException;
}
