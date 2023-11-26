package com.example.generaltemplate;

public class Home {
    private final Player player;

    public Home() {
        player = new Player();
    }

    public void givePlayerAction(String idOfBtn) {
        player.doHomeAction(idOfBtn.substring(0, idOfBtn.length()-3));
    }

    public Player getPlayer() {
        return player;
    }
}
