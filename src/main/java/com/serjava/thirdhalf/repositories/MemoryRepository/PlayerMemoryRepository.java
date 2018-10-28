package com.serjava.thirdhalf.repositories.MemoryRepository;

import com.serjava.thirdhalf.domain.Player;
import com.serjava.thirdhalf.errorhandling.ThirdHalfException;
import com.serjava.thirdhalf.repositories.PlayerRepository;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerMemoryRepository implements PlayerRepository{


    private Map<String, List<Player>> playerRepo = new HashMap<>();


    public void savePlayer(String user, Player player){
        List<Player> playerList;
        if (playerRepo.containsKey(user)){
            playerList = playerRepo.get(user);
            Player playerFound = playerList.stream().filter(x -> player.getName().equals(x.getName())).findAny().orElse(null);
            if (playerFound!=null){
                playerList = playerList.stream().map(
                        x -> {
                            if (player.getName().equals(x.getName())){
                                return player;
                            }
                            else{
                                return x;
                            }
                        }).collect(Collectors.toList());
            }
            else{
                playerList.add(player);
            }
        }
        else{
            playerList = new ArrayList<>();
            playerList.add(player);
        }
        playerRepo.put(user,playerList);

    }

    public void deletePlayer(String user, String playerName) throws ThirdHalfException{
        List<Player> playerList = getPlayers(user);
        List<Player> playerListAfterDeletion =  playerList.stream().filter(player -> !player.getName().equals(playerName)).collect(Collectors.toList());
        if (playerList.size()==playerListAfterDeletion.size()){
            throw new ThirdHalfException();
        }
        playerRepo.put(user,playerListAfterDeletion);

    }

    public Player getPlayer(String user, String playerName) throws ThirdHalfException{
        return getPlayers(user).stream().filter(player -> player.getName().equals(playerName)).findAny().orElseThrow(ThirdHalfException::new);
    }

    public List<Player> getPlayers(String user) throws ThirdHalfException{
        if (playerRepo.containsKey(user)) {
            return playerRepo.get(user);
        }
        else{
            throw new ThirdHalfException();
        }
    }

    public int getNumberOfPlayers(String user) throws ThirdHalfException{
        return getPlayers(user).size();
    }
}
