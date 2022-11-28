package org.example;

import discord4j.common.util.Snowflake;

import java.sql.*;
import java.util.HashMap;

public class Model {
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/";

    public static void insertOneId(Snowflake snowflake){
        long newId = snowflake.asLong();
        String insertIntoIds = "INSERT INTO rpg4j.ids (Snowflake) VALUES " + "(" + newId + ")";
        System.out.println(insertIntoIds);

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement insertPs = conn.prepareStatement(insertIntoIds);
        ) {
            insertPs.execute();
        } catch (SQLTimeoutException e) {
            System.out.println("SQLTimeoutException");
        } catch (SQLException e) {
            System.out.println("Database SQLException occurs");
        }

        String insertIntoHealth = "INSERT INTO rpg4j.health (Snowflake, max_health, current_health, health_regen) VALUES "
                + "(" + newId + ", "
                + Health.DEFAULT_MAX_HEALTH + ", "
                + Health.DEFAULT_MAX_HEALTH + ", "
                + Health.DEFAULT_HP_REGEN
                + ")";
        System.out.println(insertIntoHealth);


        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement insertPs = conn.prepareStatement(insertIntoHealth);
        ) {
            insertPs.execute();
        } catch (SQLTimeoutException e) {
            System.out.println("SQLTimeoutException");
        } catch (SQLException e) {
            System.out.println("Database SQLException occurs");
        }


    }

    public static void loadAllIds(CharacterManager characterManager){
            String getAllIds = "SELECT * FROM rpg4j.ids";

            try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
                 PreparedStatement ps = conn.prepareStatement(getAllIds);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    long id = rs.getLong("Snowflake");
                    characterManager.getUsersCharacters().put(Snowflake.of(id), new Character());
                }
            } catch (SQLException e) {
            }

        String getAllHealth = "SELECT * FROM rpg4j.health";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement ps = conn.prepareStatement(getAllHealth);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long id = rs.getLong("Snowflake");
                int maxHealth = rs.getInt("max_health");
                int currentHealth = rs.getInt("current_health");
                int healthRegen = rs.getInt("health_regen");
                Character character = characterManager.getCharacterById(Snowflake.of(id));
                character.getHealth().setMax(maxHealth);
                character.getHealth().set(currentHealth);
                character.getHealth().setRegenTime(healthRegen);
            }
        } catch (SQLException e) {
        } catch (NoSuchCharacterException e) {
            System.out.println("no such char to load health");
        }
    }

    public static void updateOneHealth(Snowflake snowflake, Character character){
        //UPDATE Customers
        //SET ContactName = 'Alfred Schmidt', City= 'Frankfurt'
        //WHERE CustomerID = 1;
        //"(Snowflake, max_health, current_health, health_regen) VALUES "

        long newId = snowflake.asLong();
        String updateHealth = "UPDATE rpg4j.health "
                + "SET max_health = '" + character.getHealth().getMax() + "', "
                + "current_health =" + "'" + character.getHealth().get() + "', "
                + "health_regen =" + "'" + character.getHealth().getRegen() + "' "
                + "WHERE Snowflake = " + "'" + newId + "';";
        System.out.println(updateHealth);

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement statement = conn.prepareStatement(updateHealth);
        ) {
            statement.execute();
        } catch (SQLTimeoutException e) {
            System.out.println("SQLTimeoutException");
        } catch (SQLException e) {
            System.out.println("Database SQLException occurs");
        }

        //update new stats from stats into the given ID in db
        //u can just delete and replace everything if it is just a few variables
    }

    public static void updateOneStats(Snowflake snowflake, Stats stats){


        //update new stats from stats into the given ID in db
        //u can just delete and replace everything if it is just a few variables
    }
    public static void loadOneStats(Snowflake snowflake, Stats stats){
        //getCharacter(snowflake);
        //character.stats = stats;
    }
    //class Databaseoperations.
        //class updateCharacter(userId)
            //getCharacter(userId)
            //Character -> void updateAll() and void updateEach()
            //character.getHealth().update(), character.getInventory().update(), character.getStats.update()
            // related hp vals, speed vals
            //setHpVal
            //setSpeedVal

    //setstats
    //load id related hp vals, speed vals
    //setHpVal
    //setSpeedVal
    //seteq
    //setinv
    //setAP
    //setHP
}
