package org.example;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.ArrayList;
import java.util.Iterator;


interface MessageProcessingMachine{
    boolean processMessage();
}

//class MessageProcessingMachineDecorator extends MessageProcessingMachine{
//    private MessageProcessingMachine
//}


abstract class AnsweringHelper implements MessageProcessingMachine{
    //public static Message getMessage() {return message;}
    private static Message message;
    public static void setMessage(Message message) {AnsweringHelper.message = message;
        setContent(message.getContent());
        setChannel(message.getChannel().block());

        try {
            setId(message.getAuthor().get().getId());
        }
        catch(Exception e){
            setId(null);
        }

        try {
            setCharacter(characterManager.getCharacterById(message.getAuthor().get().getId()));
        } catch (Exception e) {
            setCharacter(null);
        }
    }

    public static void setCharacterManager(CharacterManager characterManager) {
        AnsweringHelper.characterManager = characterManager;
    }

    private static String content;
    private static MessageChannel channel;
    private static Snowflake id;
    private static Character character;
    private static CharacterManager characterManager;

    protected static String getContent(){return content;};
    protected static MessageChannel getMessageChannel(){return channel;}
    protected static Snowflake getId(){return id;}
    protected static Character getCharacter(){return character;}
    protected static CharacterManager getCharacterManager(){return characterManager;}

    private static void setContent(String content) {AnsweringHelper.content = content;}
    private static void setChannel(MessageChannel channel) {AnsweringHelper.channel = channel;}
    private static void setId(Snowflake id) {AnsweringHelper.id = id;}
    private static void setCharacter(Character character) {
        try{
            AnsweringHelper.character = character;
        } catch (Exception e){
            sendMessage(channel, "Character doesn't exist");
            AnsweringHelper.character = null;
        }
    }
    protected static void sendMessage(MessageChannel messageChannel, String message){
        messageChannel.createMessage(message).block();
    }
}

class CallCharTester extends AnsweringHelper{
    public boolean processMessage(){
        if (super.getContent().equals("?tester")){
            if (getCharacter() != null) {
                CharClass charClass = CharClassFactory.createClass("archer");
                CharRace charRace = CharRaceFactory.createRace("human");
                if (charClass != null && charRace != null) {
                    if (getCharacter() == null) {
                        getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                        getCharacterManager().getCharacterById(getId()).getInventory().addItem(new SteelShield());
                        getCharacterManager().getCharacterById(getId()).getInventory().addItem(new SteelArmor());
                        getCharacterManager().getCharacterById(getId()).getInventory().addItem(new DolphinFin());
                        sendMessage(getMessageChannel(), "Creating a character for you... \nrace: "
                                + charRace.getName() + "...,\nclass: " + charClass.getName() + "... \nadding items ... done!");
                    } else {
                        sendMessage(getMessageChannel(), "You already have a character!");
                    }
                }
            }
            return true;
        }
        else return false;
    }
}

class CallHelp extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?help")){
            sendMessage(getMessageChannel(), "Available commands: ?help, ?created, ?ping, ?eq, :dolphin:");
            return true;
        }
        else{
            return false;
        }
    }
}

class CallCreation extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?created")){
            sendMessage(getMessageChannel(), "The bot was created in 2022!");
            return true;
        }
        else return false;
    }
}

class CallPing extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?ping")){
            sendMessage(getMessageChannel(), "Pong!");
            return true;
        }
        else return false;
    }
}

class CallExperience extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?exp")){
            sendMessage(getMessageChannel(), "0");
            return true;
        }
        else return false;
    }
}

class CallRace extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?race")){
            if (getCharacter() != null)
                sendMessage(getMessageChannel(), getCharacter().getCharRace().getName());
            return true;
        }
        else return false;
    }
}

class CallClass extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?class")){
            if (getCharacter() != null)
                sendMessage(getMessageChannel(), getCharacter().getCharClass().getName());
            return true;
        }
        else return false;
    }
}

