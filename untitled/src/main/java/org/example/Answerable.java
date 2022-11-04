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

//class MessageProcessingMachineDecorator extends MessageProcessingMachine{
//    private MessageProcessingMachine
//}


class InvalidUserException extends Exception{
    public InvalidUserException(String message){
        super(message);
    }
}

abstract class AnsweringHelper implements MessageProcessingMachine{
    private static String content;
    private static MessageChannel channel;
    private static Snowflake id;
    private static Character character;
    private static CharacterManager characterManager;
    private static String userName;
    private static String userNameId;
    private static String userAvatarUrl;
    private static DiscordClient discordClient;


    public User getUserBySnowflake(Snowflake id){
        try{
            return discordClient.getUserById(id).block();
        } catch (Exception e){
            System.out.println("exception when catching user");
        }
    }

    public static void setDiscordClient(DiscordClient discordClient) {
        AnsweringHelper.discordClient = discordClient;
    }

    public static void setCharacterManager(CharacterManager characterManager) {
        AnsweringHelper.characterManager = characterManager;
    }

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
    ///
    private static Shop shop;
    public static void setShops(Shop shop) {
        AnsweringHelper.shop = shop;
    }
    public static Shop getShop(){return AnsweringHelper.shop;}
    ////
    protected static String getUserAvatarUrl(){return userAvatarUrl;}
    protected static String getUserNameId(){return userNameId;}
    protected static String getUserName(){return userName;}
    protected static String getContent(){return content;}
    protected static MessageChannel getMessageChannel(){return channel;}
    protected static Snowflake getId(){return id;}
    protected static Character getCharacter(){return character;}
    protected static CharacterManager getCharacterManager(){return characterManager;}

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
}


class CallGive extends AnsweringHelper{
    public void processMessage(){
        try{
            String[] splitString = getContent().split(" ", 3);
            if (splitString[0].equals("?give")){
                try{
                    String itemName = splitString[2];
                    System.out.println(splitString[0] + " : " + (splitString[1]) + " : " + (splitString[2]));

                    String firstId = getId().asString();
                    System.out.println("first id: " + firstId);
                    Snowflake secondId = Snowflake.of(splitString[1]);
                    System.out.println("second id: " + secondId);
                    Character receivingCharacter = getCharacterManager().getCharacterById(secondId);
                    System.out.println("second character loaded");
                    if (receivingCharacter == null){
                        System.out.println("null char");
                    }
                    Item itemToMove = getCharacter().getInventory().getItemByName(itemName);

                    User user;

                    if (itemToMove == null)
                        System.out.println("itemToEquip is == null");
                    else if (itemToMove != null){
                        System.out.println("itemToMove name: " + itemName);
                        receivingCharacter.getInventory().addItem(itemToMove);
                        System.out.println("added item " + itemToMove.getName() + " from " + secondId);
                        getCharacter().getInventory().removeItem(itemToMove);
                        System.out.println("removed item" + itemToMove.getName() + " from " + getUserName());
                        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                                .author(getUserName() + " gives " + itemToMove.getName() + " to " + "<@" + splitString[1] + ">", null, getUserAvatarUrl())
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
                }
            }
        }
        catch (Exception e){
            sendMessage(getMessageChannel(),"some exception");
        }
    }
}


class CallCharTester extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?tester") && characterNegativeCheck()){
            try {
                CharClass charClass = CharClassFactory.createClass("archer");
                CharRace charRace = CharRaceFactory.createRace("human");
                     getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                     getCharacterManager().getCharacterById(getId()).getInventory().addItem(ItemManager.STEEL_SHIELD);
                     getCharacterManager().getCharacterById(getId()).getInventory().addItem(ItemManager.STEEL_ARMOR);
                     getCharacterManager().getCharacterById(getId()).getInventory().addItem(ItemManager.DOLPHIN_FIN);
                     getCharacterManager().getCharacterById(getId()).getInventory().addItem(ItemManager.SHEEP_WOOL);
                     sendMessage(getMessageChannel(), "Creating a character for you... \nrace: "
                          + charRace.getName() + "...,\nclass: " + charClass.getName() + "... \nadding items ... done!");
            } catch(Exception IllegalArgumentException){}
        }
    }
}

class CallHelp extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?help")){
            sendMessage(getMessageChannel(), "Available commands: ?help, ?created, ?ping, ?eq, :dolphin:");
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


class CallExperience extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?exp")){
            sendMessage(getMessageChannel(), "0");
        }
    }
}

