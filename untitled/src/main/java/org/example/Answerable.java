package org.example;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.time.Instant;
import java.util.*;

interface MessageProcessingMachine{
    void processMessage();
}

abstract class AnsweringHelper implements MessageProcessingMachine{
    public static void setMessage(Message message) throws InvalidUserException {
        try {
            AnsweringHelper.id = message.getAuthor().get().getId();
            AnsweringHelper.content = message.getContent();
            AnsweringHelper.channel = message.getChannel().block();
            AnsweringHelper.userName = message.getAuthor().get().getUsername();
            AnsweringHelper.userNameId = "<@" + id.asString() + ">";
            AnsweringHelper.userAvatarUrl = message.getAuthor().get().getAvatarUrl();
            AnsweringHelper.character = characterManager.getCharacterById(id);

        }
        catch(NoSuchElementException noSuchElementException){
            throw new InvalidUserException("Invalid message Author");
        } catch (NoSuchCharacterException noSuchCharacterException) {
            AnsweringHelper.character = null;
        }
    }

    private static String content;
    protected static String getContent(){return content;}

    private static MessageChannel channel;
    protected static MessageChannel getMessageChannel(){return channel;}

    private static Snowflake id;
    protected static Snowflake getId(){return id;}

    private static Character character;
    protected static Character getCharacter(){return character;}

    private static CharacterManager characterManager;
    protected static CharacterManager getCharacterManager(){return characterManager;}

    private static String userName;
    protected static String getUserName(){return userName;}

    private static String userNameId;
    protected static String getUserNameId(){return userNameId;}

    private static String userAvatarUrl;
    protected static String getUserAvatarUrl(){return userAvatarUrl;}

    private static DiscordClient discordClient;
    public static void setDiscordClient(DiscordClient discordClient) {
        AnsweringHelper.discordClient = discordClient;
    }

    private static Shop shop;
    public static void setShops(Shop shop) {
        AnsweringHelper.shop = shop;
    }
    public static Shop getShop(){return AnsweringHelper.shop;}


    public String getUsernameBySnowflake(Snowflake id){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            discordClient.getUserById(id).getData().subscribe(data -> {stringBuilder.append(data.username());});
            return stringBuilder.toString();
        } catch (Exception e){
            System.out.println("exception when catching user");
        }
        return null;
    }

    public static void setCharacterManager(CharacterManager characterManager) {
        AnsweringHelper.characterManager = characterManager;
    }

    protected static boolean characterCheck(){
        if (character == null){
            sendMessage(channel, "You need to create a character to use that command!");
            return false;
        }
        else return true;
    }
    protected static boolean characterNegativeCheck(){
        if (character != null){
            sendMessage(channel, "You can't use that command if you already have a character!");
            return false;
        }
        else return true;
    }
    protected static void sendMessage(MessageChannel messageChannel, String message){
        messageChannel.createMessage(message).block();
    }
    protected static void sendMessage(MessageChannel messageChannel, EmbedCreateSpec embedMessage){
        messageChannel.createMessage(embedMessage).block();
    }
    protected static String addSpaces(String string, int maxWidth){
        StringBuilder stringBuilder = new StringBuilder(string);
        while(stringBuilder.length() < maxWidth){
            stringBuilder.append("\u2800");
        }
        return stringBuilder.toString();
    }
    /*
    protected static String[] retrieveSplitContent(int limit){
        return getContent().split(" ", limit);
    }
    */
}


