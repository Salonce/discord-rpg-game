package org.example;

import discord4j.common.util.Snowflake;

import java.util.HashMap;

public class CharacterManager {
    private final HashMap<Snowflake, Character> usersCharacters;
    public CharacterManager() {
        usersCharacters = new HashMap<>();
    }
    public void createNewCharacter(Snowflake id, CharClass charClass, CharRace charRace){
        usersCharacters.put(id, new Character(charClass, charRace));
    }
    public Character getCharacterById(Snowflake id) throws NoSuchCharacterException {
        if (usersCharacters.get(id) == null){
            throw new NoSuchCharacterException("No such character");
        }
        else return usersCharacters.get(id);
    }
}