class CallCharInfo extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?charinfo")){
            if (characterCheck()){
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        //.title("Action points")
                        .addField("Class ", getCharacter().getCharClass().getName(), true)
                        .addField("Race ", getCharacter().getCharRace().getName(), true)
                        .addField("Action Points ", String.valueOf(getCharacter().getActionPoints().getCurrentAP()) + "/" + ActionPoints.MAX_AP, true)
                        .author(getUserName(), null, getUserAvatarUrl())
                        //.thumbnail("https://openclipart.org/image/800px/330656")
                        .timestamp(Instant.now())
                        .build();
                sendMessage(getMessageChannel(), embed);
            }
        }
    }
}


class CallBodyParts extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?bodyparts")){
            if (characterCheck())
                sendMessage(getMessageChannel(), getCharacter().getBody().getHead().getBodyPartName() + ", " + getCharacter().getBody().getTorso().getBodyPartName()
                        + ", " + getCharacter().getBody().getLegs().getBodyPartName() + ", " + getCharacter().getBody().getFeet().getBodyPartName());
        }
    }
}

class CallCreateCharacter extends AnsweringHelper{
    public void processMessage(){
        try{
            String[] splitString = getContent().split(" ");
            if (splitString[0].equals("?create")){
                try{
                    CharClass charClass = CharClassFactory.createClass(splitString[1]);
                    CharRace charRace = CharRaceFactory.createRace(splitString[2]);

                    if (characterNegativeCheck()){
                        getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                        sendMessage(getMessageChannel(), "Creating a character for you... race: "
                             + charRace.getName() + "..., class: " + charClass.getName() + "... done!");
                    }

                } catch (IllegalCharacterClassException e) {
                    sendMessage(getMessageChannel(), "Incorrect creation command! Illegal class name! Try:\n?'create <class: *knight*, *archer*, *mage*> <race: *human*, *elf*, *orc*>'\nExample: '*?create archer orc*'");
                } catch (IllegalCharacterRaceException e){
                        sendMessage(getMessageChannel(), "Incorrect creation command!  Illegal race name! Try:\n'?create <class: *knight*, *archer*, *mage*> <race: *human*, *elf*, *orc*>'\nExample: '*?create archer orc*'");
                } catch (ArrayIndexOutOfBoundsException e){
                        sendMessage(getMessageChannel(), "Incorrect creation command! Not enough arguments! Try:\n'?create <class: *knight*, *archer*, *mage*> <race: *human*, *elf*, *orc*>'\nExample: '*?create archer orc*'");
                } catch (Exception e){
                    sendMessage(getMessageChannel(), "Incorrect command! Try:\n?create <class> <race>\nExample: *?create archer orc*");
                }
            }
        }
        catch (Exception e){}
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
                        StringBuilder stringBuilder = new StringBuilder();

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



class CallLootChest extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?loot")){
            if (characterCheck()) {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    getCharacter().getActionPoints().addCooldown(2); //not Enough AP exception
                    stringBuilder.append("You use 2AP (" + getCharacter().getActionPoints().getCurrentAP() + "/" + ActionPoints.MAX_AP + "AP left).");
                    ArrayList<Item> newItems = new Chest().loot();
                    stringBuilder.append("\nLoot:\n" + LootingHelper.getItemNamesInString(newItems));
                    getCharacter().getInventory().addItems(newItems); //Inventory full exception
                } catch(NotEnoughActionPointsException e){
                    stringBuilder.append("Not enough action points! You need " + e.getMessage() + "AP to continue. Use *?ap* command to check your status.");
                } catch (InventoryFullException e){
                    stringBuilder.append("Inventory is full! Can't take everything from the chest!");
                } finally {
                    sendMessage(getMessageChannel(), stringBuilder.toString());
                }
            }
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

