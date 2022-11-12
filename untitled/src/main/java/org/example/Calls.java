package org.example;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.time.Instant;
import java.util.*;


class CallGive extends MessageProcessor{
    public void process(){
        try{
            String[] content = getContent().split(" ", 3);
            if (content[0].equals(".give")){
                String itemName = content[2];
                Snowflake secondId = Snowflake.of(content[1]);
                Item item = getCharacter().getInventory().get(itemName);
                getCharacterManager().getCharacterById(secondId).getInventory().add(item);
                getCharacter().getInventory().removeItem(item);
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        //secondId can be null
                        .author(getUserName() + " gives " + itemName + " to " + getUsernameBySnowflake(secondId), null, getUserAvatarUrl())
                        .color(Color.RED)
                        .build();
                sendMessage(embed);
            }
        } catch (NoSuchCharacterException ex) {
            sendMessage("Make a character first!");
        } catch (NumberFormatException e){
            sendMessage("Wrong ID");
        } catch (InventoryFullException e) {
            sendMessage( "Sorry! Can not do! Inventory of receiver is full.");
        } catch (NullPointerException e){
            sendMessage("Sorry! Something went wrong with receiver's name");
        } catch (NoSuchItemException e){
            sendMessage("Sorry! You don't have that item!");
        } catch (Exception e){
            sendMessage("Unknown exception");
        }
    }
}


class CallCharTester extends MessageProcessor{
    public void process(){
        try {
            if (getContent().equals(".tester") && characterNegativeCheck()){
                CharClass charClass = CharClassFactory.createClass("archer");
                CharRace charRace = CharRaceFactory.createRace("human");
                 getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                 getCharacterManager().getCharacterById(getId()).getInventory().add(ManagerItem.STEEL_SHIELD);
                 getCharacterManager().getCharacterById(getId()).getInventory().add(ManagerItem.STEEL_ARMOR);
                 getCharacterManager().getCharacterById(getId()).getInventory().add(ManagerItem.STEEL_SWORD);

                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                    .color(Color.BLUE)
                    .author(getUserName() + " - " + "Character created!", null, getUserAvatarUrl())
                    .timestamp(Instant.now())
                    .addField("Class", charClass.getName(), true)
                    .addField("Race", charRace.getName(), true)
                    .build();
                 sendMessage(embed);
            }
        } catch(Exception e){
        }
    }
}

class CallHelp extends MessageProcessor{
    private String getHelp(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".help")
                .append("\n.tester - create test character")
                .append("\n.create *class* *race* - create character ")
                .append("\n.give *other user's ID* - give an item to someone")
                .append("\n.eq")
                .append("\n.i")
                .append("\n.drop")
                .append("\n.equip")
                .append("\n.unequip")
                .append("\n.ap")
                .append("\n.charinfo")
                .append("\n.forest");

        return stringBuilder.toString();
    }
    public void process(){
        if (getContent().equals(".help")){
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        .title("Available commands")
                        .addField("...", getHelp(), false)
                        .author(getUserName(), null, getUserAvatarUrl())
                        .timestamp(Instant.now())
                        .build();
                sendMessage(embed);
        }
    }
}

class CallCharInfo extends MessageProcessor{
    public void process(){
        if (getContent().equals(".charinfo")){
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
                sendMessage(embed);
            }
        }
    }
}

class CallCreateCharacter extends MessageProcessor {
    private EmbedCreateSpec.Builder getErrorEmbedBuilder() {
        return EmbedCreateSpec.builder()
                .color(Color.BLUE)
                .author(getUserName(), null, getUserAvatarUrl())
                .timestamp(Instant.now());
    }

    public void process() {
        try {
            String[] content = getContent().split(" ", 3);
            if (content[0].equals(".create")) {
                CharClass charClass = CharClassFactory.createClass(content[1]);
                CharRace charRace = CharRaceFactory.createRace(content[2]);
                if (characterNegativeCheck()) {
                    getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                    EmbedCreateSpec.Builder embedBuilder = getErrorEmbedBuilder().title("Character created!")
                        .addField("Class", charClass.getName(), true)
                        .addField("Race", charRace.getName(), true);
                    sendMessage(embedBuilder.build());
                }
            }
        } catch (IllegalCharacterClassException e) {
            sendMessage("Illegal class name!");
        } catch (IllegalCharacterRaceException e) {
            sendMessage("Illegal race name!!");
        } catch (Exception e) {
        }
    }
}

