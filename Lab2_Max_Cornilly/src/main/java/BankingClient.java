import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class BankingClient {
    //HERE WE TEST OUR SERVER / CLIENT
    static String getBalance(String user) throws UnirestException {
        String balance;
        //String localURL = "http://localhost:8080/BankingServer/users/" + user;
        //balance = Unirest.get(localURL).asString().getBody();
        String URL = "http://192.168.80.3:8080/BankingServer/users/" + user;
        balance = Unirest.get(URL).asString().getBody();
        return balance;
    }
    static String putBalance(String user, int money) throws UnirestException {
        String balance;
        //String localURL = "http://localhost:8080/BankingServer/users/" + user;
        //Unirest.put(localURL).header("Content-Type", "application/json").body(Integer.toString(money)).asString();
        //balance = Unirest.get(localURL).asString().getBody();
        String URL = "http://192.168.80.3:8080/BankingServer/users/" + user;
        Unirest.put(URL).header("Content-Type", "application/json").body(Integer.toString(money)).asString();
        balance = Unirest.get(URL).asString().getBody();
        return balance;
    }
    static String newUser(String user, int money) throws UnirestException {
        String balance;
        //String localURL = "http://localhost:8080/BankingServer/users/" + user;
        //Unirest.post(localURL).header("Content-Type", "application/json").body(Integer.toString(money)).asString();
        //balance = Unirest.get(localURL).asString().getBody();
        String URL = "http://192.168.80.3:8080/BankingServer/users/" + user;
        Unirest.post(URL).header("Content-Type", "application/json").body(Integer.toString(money)).asString();
        balance = Unirest.get(URL).asString().getBody();
        return balance;
    }
    static void deleteUser(String user) throws UnirestException {
        //String localURL = "http://localhost:8080/BankingServer/users/" + user;
        //Unirest.delete(localURL).asString();
        String URL = "http://192.168.80.3:8080/BankingServer/users/" + user;
        Unirest.delete(URL).asString();
    }
    public static void main(String[] args) throws IOException, UnirestException {
        String name;
        if(args.length > 0){
            name = args[0];
        }else{
            System.out.println("Please give a name to your client!");
            return;
        }
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger("org.apache.http");
        root.setLevel(ch.qos.logback.classic.Level.OFF);
        System.out.println("Starting BankingClient...");
        System.out.println("New Client Bavo: POST /bank/users/bavo with body: " + BankingClient.newUser(name, 100));
        System.out.println("PUT /bank/users/bavo 100 , balance: " + BankingClient.putBalance(name,100));
        System.out.println("GET /bank/users/bavo, balance: " + BankingClient.getBalance(name));
        System.out.println("DELETE /bank/users/bavo");
        BankingClient.deleteUser(name);

    }
}