                /*
                //AP taken without checks yet
                sendMessage(getMessageChannel(), "Action points: " + getCharacter().getActionPoints().getCurrentAP() + "/" + ActionPoints.MAX_AP
                        //+ ".\nCooldowns (seconds): " + getCharacter().getActionPoints().getStringCooldowns()
                        + ".\nAP recovery time (each): " + getCharacter().getActionPoints().AP_RECOVERY_TIME
                        + ".\nAction points available in: " + getCharacter().getActionPoints().getFirstCD() + " (next), "
                        + getCharacter().getActionPoints().getLastCD() + " (all)."
                        //+ ".\nAll AP available in: " + getCharacter().getActionPoints().getLastCD()
                 */


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
                        //.title("Inventory (" + getCharacter().getInventory().getSize() + "/" + Inventory.MAX_ITEM_NUMBER + ")")
                        .addField("Total"
                                //, ":coin: " + getCharacter().getInventory().getItemsValue()
                                , ":coin: " + getCharacter().getInventory().getMoney()
                                        + " :scales: " + getCharacter().getInventory().getItemsWeight(), false)
                        //.addField("Total"
                        //        , ":coin: " + getCharacter().getInventory().getItemsValue()
                        //        + "\n:scales: " + getCharacter().getInventory().getItemsWeight(), false)
                        .author(getUserName() + " : Inventory (" + getCharacter().getInventory().getSize() + "/" + Inventory.MAX_ITEM_NUMBER + ")", null, getUserAvatarUrl())
                        .thumbnail("https://openclipart.org/image/800px/330656");
                //.timestamp(Instant.now());
                for (Item item : getCharacter().getInventory().getItemList()){
                    embedBuilder.addField(addSpaces(item.getName(), INV_MAX_CHAR), getItemInfo(item), true);
                }
                sendMessage(getMessageChannel(), embedBuilder.build());
            }
        }
    }
}