class CallEquip extends MessageProcessor{
    public void process(){
        try{
            String[] content = getContent().split(" ", 2);
            String itemName = content[1];
            if (content[0].equals(".equip")){
                Item itemOn = getCharacter().getInventory().get(itemName);
                Item itemOff = getCharacter().getEquipment().get(itemOn.getWearable());
                if (getCharacter().getEquipment().equip(itemOn)) {
                    getCharacter().getInventory().remove(itemName);
                    getCharacter().getInventory().add(itemOff);
                }
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                    .author(getUserName() + " equipped " + itemOn.getName(), null, getUserAvatarUrl())
                    .color(Color.RED)
                    .build();
                sendMessage(embed);
            }
        } catch (NoSuchItemException e){
            sendMessage("You don't have this item in your inventory!");
        }
        catch (Exception e){}
    }
}

class CallDrop extends MessageProcessor{
    public void process(){
        try{
            String[] content = getContent().split(" ", 2);
            if (content[0].equals(".drop")){
                String itemName = content[1];
                Item itemToDrop = getCharacter().getInventory().get(itemName);
                if (itemToDrop != null){
                    getCharacter().getInventory().remove(itemName);
                    EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .author(getUserName() + " dropped " + itemToDrop.getName(), null, getUserAvatarUrl())
                        .color(Color.RED)
                        .build();
                    sendMessage(embed);
                }
            }
        } catch (NoSuchItemException e){
            sendMessage("You don't have this item in your inventory!");
        } catch (Exception e){
        }
    }
}

class CallUnequip extends MessageProcessor{
    public void process(){
        try{
            String[] content = getContent().split(" ", 2);
            if (content[0].equals(".unequip")) {
                String itemName = content[1];
                Item item = getCharacter().getEquipment().get(itemName);
                getCharacter().getEquipment().remove(itemName);
                getCharacter().getInventory().add(item);
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                    .author(getUserName() + " unequipped " + item.getName(), null, getUserAvatarUrl())
                    .color(Color.RED)
                    .build();
                sendMessage(embed);
            }
        }
        catch (IndexOutOfBoundsException e){
        } catch (NoSuchItemException e){
            sendMessage("You don't have that item!");
        } catch (Exception e){}
    }
}

class CallSell extends MessageProcessor{
    private void sendMsg(String soldItemName, int soldItemValue){
        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .author(getUserName() + " sold " + soldItemName + " for " + soldItemValue, null, getUserAvatarUrl())
                .color(Color.RED)
                .build();
        sendMessage(embed);
    }

    public void process(){
        try{
            String[] content = getContent().split(" ", 2);
            if (content[0].equalsIgnoreCase(".sell")){
                String itemName = content[1];
                Item itemToSell = getCharacter().getInventory().get(content[1]);

                getCharacter().getInventory().remove(itemName);
                getCharacter().getInventory().setMoney(getCharacter().getInventory().getMoney() + itemToSell.getValue());

                sendMsg(itemToSell.getName(), itemToSell.getValue());
            }
        } catch (NoSuchItemException e){
            sendMessage("You don't have this item!");
        }
        catch (Exception e){}
    }
}

class CallPing extends MessageProcessor{
    public void process(){
        if (getContent().equals(".ping")){
            sendMessage("pong");
        }
    }
}

class CallCooldowns extends MessageProcessor{
    final static int AP_MAX_CHAR = 20;
    public void process(){
        if (getContent().equals(".ap")){
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
                sendMessage(embed);
            }
        }
    }
}

class CallShop extends MessageProcessor{
    public void process(){
        if (getContent().equals(".shop")){
            if (characterCheck()) {
                sendMessage(getShop().itemsAvailable());
            }
        }
    }
}

