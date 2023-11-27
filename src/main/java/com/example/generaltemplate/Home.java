package com.example.generaltemplate;

public class Home {
    private final Player player;

    public Home() {
        player = new Player();
    }

    public void givePlayerAction(String actionId) {
        player.doHomeAction(actionId);
    }

    public Player getPlayer() {
        return player;
    }
}