class CallGive extends AnsweringHelper{
    public void processMessage(){
        try{
            String[] splitString = getContent().split(" ", 3);
            if (splitString[0].equals("?give")){
                String itemName = splitString[2];
                Snowflake secondId = Snowflake.of(splitString[1]);
                Character receivingCharacter = getCharacterManager().getCharacterById(secondId);
                Item itemToMove = getCharacter().getInventory().getItemByName(itemName);
                receivingCharacter.getInventory().addItem(itemToMove);
                getCharacter().getInventory().removeItem(itemToMove);
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        //secondId can be null
                        .author(getUserName() + " gives " + itemToMove.getName() + " to " + getUsernameBySnowflake(secondId), null, getUserAvatarUrl())
                        .color(Color.RED)
                        .build();
                sendMessage(getMessageChannel(), embed);
            }
        } catch (NoSuchCharacterException ex) {
            sendMessage(getMessageChannel(), "Character doesn't exist!");
        } catch (NumberFormatException e){
            sendMessage(getMessageChannel(), "Wrong ID");
        } catch (InventoryFullException e) {
            sendMessage(getMessageChannel(), "Sorry! Can not do! Inventory of receiver is full.");
        } catch (NullPointerException e){
            sendMessage(getMessageChannel(), "Sorry! Something went wrong with receiver's name");
        } catch (NoSuchItemException e){
            sendMessage(getMessageChannel(), "Sorry! You don't have that item!");
        } catch (Exception e){
            sendMessage(getMessageChannel(), "Unknown exception");
        }
    }
}


class CallCharTester extends AnsweringHelper{
    public void processMessage(){
        try {
            if (getContent().equals("?tester") && characterNegativeCheck()){
                CharClass charClass = CharClassFactory.createClass("archer");
                CharRace charRace = CharRaceFactory.createRace("human");
                 getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                 getCharacterManager().getCharacterById(getId()).getInventory().addItem(ItemManager.STEEL_SHIELD);
                 getCharacterManager().getCharacterById(getId()).getInventory().addItem(ItemManager.STEEL_ARMOR);
                 getCharacterManager().getCharacterById(getId()).getInventory().addItem(ItemManager.STEEL_SWORD);

                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                    .color(Color.BLUE)
                    .author(getUserName() + " - " + "Character created!", null, getUserAvatarUrl())
                    .timestamp(Instant.now())
                    .addField("Class", charClass.getName(), true)
                    .addField("Race", charRace.getName(), true)
                    .build();
                 sendMessage(getMessageChannel(), embed);
            }
        } catch(Exception e){

        }
    }
}

class CallHelp extends AnsweringHelper{
    private String getHelp(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?help")
                .append("\n?tester - create test character")
                .append("\n?create *class* *race* - create character ")
                .append("\n?give *other user's ID* - give an item to someone")
                .append("\n?eq")
                .append("\n?i")
                .append("\n?drop")
                .append("\n?equip")
                .append("\n?unequip")
                .append("\n?ap")
                .append("\n?charinfo")
                .append("\n?forest");

        return stringBuilder.toString();
    }
    public void processMessage(){
        if (getContent().equals("?help")){
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        .title("Available commands")
                        .addField("...", getHelp(), false)
                        .author(getUserName(), null, getUserAvatarUrl())
                        .timestamp(Instant.now())
                        .build();
                sendMessage(getMessageChannel(), embed);
        }
    }
}

class CallCreation extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?created")){
            sendMessage(getMessageChannel(), "The bot was created in 2022!");
        }
    }
}

class CallCharInfo extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?charinfo")){
            if (characterCheck()){
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        .addField("Class ", getCharacter().getCharClass().getName(), true)
                        .addField("Race ", getCharacter().getCharRace().getName(), true)
                        .addField("Combat power ", String.valueOf(getCharacter().getCombatPower()), true)
                        .addField("Action Points ", String.valueOf(getCharacter().getActionPoints().getCurrentAP()) + "/" + ActionPoints.MAX_AP, true)
                        .author(getUserName(), null, getUserAvatarUrl())
                        .timestamp(Instant.now())
                        .build();
                sendMessage(getMessageChannel(), embed);
            }
        }
    }
}


class CallCreateCharacter extends AnsweringHelper {
    private EmbedCreateSpec.Builder getErrorEmbedBuilder() {
        return EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .author(getUserName(), null, getUserAvatarUrl())
                .timestamp(Instant.now());
    }