class CallI extends MessageProcessor{
    final static int INV_MAX_CHAR = 15;
    public void process(){
        if (getContent().equals(".i")){
            if (characterCheck()) {
                EmbedCreateSpec.Builder embedBuilder = EmbedCreateSpec.builder()
                        .color(Color.BROWN)
                        .author(getUserName() + " : Inventory (" + getCharacter().getInventory().getSize() + "/" + Inventory.MAX_ITEM_NUMBER + ")", null, getUserAvatarUrl())
                        .addField("Total"
                                , ":coin: " + getCharacter().getInventory().getMoney()
                                        + " :scales: " + getCharacter().getInventory().getItemsWeight(), false)
                        .thumbnail("https://openclipart.org/image/800px/330656");
                for (Item item : getCharacter().getInventory().getItemList()){
                    embedBuilder.addField(addSpaces(item.getName(), INV_MAX_CHAR), getItemInfo(item), true);
                }
                sendMessage(embedBuilder.build());
            }
        }
    }
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
}

class CallEquipmentInfo extends MessageProcessor{
    public void process(){
        if (getContent().equals(".eq")){
            if (characterCheck()){
                EmbedCreateSpec embed = EmbedCreateSpec.builder()
                        .color(Color.BLUE)
                        .addField(addSpaces("Head", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getHeadEquipment()), true)
                        .addField(addSpaces("Torso", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getTorsoEquipment()), true)
                        .addField(addSpaces("Legs", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getLegsEquipment()), true)
                        .addField(addSpaces("Feet", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getFeetEquipment()), true)
                        .addField(addSpaces("Hands", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getHandsEquipment()), true)
                        .addField(addSpaces("1st hand", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getFirstHandEquipment()), true)
                        .addField(addSpaces("2nd hand", EQ_MAX_CHAR), getEmbedStats(getCharacter().getEquipment().getSecondHandEquipment()), true)
                        .addField("Total", ":shield: " + getCharacter().getEquipment().getTotalDefence() + "\n:axe: " + getCharacter().getEquipment().getTotalAttack() + "\n:scales: " + getCharacter().getEquipment().getTotalWeight(), true)
                        .author(getUserName() + " - Equipment", null, getUserAvatarUrl())
                        .timestamp(Instant.now())
                        .build();
                sendMessage(embed);
            }
        }
    }
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
}

class CallRat extends MessageProcessor{
    public void process(){
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
                    sendMessage(embedBuilder.build());
                }
            } catch (InventoryFullException e){
                sendMessage("not all looted");
            } catch (NoSuchMonsterException e) {
                sendMessage("no luck finding a monster");
            }
        }
    }
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

    private Dungeon getDungeon(String command){
        switch (command){
            case ".forest":
                return ManagerDungeon.FOREST;
            case ".lake":
                return ManagerDungeon.LAKE;
            case ".chest":
                return ManagerDungeon.CHESTPLACE;
            case ".server":
                return ManagerDungeon.SERVER;
            case ".cave":
                return ManagerDungeon.CHICKEN_CAVE;
            default:
                return null;
        }
    }
}

class AnswerManager {
    private ArrayList<MessageProcessor> messageProcessorList = new ArrayList<>();

    public AnswerManager() {
        messageProcessorList.add(new CallHelp());
        messageProcessorList.add(new CallPing());
        messageProcessorList.add(new CallCreateCharacter());
        messageProcessorList.add(new CallCharTester());
        messageProcessorList.add(new CallCooldowns());
        messageProcessorList.add(new CallShop());
        messageProcessorList.add(new CallI());
        messageProcessorList.add(new CallCharInfo());
        messageProcessorList.add(new CallEquipmentInfo());
        messageProcessorList.add(new CallEquip());
        messageProcessorList.add(new CallUnequip());
        messageProcessorList.add(new CallDrop());
        messageProcessorList.add(new CallSell());
        messageProcessorList.add(new CallGive());
        messageProcessorList.add(new CallRat());
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
                MessageProcessor.setMessage(message);
                Iterator<MessageProcessor> messageProcessingMachine = messageProcessorList.iterator();
                while (messageProcessingMachine.hasNext()) {
                    //System.out.println("executing...");
                    messageProcessingMachine.next().process();
                }
            }
        }
        catch(InvalidUserException e){
        }
    }
}