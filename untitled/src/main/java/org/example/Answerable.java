package org.example;

import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.util.ArrayList;


interface MessageProcessingMachine{
    boolean processMessage(Message message, CharacterManager characterManager);
}

abstract class AnsweringHelper implements MessageProcessingMachine{
    protected MessageChannel getMessageChannel(Message message){
        return message.getChannel().block();
    }
    protected String getMessageContent(Message message){
        return message.getContent();
    }
    protected void sendMessage(MessageChannel messageChannel, String message){
        messageChannel.createMessage(message).block();
    }

}

class CallCharTester extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?tester")){
            MessageChannel channel = getMessageChannel(message);
            Snowflake id;
            try{
                CharClass charClass = CharClassFactory.createClass("archer");
                CharRace charRace = CharRaceFactory.createRace("human");
                if (charClass != null && charRace != null){
                    id = message.getAuthor().get().getId();
                    Character character = characterManager.getCharacterById(id);
                    if (character == null){
                        characterManager.createNewCharacter(id, charClass, charRace);
                        characterManager.getCharacterById(id).getInventory().addItem(new SteelShield());
                        characterManager.getCharacterById(id).getInventory().addItem(new SteelArmor());
                        characterManager.getCharacterById(id).getInventory().addItem(new DolphinFin());
                        sendMessage(channel, "Creating a character for you... race: "
                                + charRace.getName() + "..., class: " + charClass.getName() + "... adding items ... done!");
                    }
                    else{
                        sendMessage(channel, "You already have a character!");
                    }
                }

            } catch (Exception e){
                //
            }
            return true;
        }
        else return false;
    }
}


class CallHelp extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?help")){
            MessageChannel channel = getMessageChannel(message);
            sendMessage(channel, "Available commands: ?help, ?created, ?ping, ?eq, :dolphin:");
            return true;
        }
        else{
            return false;
        }
    }
}

class CallCreation extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?created")){
            MessageChannel channel = getMessageChannel(message);
            sendMessage(channel, "The bot was created in 2022!");
            return true;
        }
        else return false;
    }
}

class CallPing extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?ping")){
            MessageChannel channel = getMessageChannel(message);
            sendMessage(channel, "Pong!");
            return true;
        }
        else return false;
    }
}

class CallExperience extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?exp")){
            MessageChannel channel = getMessageChannel(message);
            sendMessage(channel, "0");
            return true;
        }
        else return false;
    }
}

class CallRace extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?race")){
            MessageChannel channel = getMessageChannel(message);
            try{
                Character character = characterManager.getCharacterById(message.getAuthor().get().getId());
                sendMessage(channel, character.getCharRace().getName());
            } catch (Exception e){
                sendMessage(channel, "Character doesn't exist");
            }

            return true;
        }
        else return false;
    }
}

class CallClass extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?class")){
            MessageChannel channel = getMessageChannel(message);
            try{
                Character character = characterManager.getCharacterById(message.getAuthor().get().getId());
                sendMessage(channel, character.getCharClass().getName());
            } catch (Exception e){
                sendMessage(channel, "Character doesn't exist");
            }
            return true;
        }
        else return false;
    }
}

class CallBodyParts extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?bodyparts")){
            MessageChannel channel = getMessageChannel(message);
            try{
                Character character = characterManager.getCharacterById(message.getAuthor().get().getId());
                sendMessage(channel, character.getBody().getHead().getBodyPartName() + ", " + character.getBody().getTorso().getBodyPartName()
                        + ", " + character.getBody().getLegs().getBodyPartName() + ", " + character.getBody().getFeet().getBodyPartName());
            } catch (Exception e){
                sendMessage(channel, "Character doesn't exist");
            }
            return true;
        }
        else return false;
    }
}

class CallCreateCharacter extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        try{
            String[] splitString = content.split(" ");
            String firstString = splitString[0];
            if (firstString.equals("?create")){
                MessageChannel channel = getMessageChannel(message);
                Snowflake id;
                try{
                    String className = splitString[1];
                    String raceName = splitString[2];
                    CharClass charClass = CharClassFactory.createClass(className);
                    CharRace charRace = CharRaceFactory.createRace(raceName);
                    if (charClass != null && charRace != null){
                        id = message.getAuthor().get().getId();
                        Character character = characterManager.getCharacterById(id);
                        if (character == null){
                            characterManager.createNewCharacter(id, charClass, charRace);
                            sendMessage(channel, "Creating a character for you... race: "
                                    + charRace.getName() + "..., class: " + charClass.getName() + "... done!");
                        }
                        else{
                            sendMessage(channel, "You already have a character!");
                        }
                    }

                } catch (Exception e){
                    //
                }
                return true;
            }
            //sendMessage(getMessageChannel(message), splitString[0] + " aaa " + splitString[1]);
        }
        catch (Exception e){
            return false;
            //sendMessage(getMessageChannel(message), "can't split it!");
        }
        return false;
    }
}

class CallInventory extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?inventory")){
            MessageChannel channel = getMessageChannel(message);
            try{
                Character character = characterManager.getCharacterById(message.getAuthor().get().getId());
                StringBuilder inventoryNamesOutput = new StringBuilder(1023);
                inventoryNamesOutput.append("Inventory:\n");
                for (String string : character.getInventory().getItemNamesWeightValues()){
                    inventoryNamesOutput.append(string + ",\n");
                }
                inventoryNamesOutput.append("Total (weight: " + character.getInventory().getItemsWeight());
                inventoryNamesOutput.append(" ,value: " + character.getInventory().getItemsValue() + ")");
                String inventoryNamesFinalString = inventoryNamesOutput.toString();
                sendMessage(channel, inventoryNamesFinalString);
            } catch (Exception e){
                sendMessage(channel, "Character doesn't exist");
            }
            return true;
        }
        else return false;
    }
}

class CallLootChest extends AnsweringHelper{
    public boolean processMessage(Message message, CharacterManager characterManager){
        String content = getMessageContent(message);
        if (content.equals("?lootchest")){
            MessageChannel channel = getMessageChannel(message);
            try{
                Lootable chest = new Chest();
                Character character = characterManager.getCharacterById(message.getAuthor().get().getId());
                ArrayList<Item> newItems = chest.loot();
                character.getInventory().addItems(newItems);
                sendMessage(channel, "Looted from the chest:\n" + LootingHelper.getItemNamesInString(newItems));
                } catch (Exception e){
                    sendMessage(channel, "Character doesn't exist");
                }
            return true;
        }
        else return false;
    }
}


class AnswerManager{
    private ArrayList<MessageProcessingMachine> messageProcessingArrayList = new ArrayList<MessageProcessingMachine>();
    boolean stop;
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
        try {
            Character character = characterManager.getCharacterById(message.getAuthor().get().getId());
            if (callConditions(message)) {
                stop = false;
                System.out.println("processing starts...");
                for (MessageProcessingMachine messageProcessingMachine : messageProcessingArrayList) {
                    System.out.println("executing...");
                    stop = messageProcessingMachine.processMessage(message, characterManager);
                    if (stop == true) {
                        break;
                    }
                }
                System.out.println("processing finishes...");
            }
        } catch (Exception e){
            System.out.println("can't process");
        }
    }
}













/*

abstract class Answer{
    abstract MessageChannel getMessageChannel(Message message);
    abstract String getMessageString(Message message);
    public void send(Message message){
        getMessageChannel(message).createMessage(getMessageString(message)).block();
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