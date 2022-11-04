package org.example;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class Main {
    public static void main(String[] args) {
        //log the bot in
        final String token = args[0];
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        //create a list of characters for users
        final AnswerManager answerManager = new AnswerManager();
        final CharacterManager characterManager = new CharacterManager();
        final Shop basicShop = new BasicShop();

        AnsweringHelper.setCharacterManager(characterManager);
        AnsweringHelper.setDiscordClient(client);
        AnsweringHelper.setShops(basicShop);


        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            answerManager.process(event.getMessage());
        });
        gateway.onDisconnect().block();
    }
}


/*
class MessageManager{
    //create a list of characters for users
    HashMap<Snowflake, Character> usersCharacters = new HashMap<>();
    //create a list of answers
    ArrayList<Answerable> answerable = new ArrayList<Answerable>();
    public MessageManager(){
    //create a list of answers
        answerable.add(new AnswerCreation());
        answerable.add(new AnswerPing());
        answerable.add(new AnswerEquipment());
    }
}
*/

//    for (Answerable toAnswer: answerable)
//        toAnswer.answer(message);

//create a list of answers

//ArrayList<Answerable> answerable = new ArrayList<Answerable>();
//    answerable.add(new AnswerCreation());
//    answerable.add(new AnswerPing());
//    answerable.add(new AnswerEquipment());
//    answerable.add(new AnswerHelp());
//send answers on call

/*
abstract class SalonkaBotUser{
    public SalonkaBotUser(long id){
        this.id = id;
    }
    private long id;
    private int numberOfMessages;
    private int timeSpentInVc;
}
*/