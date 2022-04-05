package BankApp;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class BankingServer {

    HashMap<String, BankingAccount> bankUser;

    public BankingServer(){
        this.bankUser = new HashMap<>();
        this.bankUser.put("Bavo",new BankingAccount("Bavo",1000));
    }
    @GetMapping("/BankingServer/users/{user}")
    public String getBalance(@PathVariable(value = "user") String user) {
        long balance;
        if (bankUser.containsKey(user)) {
            balance = bankUser.get(user).getBalance();
            return "User " + user + " has balance: " + balance;
        }
        return "User " + user + " doesn't exist";

    }
    @PutMapping("BankingServer/users/{user}")
    public String putBalance(@PathVariable(value = "user") String user, @RequestBody String money) {
        long balance;
        if (bankUser.containsKey(user)) {
            balance = bankUser.get(user).putBalance(Integer.parseInt(money)) ;
            return "User " + user + " new balance: " + balance;
        }
        return "User " + user + " doesn't exist";
    }
    @PostMapping("BankingServer/users/joint/{user}")
    public String jointAccount(@PathVariable(value = "user") String user, @RequestBody String jointAccount){
        long balance;
        if(!bankUser.containsKey(user)){
            bankUser.put(user, bankUser.get(jointAccount));
        }
        balance = bankUser.get(user).getBalance();
        return "User " + user + " Added to " + jointAccount + " with balance: " + balance;
    }

    @PostMapping("BankingServer/users/{user}")
    public String newUser(@PathVariable(value = "user") String user, @RequestBody int money){
        long balance;
        if(!bankUser.containsKey(user)){
            bankUser.put(user, new BankingAccount(user, money));
        }
        balance = bankUser.get(user).getBalance();
        return "User " + user + " Added with balance: " + balance;
    }
    @DeleteMapping("BankingServer/users/{user}")
    public String deleteUser(@PathVariable(value = "user") String user) {
        if (bankUser.containsKey(user)) {
            bankUser.remove(user);
            return "User " + user + " Removed";
        }
        return "User " + user + " was not removed because he doesn't exist";
    }
}
