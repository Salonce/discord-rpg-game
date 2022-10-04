/*package org.example;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;

public class ServerConnection {
    final String token;
    final DiscordClient client;
    final GatewayDiscordClient gateway;
    
    public ServerConnection(String token){
        this.token = token; 
        this.client = DiscordClient.create(token);
        this.gateway = client.login().block();
    }
    public GatewayDiscordClient getGateway(){
        return this.gateway;
    }


}
*/