class CallEquipmentInfo extends AnsweringHelper{
    final static int EQ_MAX_CHAR = 15;
    private String getEmbedStats(Item item){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n" + item.getName());
        stringBuilder.append("\n:coin: " + item.getValue());
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
                        .addField(addSpaces("First hand", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getFirstHandEquipment()), true)
                        .addField(addSpaces("Second hand", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getSecondHandEquipment()), true)
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


class AnswerManager {
    private ArrayList<MessageProcessingMachine> messageProcessingArrayList = new ArrayList<>();

    public AnswerManager() {
        messageProcessingArrayList.add(new CallHelp());
        messageProcessingArrayList.add(new CallCreation());
        messageProcessingArrayList.add(new CallPing());
        messageProcessingArrayList.add(new CallExperience());
        messageProcessingArrayList.add(new CallCreateCharacter());
        messageProcessingArrayList.add(new CallBodyParts());
        //messageProcessingArrayList.add(new CallInventory());
        messageProcessingArrayList.add(new CallLootChest());
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

/*
    protected MessageChannel getMessageChannel(Message message){
        return message.getChannel().block();
    }
    protected String getMessageContent(Message message){
        return message.getContent();
    }
*/

//Character character = characterManager.getCharacterById(message.getAuthor().get().getId());
/*
                System.out.println("processing starts...");
                messageProcessingArrayList.forEach((call) -> {
                    System.out.println("executing...");
                    stop = call.processMessage(message, characterManager);
                    if (stop == true) {
                        throw new RuntimeException();
                    }
                });
*/

/*
                Iterator<MessageProcessingMachine> messageProcessingMachine = messageProcessingArrayList.iterator();
                messageProcessingMachine.forEachRemaining(call -> {
                    System.out.println("executing...");
                    if(call.processMessage(message, characterManager)){
                        throw new RuntimeException();
                    };
                });

 */


//for (MessageProcessingMachine messageProcessingMachine : messageProcessingArrayList) {
//    System.out.println("executing...");
//    if (messageProcessingMachine.processMessage(message, characterManager)) {
//         break;
//    }
//}
//System.out.println("processing finishes...");



/*

abstract class Answer{
    abstract MessageChannel getMessageChannel(Message message);
    abstract String getMessageString(Message message);
    public void send(Message message){
        getMessageChannel().createMessage(getMessageString(message)).block();
    }
}
class AnswerHelp extends Answer{
    MessageChannel getMessageChannel(Message message){
        return message.getChannel().block();
    }
    String getMessageString(Message message){
        return "Available commands: ?help, ?created, ?ping, ?eq";
    }
}
class AnswerCreation extends Answer {
    MessageChannel getMessageChannel(Message message){
        return message.getChannel().block();
    }
    String getMessageString(Message message){
        return "The bot was created in 2022!";
    }
}

class AnswerPing extends Answer {
    MessageChannel getMessageChannel(Message message){
        return message.getChannel().block();
    }
    String getMessageString(Message message){
        return "Pong!";
    }
}

class AnswerManager{

    public static void sendAnswer(Message message){
        if (message.getContent().equals("?help")) {
            Answer answer = new AnswerHelp();
            answer.send(message);
        }
        else if (message.getContent().equals("?ping")) {
            Answer answer = new AnswerPing();
            answer.send(message);
        }
        else if (message.getContent().equals("?created")) {
            Answer answer = new AnswerCreation();
            answer.send(message);
        }
        //answer.send(message) here instead of all above
    }
}


/*
class CallInventory extends AnsweringHelper{
    public void processMessage(){
        if (getContent().equals("?inventory")){
            if (characterCheck()) {
                StringBuilder inventoryNamesOutput = new StringBuilder(1023);
                inventoryNamesOutput.append("Inventory (" + getCharacter().getInventory().getSize() + "/" + Inventory.MAX_ITEM_NUMBER + ") (w - weight, v - value): \n");
                for (String string : getCharacter().getInventory().getItemNamesWeightValues()) {
                    inventoryNamesOutput.append(string + ",\n");
                }
                inventoryNamesOutput.append("Total (w: " + getCharacter().getInventory().getItemsWeight());
                inventoryNamesOutput.append(", v: " + getCharacter().getInventory().getItemsValue() + ")");
                sendMessage(getMessageChannel(), inventoryNamesOutput.toString());
            }
        }
    }
}
 */






/*
class AnswerAAA extends Answer {

    MessageChannel getMessageChannel(Message message){
        return message.getChannel().block();
    }
    String getMessageString(Message message){
        return "Pong!";
    }
}
*/
//AnswerFactory.sendAnswer(message);






/*
class AnswerHelp implements Answerable {
    public String answer(Message message){
        if ("?help".equals(message.getContent())){
            final MessageChannel channel = message.getChannel().block();
            return "Available commands: ?help, ?created, ?ping, ?eq";
        }
        else return null;
    }
}


class AnswerCreation implements Answerable {
    public String answer(Message message){
        if ("?created".equals(message.getContent())){
            final MessageChannel channel = message.getChannel().block();
            return "The bot was created in 2022!";
        }
        else return null;
    }
}

class AnswerPing implements Answerable {
    public String answer(Message message){
        if ("?ping".equals(message.getContent())){


            final MessageChannel channel = message.getChannel().block();
            channel.createMessage("Pong!").block();


            SteelHelmet steelHelmet = new SteelHelmet();
            Dressable head = new Head();
            head.dress(steelHelmet);
        }
    }
}

class AnswerEquipment implements Answerable {
    public String answer(Message message){
        if ("?eq".equals(message.getContent())){
            final MessageChannel channel = message.getChannel().block();
            //channel.createMessage("Pong!").block();

            try {
                Snowflake userId = message.getAuthor().get().getId();

            }
            catch (NoSuchElementException e){

            }

            SteelHelmet steelHelmet = new SteelHelmet();
            Dressable head = new Head();
            head.dress(steelHelmet);

            channel.createMessage(head.dress(steelHelmet)).block();
        }
    }
}
*/




/*
class MessageManagerrr{

    private Message message;
    private MessageChannel channel;
    private String outputString;

    public void setInputMessage(Message message){
        this.message = message;
    }
    public void setChannel(MessageChannel channel){
        this.channel = channel;
    }
    public void setOutputString(String outputString){
        this.outputString = outputString;
    }

    //MessageChannel getMessChannel(Message message){
    //    return message.getChannel().block();
    //}

    String getResponse(Answerable answerable){
        return answerable.answer(message);
    }

    public void createMessage(){
        channel.createMessage(outputString).block();
    }
}
 */



/*
class AnswerHelp implements Answerable {
    public void answer(Message message){
        if ("?help".equals(message.getContent())){
            final MessageChannel channel = message.getChannel().block();
            channel.createMessage("Available commands: ?help, ?created, ?ping, ?eq").block();
        }
    }
}


class AnswerCreation implements Answerable {
    public void answer(Message message){
        if ("?created".equals(message.getContent())){
            final MessageChannel channel = message.getChannel().block();
            channel.createMessage("The bot was created in 2022!").block();
        }
    }
}
class AnswerPing implements Answerable {
    public void answer(Message message){
        if ("?ping".equals(message.getContent())){


            final MessageChannel channel = message.getChannel().block();
            channel.createMessage("Pong!").block();


            SteelHelmet steelHelmet = new SteelHelmet();
            Dressable head = new Head();
            head.dress(steelHelmet);
        }
    }
}

class AnswerEquipment implements Answerable {
    public void answer(Message message){
        if ("?eq".equals(message.getContent())){
            final MessageChannel channel = message.getChannel().block();
            //channel.createMessage("Pong!").block();

            try {
                Snowflake userId = message.getAuthor().get().getId();

            }
            catch (NoSuchElementException e){

            }

            SteelHelmet steelHelmet = new SteelHelmet();
            Dressable head = new Head();
            head.dress(steelHelmet);

            channel.createMessage(head.dress(steelHelmet)).block();
        }
    }
}
*/