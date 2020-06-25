package com.company.DTO;

public class StringsAndInt {

    private String gameName;
    private String Login;
    private int gameStatus;

    public StringsAndInt(String gameName, String login, int gameStatus) {
        this.gameName = gameName;
        Login = login;
        this.gameStatus = gameStatus;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public String toString() {
        return "StringsAndInt{" +
                "gameName='" + gameName + '\'' +
                ", Login='" + Login + '\'' +
                ", gameStatus=" + gameStatus +
                '}';
    }
}