    public void processMessage() {
        try {
            String[] splitContent = getContent().split(" ", 3);
            if (splitContent[0].equals("?create")) {
                CharClass charClass = CharClassFactory.createClass(splitContent[1]);
                CharRace charRace = CharRaceFactory.createRace(splitContent[2]);
                if (characterNegativeCheck()) {
                    getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                    EmbedCreateSpec.Builder embedBuilder = getErrorEmbedBuilder().title("Character created!")
                            .addField("Class", charClass.getName(), true)
                            .addField("Race", charRace.getName(), true);
                    sendMessage(getMessageChannel(), embedBuilder.build());
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            EmbedCreateSpec.Builder embedBuilder = getErrorEmbedBuilder().title("Not enough arguments! Try:\n'?create <class: *knight*, *archer*, *mage*> <race: *human*, *elf*, *orc*>'\nExample: '*?create archer orc*'");
            sendMessage(getMessageChannel(), embedBuilder.build());
        } catch (IllegalCharacterClassException e) {
            EmbedCreateSpec.Builder embedBuilder = getErrorEmbedBuilder().title("Illegal class name! Try:\n?'create <class: *knight*, *archer*, *mage*> <race: *human*, *elf*, *orc*>'\nExample: '*?create archer orc*'");
            sendMessage(getMessageChannel(), embedBuilder.build());
        } catch (IllegalCharacterRaceException e) {
            EmbedCreateSpec.Builder embedBuilder = getErrorEmbedBuilder().title("Illegal race name! Try:\n'?create <class: *knight*, *archer*, *mage*> <race: *human*, *elf*, *orc*>'\nExample: '*?create archer orc*'");
            sendMessage(getMessageChannel(), embedBuilder.build());
        } catch (Exception e) {
            EmbedCreateSpec.Builder embedBuilder = getErrorEmbedBuilder().title("Unknown Exception");
            sendMessage(getMessageChannel(), embedBuilder.build());
        }
    }
}

class CallEquip extends AnsweringHelper{
    public void processMessage(){
        try{
            String[] splitString = getContent().split(" ", 2);
            if (splitString[0].equals("?equip")){
                try{
                    String itemName = splitString[1];
                    System.out.println(splitString[0] + " : " + (splitString[1]));
                    Item itemToEquip = getCharacter().getInventory().getItemByName(itemName);
                    if (itemToEquip == null)
                        System.out.println("itemToEquip is == null");
                    //if (itemToEquip != null)
                    if (itemToEquip != null && itemToEquip.getWearablePart() != Wearable.NOTHING){
                        System.out.println("itemToEquip name: " + itemToEquip.getName());
                        Wearable wearable = itemToEquip.getWearablePart();
                        System.out.println("wearable to equip: " + wearable);
                        Item itemToUnequip = getCharacter().getEquipment().getItemByWearable(wearable);
                        System.out.println("item to unequip: " + itemToUnequip.getName());
                        getCharacter().getEquipment().equipItem(itemToEquip);
                        System.out.println("equipped: " + itemToEquip.getName());
                        getCharacter().getInventory().removeItemByName(itemName);
                        System.out.println("removed from Inventory: " + itemToEquip.getName());
                        if (!itemToUnequip.isEmptyEquipment()) {
                            getCharacter().getInventory().addItem(itemToUnequip);
                            System.out.println("added to back to inventory: " + itemToUnequip.getName());
                        }
                        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                                .author(getUserName() + " equipped " + itemToEquip.getName(), null, getUserAvatarUrl())
                                .color(Color.RED)
                                .build();
                        sendMessage(getMessageChannel(), embed);
                    }
                } catch (Exception e){
                    sendMessage(getMessageChannel(), "Incorrect command!");
                }
            }
        }
        catch (Exception e){}
    }
}

class CallDrop extends AnsweringHelper{
    public void processMessage(){
        try{
            String[] splitString = getContent().split(" ", 2);
            if (splitString[0].equals("?drop")){
                try{
                    String itemName = splitString[1];
                    System.out.println(splitString[0] + " : " + (splitString[1]));
                    Item itemToDrop = getCharacter().getInventory().getItemByName(itemName);
                    if (itemToDrop == null)
                        System.out.println("itemToDrop is == null");
                    if (itemToDrop != null){
                        StringBuilder stringBuilder = new StringBuilder();
                        System.out.println("itemToDrop name: " + itemToDrop.getName());
                        getCharacter().getInventory().removeItemByName(itemName);
                        System.out.println("removed from Inventory: " + itemToDrop.getName());
                        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                                .author(getUserName() + " dropped " + itemToDrop.getName(), null, getUserAvatarUrl())
                                .color(Color.RED)
                                .build();
                        sendMessage(getMessageChannel(), embed);
                    }
                } catch (Exception e){
                    sendMessage(getMessageChannel(), "Incorrect command!");
                }
            }
        }
        catch (Exception e){}
    }
}

class CallUnequip extends AnsweringHelper{
    public void processMessage(){
        try{
            String[] splitString = getContent().split(" ", 2);
            if (splitString[0].equals("?unequip")){
                try{
                    String itemName = splitString[1];
                    System.out.println(splitString[0] + " : " + (splitString[1]));
                    Item itemToUnequip = getCharacter().getEquipment().getItemByName(itemName);
                    if (itemToUnequip == null)
                        System.out.println("itemToEquip is == null");
                    if (itemToUnequip != null){
                        System.out.println("itemToUnequip name: " + itemName);
                        if (itemToUnequip.isEmptyEquipment()) {
                            System.out.println("no-equipment item, doing nothing...");
                        }
                        if (!itemToUnequip.isEmptyEquipment()) {
                            getCharacter().getEquipment().removeItemByName(itemName);
                            getCharacter().getInventory().addItem(itemToUnequip);
                            System.out.println("added to back to inventory: " + itemToUnequip.getName());
                            //what if inventory full?
                        }
                        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                                .author(getUserName() + " unequipped " + itemToUnequip.getName(), null, getUserAvatarUrl())
                                .color(Color.RED)
                                .build();
                        sendMessage(getMessageChannel(), embed);
                    }
                } catch (Exception e){
                    sendMessage(getMessageChannel(), "Incorrect command!");
                }
            }
        }
        catch (Exception e){}
    }
}

class CallSell extends AnsweringHelper{
    public void processMessage(){
        try{
            String[] splitString = getContent().split(" ", 2);
            if (splitString[0].equalsIgnoreCase("?sell")){
                try{
                    String itemName = splitString[1];
                    System.out.println(splitString[0] + " : " + (splitString[1]));
                    Item itemToSell = getCharacter().getInventory().getItemByName(splitString[1]);
                    if (itemToSell == null)
                        System.out.println("itemToSell is == null");
                    if (itemToSell != null){
                        System.out.println("itemToSell name: " + itemToSell.getName());
                        getCharacter().getInventory().removeItemByName(itemName);
                        getCharacter().getInventory().setMoney(getCharacter().getInventory().getMoney() + itemToSell.getValue());
                        System.out.println("removed from Inventory: " + itemToSell.getName());
                        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                                .author(getUserName() + " sold " + itemToSell.getName() + " for " + itemToSell.getValue(), null, getUserAvatarUrl())
                                .color(Color.RED)
                                .build();
                        sendMessage(getMessageChannel(), embed);
                    }
                } catch (Exception e){
                    sendMessage(getMessageChannel(), "Incorrect command!");
                }
            }
        }
        catch (Exception e){}
    }
}

class CallPing extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?ping")){
            sendMessage(getMessageChannel(), "pong");
        }
    }
}

