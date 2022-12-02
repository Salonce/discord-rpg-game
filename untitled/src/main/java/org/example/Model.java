package org.example;

import discord4j.common.util.Snowflake;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Model {
    private static final String connectionUrl = "jdbc:mysql://localhost:3306/";
    private static String inApostrophes(String string){
        return ("'" + string + "'");
    }


    private static String doubleTheApostrophe(String string){
        return string.replace("'", "''");
    }

    private static String inventoryListSaver(Character character){
        ArrayList<Item> itemList = character.getInventory().getItemList();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < itemList.size(); i++){
            stringBuilder.append(", i" + i + " = '" + doubleTheApostrophe(character.getInventory().getItemList().get(i).getName()) + "'");
        }
        for (int i = itemList.size(); i < 15; i++){
            stringBuilder.append(", i" + i + " = NULL");
        }
        return stringBuilder.toString();
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
                System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }

        String getAllInventory = "SELECT * FROM rpg4j.inventory";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement ps = conn.prepareStatement(getAllInventory);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ArrayList<Item> itemArrayList = new ArrayList<>();
                long id = rs.getLong("Snowflake");
                int max_capacity = rs.getInt("max_capacity");
                fillItems(itemArrayList, rs);
                //itemStrings[0] = ManagerItem.get(rs.getString("i0"));
                //itemStrings[1] = ManagerItem.get(rs.getString("i1"));
                Character character = characterManager.getCharacterById(Snowflake.of(id));
                character.getInventory().setMaxCapacity(max_capacity);
                character.getInventory().addItems(itemArrayList);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InventoryFullException e) {
            throw new RuntimeException(e);
        }

        String getAllEquipment = "SELECT * FROM rpg4j.equipment";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement ps = conn.prepareStatement(getAllEquipment);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ArrayList<Item> itemArrayList = new ArrayList<>();
                long id = rs.getLong("Snowflake");
                Item head = ManagerItem.get(rs.getString("head"));
                Item torso = ManagerItem.get(rs.getString("torso"));
                Item legs = ManagerItem.get(rs.getString("legs"));
                Item feet = ManagerItem.get(rs.getString("feet"));
                Item hands = ManagerItem.get(rs.getString("hands"));
                Item firstHand = ManagerItem.get(rs.getString("firsthand"));
                Item secondHand = ManagerItem.get(rs.getString("secondhand"));
                Character character = characterManager.getCharacterById(Snowflake.of(id));
                character.getEquipment().equip(head);
                character.getEquipment().equip(torso);
                character.getEquipment().equip(legs);
                character.getEquipment().equip(feet);
                character.getEquipment().equip(hands);
                character.getEquipment().equip(firstHand);
                character.getEquipment().equip(secondHand);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NotWearableItemException e) {
            throw new RuntimeException(e);
        }
    }
    private static void fillItems(ArrayList<Item> itemArrayList, ResultSet rs) throws SQLException {
        for (int i = 0; i < 15; i++){
            if (rs.getString("i" + i) != null) {
                Item newItem = ManagerItem.get(rs.getString("i" + i));
                //if (newItem != null)
                itemArrayList.add(newItem);
            }
        }
    }

    public static void addEquipmentToDb(Snowflake snowflake){
        String getId = "SELECT * FROM rpg4j.equipment WHERE Snowflake = '" + snowflake.asLong() + "'";
        String saveDefaultEquipment = "INSERT INTO rpg4j.equipment (Snowflake, head, torso, legs, feet, hands, firsthand, secondhand) VALUES "
                + "(" + snowflake.asLong() + ", "
                + "'NO_HELMET', "
                + "'NO_ARMOR', "
                + "'NO_TROUSERS', "
                + "'NO_BOOTS', "
                + "'NO_GLOVES', "
                + "'NO_WEAPON', "
                + "'NO_SHIELD'"
                + ")";
        //System.out.println(saveDefaultEquipment);
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement idExistsStatement = conn.prepareStatement(getId);
             PreparedStatement insertValuesStatement = conn.prepareStatement(saveDefaultEquipment);

             ResultSet resultSetId = idExistsStatement.executeQuery()){

            if(resultSetId.next()){
                System.out.println("user in eq IDs");
            }
            else{
                insertValuesStatement.execute();
                System.out.println("User not in eq IDs. Saving user.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addInventoryToDb(Snowflake snowflake){
        String getId = "SELECT * FROM rpg4j.inventory WHERE Snowflake = '" + snowflake.asLong() + "'";
        String saveDefaultInvetory = "INSERT INTO rpg4j.inventory (Snowflake, max_capacity) VALUES "
                + "(" + snowflake.asLong() + ", "
                + Inventory.DEFAULT_MAX_CAPACITY
                + ")";
        //System.out.println(saveDefaultInvetory);
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement idExistsStatement = conn.prepareStatement(getId);
             PreparedStatement insertValuesStatement = conn.prepareStatement(saveDefaultInvetory);

             ResultSet resultSetId = idExistsStatement.executeQuery()){

            if(resultSetId.next()){
                System.out.println(snowflake.asLong() + " in inventory IDs");
            }
            else{
                insertValuesStatement.execute();
                System.out.println("User not in inventory IDs. Saving user.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addHealthToDb(Snowflake snowflake){
        String getId = "SELECT * FROM rpg4j.health WHERE Snowflake = '" + snowflake.asLong() + "'";
        String saveDefaultHealth = "INSERT INTO rpg4j.health (Snowflake, max_health, current_health, health_regen) VALUES "
                + "(" + snowflake.asLong() + ", "
                + Health.DEFAULT_MAX_HEALTH + ", "
                + Health.DEFAULT_MAX_HEALTH + ", "
                + Health.DEFAULT_HP_REGEN
                + ")";
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement idExistsStatement = conn.prepareStatement(getId);
             PreparedStatement insertValuesStatement = conn.prepareStatement(saveDefaultHealth);

             ResultSet resultSetId = idExistsStatement.executeQuery()){

            if(resultSetId.next()){
                System.out.println("user in health IDs");
            }
            else{
                insertValuesStatement.execute();
                System.out.println("User not in Health IDs. Saving user.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addIdToDb(Snowflake snowflake){
        String getId = "SELECT * FROM rpg4j.ids WHERE Snowflake = '" + snowflake.asLong() + "'";
        String saveDefaultId = "INSERT INTO rpg4j.ids (Snowflake) VALUES " + "(" + snowflake.asLong() + ")";
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement idExistsStatement = conn.prepareStatement(getId);
             PreparedStatement insertValuesStatement = conn.prepareStatement(saveDefaultId);

             ResultSet resultSetId = idExistsStatement.executeQuery()){

            //statement select where Snowflake = id
            if(resultSetId.next()){
                System.out.println("user in IDs");
            }
            else{
                insertValuesStatement.execute();
                System.out.println("User not in IDs. Saving user.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateDbForAll(){
        String getIds = "SELECT * FROM rpg4j.ids";
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement getAllIdsStatement = conn.prepareStatement(getIds);
             ResultSet resultSetId = getAllIdsStatement.executeQuery()){

            while(resultSetId.next()){
                Snowflake snowflake = Snowflake.of(resultSetId.getLong("Snowflake"));
                addAllToDb(snowflake);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addAllToDb(Snowflake snowflake){
        addIdToDb(snowflake);
        addInventoryToDb(snowflake);
        addEquipmentToDb(snowflake);
        addHealthToDb(snowflake);
    }

    public static void updateOneHealth(Snowflake snowflake, Character character){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement statement = updateHealthStatement(conn, snowflake, character);
        ) {
            statement.execute();
        } catch (SQLTimeoutException e) {
            System.out.println("SQLTimeoutException");
        } catch (SQLException e) {
            System.out.println("Database SQLException occurs");
        }
    }


    public static void updateOneInventory(Snowflake snowflake, Character character){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement statement = updateInventoryStatement(conn, snowflake, character);
        ) {
            statement.execute();
        } catch (SQLTimeoutException e) {
            System.out.println("SQLTimeoutException");
        } catch (SQLException e) {
            System.out.println("Database SQLException occurs");
        }
    }

    public static void updateOneEquipment(Snowflake snowflake, Character character){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement statement = updateEquipmentStatement(conn, snowflake, character);
        ) {
            statement.execute();
        } catch (SQLTimeoutException e) {
            System.out.println("SQLTimeoutException");
        } catch (SQLException e) {
            System.out.println("Database SQLException occurs");
        }
    }


    private static PreparedStatement updateEquipmentStatement(Connection connection, Snowflake snowflake, Character character) throws SQLException {
        long newId = snowflake.asLong();
        String updateEquipment = "UPDATE rpg4j.equipment "
                + "SET head = '" + character.getEquipment().getHeadEquipment().getName() + "'"
                + ", torso = '" + character.getEquipment().getTorsoEquipment().getName() + "'"
                + ", legs = '" + character.getEquipment().getLegsEquipment().getName() + "'"
                + ", feet = '" + character.getEquipment().getFeetEquipment().getName() + "'"
                + ", hands = '" + character.getEquipment().getHandsEquipment().getName() + "'"
                + ", firstHand = '" + character.getEquipment().getFirstHandEquipment().getName() + "'"
                + ", secondHand = '" + character.getEquipment().getSecondHandEquipment().getName() + "'"
                + " WHERE Snowflake = " + "'" + newId + "';";
        System.out.println(updateEquipment);
        return connection.prepareStatement(updateEquipment);
    }

    private static PreparedStatement updateHealthStatement(Connection connection, Snowflake snowflake, Character character) throws SQLException {
        long newId = snowflake.asLong();
        String updateHealth = "UPDATE rpg4j.health "
                + "SET max_health = '" + character.getHealth().getMax() + "', "
                + "current_health =" + "'" + character.getHealth().get() + "', "
                + "health_regen =" + "'" + character.getHealth().getRegen() + "' "
                + "WHERE Snowflake = " + "'" + newId + "';";
        System.out.println(updateHealth);
        return connection.prepareStatement(updateHealth);
    }


    private static PreparedStatement updateInventoryStatement(Connection connection, Snowflake snowflake, Character character) throws SQLException {
        long newId = snowflake.asLong();
        String updateInventory = "UPDATE rpg4j.inventory "
                + "SET max_capacity = '" + character.getInventory().getMaxCapacity() + "'"
                + inventoryListSaver(character)
                + " WHERE Snowflake = " + "'" + newId + "';";
        //System.out.println(updateInventory);
        return connection.prepareStatement(updateInventory);
    }
}

   /*

    public static void addIdToDb(Snowflake snowflake){
        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement statement = addIdStatement(conn, snowflake)){
             statement.execute();
        } catch (SQLTimeoutException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private static PreparedStatement addIdStatement(Connection connection, Snowflake snowflake) throws SQLException {
        long newId = snowflake.asLong();
        String saveDefaultId = "INSERT INTO rpg4j.ids (Snowflake) VALUES " + "(" + newId + ")";
        System.out.println(saveDefaultId);
        return connection.prepareStatement(saveDefaultId);
    }
     */



/*
    public static void ifNotInDbAdd(Snowflake snowflake){
        String getId = "SELECT * FROM rpg4j.ids WHERE Snowflake = '" + snowflake.asLong() + "'";
        String getHealth = "SELECT * FROM rpg4j.health WHERE Snowflake = '" + snowflake.asLong() + "'";
        String getInventory = "SELECT * FROM rpg4j.inventory WHERE Snowflake = '" + snowflake.asLong() + "'";
        String getEquipment = "SELECT * FROM rpg4j.equipment WHERE Snowflake = '" + snowflake.asLong() + "'";

        try (Connection conn = DriverManager.getConnection(connectionUrl, "root", "password1");
             PreparedStatement idStatement = conn.prepareStatement(getId);
             PreparedStatement healthStatement = conn.prepareStatement(getHealth);
             PreparedStatement inventoryStatement = conn.prepareStatement(getInventory);
             PreparedStatement equipmentStatement = conn.prepareStatement(getEquipment);

             ResultSet resultSetId = idStatement.executeQuery();
             ResultSet resultSetHealth = healthStatement.executeQuery();
             ResultSet resultSetInventory = inventoryStatement.executeQuery();
             ResultSet resultSetEquipment = equipmentStatement.executeQuery()) {

            //statement select where Snowflake = id
            if(resultSetId.next() && resultSetHealth.next() && resultSetInventory.next() && resultSetEquipment.next()){
                System.out.println("user in all dbs");
            }
            else{
                System.out.println("User not in dbs. Saving user.");
                addIdToDb(snowflake);
                addInventoryToDb(snowflake);
                addEquipmentToDb(snowflake);
                addHealthToDb(snowflake);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
*/