class CallBodyParts extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?bodyparts")){
            if (getCharacter() != null)
                sendMessage(getMessageChannel(), getCharacter().getBody().getHead().getBodyPartName() + ", " + getCharacter().getBody().getTorso().getBodyPartName()
                        + ", " + getCharacter().getBody().getLegs().getBodyPartName() + ", " + getCharacter().getBody().getFeet().getBodyPartName());
            return true;
        }
        else return false;
    }
}

class CallCreateCharacter extends AnsweringHelper{
    public boolean processMessage(){
        try{
            String[] splitString = getContent().split(" ");
            String firstString = splitString[0];
            if (firstString.equals("?create")){
                try{
                    String className = splitString[1];
                    String raceName = splitString[2];
                    CharClass charClass = CharClassFactory.createClass(className);
                    CharRace charRace = CharRaceFactory.createRace(raceName);
                    if (charClass != null && charRace != null){
                        if (getCharacter() == null){
                            getCharacterManager().createNewCharacter(getId(), charClass, charRace);
                            sendMessage(getMessageChannel(), "Creating a character for you... race: "
                                    + charRace.getName() + "..., class: " + charClass.getName() + "... done!");
                        }
                        else{
                            sendMessage(getMessageChannel(), "You already have a character!");
                        }
                    }
                } catch (Exception e){
                    sendMessage(getMessageChannel(), "Incorrect command! Try:\n?create <class> <race>\nExample: *?create archer orc*");
                }
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }
}

class CallInventory extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?inventory")){
            if (getCharacter() != null) {
                StringBuilder inventoryNamesOutput = new StringBuilder(1023);
                inventoryNamesOutput.append("Inventory:\n");
                for (String string : getCharacter().getInventory().getItemNamesWeightValues()) {
                    inventoryNamesOutput.append(string + ",\n");
                }
                inventoryNamesOutput.append("Total (weight: " + getCharacter().getInventory().getItemsWeight());
                inventoryNamesOutput.append(" ,value: " + getCharacter().getInventory().getItemsValue() + ")");
                String inventoryNamesFinalString = inventoryNamesOutput.toString();
                sendMessage(getMessageChannel(), inventoryNamesFinalString);
            }
            return true;
        }
        else return false;
    }
}

class CallLootChest extends AnsweringHelper{
    public boolean processMessage(){
        if (getContent().equals("?lootchest")){
            if (getCharacter() != null) {
                Lootable chest = new Chest();
                ArrayList<Item> newItems = chest.loot();
                getCharacter().getInventory().addItems(newItems);
                sendMessage(getMessageChannel(), "Looted from the chest:\n" + LootingHelper.getItemNamesInString(newItems));
            }
            return true;
        }
        else return false;
    }
}

class AnswerManager{
    private ArrayList<MessageProcessingMachine> messageProcessingArrayList = new ArrayList<>();
    public AnswerManager(){
        messageProcessingArrayList.add(new CallHelp());
        messageProcessingArrayList.add(new CallCreation());
        messageProcessingArrayList.add(new CallPing());
        messageProcessingArrayList.add(new CallExperience());
        messageProcessingArrayList.add(new CallRace());
        messageProcessingArrayList.add(new CallClass());
        messageProcessingArrayList.add(new CallCreateCharacter());
        messageProcessingArrayList.add(new CallBodyParts());
        messageProcessingArrayList.add(new CallInventory());
        messageProcessingArrayList.add(new CallLootChest());
        messageProcessingArrayList.add(new CallCharTester());
    }

    private boolean selfSending(Message message){
        try {
            if (message.getAuthor().get().getId().asString().equals("772821811707904022")){
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    private boolean callConditions(Message message){
        if (selfSending(message))
            return false;
        return true;
    }

    public void process(Message message, CharacterManager characterManager){
        AnsweringHelper.setMessage(message);
        try {
            if (callConditions(message)) {
                boolean stop = false;
                Iterator<MessageProcessingMachine> messageProcessingMachine = messageProcessingArrayList.iterator();
                while (messageProcessingMachine.hasNext() && stop == false){
                    System.out.println("executing...");
                    stop = messageProcessingMachine.next().processMessage();
                }
            }
        } catch (RuntimeException e){
            if (e instanceof RuntimeException) {
                System.out.println("message properly processed, runException");
            }
            else System.out.println("can't process the message, someException");
        }
        catch (Exception e){

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