class CallCooldowns extends AnsweringHelper{
    final static int AP_MAX_CHAR = 20;
    public void processMessage(){
        if (getContent().equals("?ap")){
            if (characterCheck()) {
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        .title("Action points")
                        .addField(addSpaces("Available", AP_MAX_CHAR), getCharacter().getActionPoints().getCurrentAP() + "/" + ActionPoints.MAX_AP, true)
                        .addField(addSpaces("Recovery time", AP_MAX_CHAR), String.valueOf(getCharacter().getActionPoints().AP_RECOVERY_TIME), true)
                        .addField(addSpaces("Next AP in ", AP_MAX_CHAR), String.valueOf(getCharacter().getActionPoints().getFirstCD()), true)
                        .addField(addSpaces("All AP in ", AP_MAX_CHAR), String.valueOf(getCharacter().getActionPoints().getLastCD()), true)
                        .author(getUserName(), null, getUserAvatarUrl())
                        //.thumbnail("https://openclipart.org/image/800px/330656")
                        .timestamp(Instant.now())
                        .build();
                sendMessage(getMessageChannel(), embed);
            }
        }
    }
}

class CallShop extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?shop")){
            if (characterCheck()) {
                sendMessage(getMessageChannel(), getShop().itemsAvailable());
            }
        }
    }
}

class CallI extends AnsweringHelper{
    final static int INV_MAX_CHAR = 15;
    private String getItemInfo(Item item){
        int empty = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n:coin: " + item.getValue());
        stringBuilder.append("\n:scales: " + item.getWeight());
        stringBuilder.append("\n\u2800");
        if (item.hasAttack())
            stringBuilder.append("\n:axe: " + item.getAttack());
        else
            empty++;
        if (item.hasDefense()){
            stringBuilder.append("\n:shield: " + item.getDefence());
        } else
            empty++;
        int a = 0;
        while (a < empty){
            stringBuilder.append("\n\u2800");
            a++;
        }
        return stringBuilder.toString();
    }

    public void processMessage(){
        if (getContent().equals("?i")){
            if (characterCheck()) {
                EmbedCreateSpec.Builder embedBuilder = EmbedCreateSpec.builder()
                        .color(Color.BROWN)
                        .addField("Total"
                                , ":coin: " + getCharacter().getInventory().getMoney()
                                        + " :scales: " + getCharacter().getInventory().getItemsWeight(), false)
                        .author(getUserName() + " : Inventory (" + getCharacter().getInventory().getSize() + "/" + Inventory.MAX_ITEM_NUMBER + ")", null, getUserAvatarUrl())
                        .thumbnail("https://openclipart.org/image/800px/330656");
                for (Item item : getCharacter().getInventory().getItemList()){
                    embedBuilder.addField(addSpaces(item.getName(), INV_MAX_CHAR), getItemInfo(item), true);
                }
                sendMessage(getMessageChannel(), embedBuilder.build());
            }
        }
    }
}

class CallEquipmentInfo extends AnsweringHelper{
    final static int EQ_MAX_CHAR = 13;
    private String getEmbedStats(Item item){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.getName());
        //stringBuilder.append("\n:coin: " + item.getValue());
        stringBuilder.append("\n:scales: " + item.getWeight());
        if (item.hasAttack()){
            stringBuilder.append("\n:axe: " + item.getAttack());
        }
        if (item.hasDefense()){
            stringBuilder.append("\n:shield: " + item.getDefence());
        }
        return stringBuilder.toString();
    }
    public void processMessage(){
        if (getContent().equals("?eq")){
            if (characterCheck()){
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        //.title("Equipment")
                        .addField(addSpaces("Head", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getHeadEquipment()), true)
                        .addField(addSpaces("Torso", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getTorsoEquipment()), true)
                        .addField(addSpaces("Legs", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getLegsEquipment()), true)
                        .addField(addSpaces("Feet", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getFeetEquipment()), true)
                        .addField(addSpaces("Hands", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getHandsEquipment()), true)
                        .addField(addSpaces("1st hand", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getFirstHandEquipment()), true)
                        .addField(addSpaces("2nd hand", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getSecondHandEquipment()), true)
                        .addField("Total", ":shield: " + getCharacter().getEquipment().getTotalDefence() + "\n:axe: " + getCharacter().getEquipment().getTotalAttack() + "\n:scales: " + getCharacter().getEquipment().getTotalWeight(), true)
                        //.addField(addSpaces("Total attack", EQ_MAX_CHAR), String.valueOf(getCharacter().getEquipment().getTotalAttack()), true)
                        //.addField(addSpaces("Total weight", EQ_MAX_CHAR), String.valueOf(getCharacter().getEquipment().getTotalWeight()), true)
                        .author(getUserName() + " - Equipment", null, getUserAvatarUrl())
                        //.thumbnail("https://openclipart.org/image/800px/330656")
                        .timestamp(Instant.now())
                        .build();
                sendMessage(getMessageChannel(), embed);
            }
        }
    }
}

class CallRat extends AnsweringHelper{
    private String printFightResult(Fight fight, Monster monster){
        if (fight.getResult().equals("A wins"))
           return (getUserName() + " wins against " + monster.getName());
        else if (fight.getResult().equals("B wins"))
            return (getUserName() + " loses against the "  + monster.getName());
        else
            return (getUserName() + " ties with "  + monster.getName());
    }

    private String printLoot(ArrayList<Item> lootList){
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Item> itemIterator = lootList.iterator();
        while (itemIterator.hasNext()) {
            stringBuilder.append(itemIterator.next().getName());
            if (itemIterator.hasNext())
                stringBuilder.append(", ");
        }
        if (stringBuilder.length() == 0)
            stringBuilder.append("-");
        return stringBuilder.toString();
    }

    /*
    private EmbedCreateSpec.Builder addPrintingLoot(EmbedCreateSpec.Builder builder, ArrayList<Item> randomLoot){
        builder.addField("Loot", printLoot(randomLoot), false);
        return builder;
    }
     */

    private Dungeon getDungeon(String command){
        switch (command){
            case "?forest":
                return DungeonManager.FOREST;
            case "?lake":
                return DungeonManager.LAKE;
            case "?chest":
                return DungeonManager.CHESTPLACE;
            case "?server":
                return DungeonManager.SERVER;
            case "?cave":
                return DungeonManager.CHICKEN_CAVE;
            default:
                return null;
        }
    }

    public void processMessage(){
        Dungeon dungeon = getDungeon(getContent());
        if (dungeon != null){
            try {
                if (characterCheck()) {
                    Monster newMonster = dungeon.getMonster();
                    Fight fight = new Fight(getCharacter(), newMonster);
                    ArrayList<Item> randomLoot = newMonster.getRandomLoot();
                    EmbedCreateSpec.Builder embedBuilder = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        .author(getUserName(), null, getUserAvatarUrl())
                        .title("Fight")
                        .addField(newMonster.getName(), newMonster.getDescription(), false)
                        .addField(getUserName(), ":crossed_swords: " + getCharacter().getCombatPower(), true)
                        .addField(newMonster.getName(), ":crossed_swords: " + newMonster.getCombatPower(), true)
                        .addField("Result", printFightResult(fight, newMonster), false)
                        .timestamp(Instant.now());
                    if (fight.getResult().equals("A wins")) {
                        embedBuilder.addField("Loot", printLoot(randomLoot), false);
                    }
                    getCharacter().getInventory().addItems(randomLoot);
                    sendMessage(getMessageChannel(), embedBuilder.build());
                    }
            } catch (InventoryFullException e){
                sendMessage(getMessageChannel(), "not all looted");
            } catch (NoSuchMonsterException e) {
                sendMessage(getMessageChannel(), "no luck finding a monster");
            }
        }
    }
}

class AnswerManager {
    private ArrayList<MessageProcessingMachine> messageProcessingArrayList = new ArrayList<>();

    public AnswerManager() {
        messageProcessingArrayList.add(new CallHelp());
        messageProcessingArrayList.add(new CallCreation());
        messageProcessingArrayList.add(new CallPing());
        messageProcessingArrayList.add(new CallCreateCharacter());
        messageProcessingArrayList.add(new CallCharTester());
        messageProcessingArrayList.add(new CallCooldowns());
        messageProcessingArrayList.add(new CallShop());
        messageProcessingArrayList.add(new CallI());
        messageProcessingArrayList.add(new CallCharInfo());
        messageProcessingArrayList.add(new CallEquipmentInfo());
        messageProcessingArrayList.add(new CallEquip());
        messageProcessingArrayList.add(new CallUnequip());
        messageProcessingArrayList.add(new CallDrop());
        messageProcessingArrayList.add(new CallSell());
        messageProcessingArrayList.add(new CallGive());

        messageProcessingArrayList.add(new CallRat());

    }

    private boolean selfSending(Message message) {
        try {
            if (message.getAuthor().get().getId().asString().equals("772821811707904022")) {
                return true;
            }
            return false;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
    public void process(Message message) {
        try{
            if (!selfSending(message)) {
                AnsweringHelper.setMessage(message);
                Iterator<MessageProcessingMachine> messageProcessingMachine = messageProcessingArrayList.iterator();
                while (messageProcessingMachine.hasNext()) {
                    //System.out.println("executing...");
                    messageProcessingMachine.next().processMessage();
                }
            }
        }
        catch(InvalidUserException e){
        }
    }
}