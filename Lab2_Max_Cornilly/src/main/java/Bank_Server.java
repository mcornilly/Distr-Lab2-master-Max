import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Bank_Server {
    HashMap<String,Bank_Account> bankUser; //link account with name of a user

    public Bank_Server() {
        //Add accounts
        this.bankUser = new HashMap<>();
        this.bankUser.put("Bavo",new Bank_Account("Account_Bavo",1000));
        this.bankUser.put("Magalie", bankUser.get("Bavo"));
        this.bankUser.put("Oliver",new Bank_Account("Account_Oliver",500));
        this.bankUser.put("Famke", bankUser.get("Oliver"));
        this.bankUser.put("Max",new Bank_Account("Account_Max",500));
        this.bankUser.put("Beverley", bankUser.get("Max"));
        this.bankUser.put("Jeoffrey",new Bank_Account("Account_Jeoffrey",0));
    }

    public void startServer() throws IOException { // Start the REST_server
        REST_Server server = new REST_Server();
        server.addContext("/bank", new bank_API(bankUser)); //add new API
        server.start();
    }

    private class bank_API extends REST_Handler { //this API handles the put and get requests
        HashMap<String, Bank_Account> bank;

        public bank_API(HashMap<String, Bank_Account> bank) { //hasmap that links name with account
            this.bank = bank;
        }

        @Override
        String getURI(String _URI) { //get request
            String[] URI_tree = _URI.split("/"); // split the uri so /bank/user
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator(); // iterate over the items of the URI
            long balance = 0;
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (next.equals("")) continue; //empty so continue
                if (next.equals("users") && iterator.hasNext()) { //after bank we know there is "user"
                    String user = iterator.next(); //we know this is the user because of our Context
                    if (!bank.containsKey(user))
                        return null; // If the user does not exist => maybe write 'user doesn't exist'
                    balance = bank.get(user).getBalance(); //get balance from Bank_Account

                }
            }
            return Long.toString(balance); // Return the balance
        }
        @Override
        String putURI(String _URI, String body) {
            _URI = _URI.substring(1); //remove first '/'
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();
            long balance = 0;
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (next.equals("")) continue;
                if (next.equals("users") && iterator.hasNext()) {
                    String user = iterator.next();
                    if (!bank.containsKey(user)) {
                        return null; // If the user does not exist => maybe write 'user doesn't exist'
                    }
                    balance = bank.get(user).putBalance(Long.parseLong(body));
                }
            }
            return Long.toString(balance);
        }
        @Override
        String postURI(String _URI, String value) {
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();
            String user;
            try{
                while (iterator.hasNext()){
                    String next = iterator.next();
                    if (next.equals("")) continue;
                    else if (next.equals("bank")) continue;
                    else if (next.equals("users")){
                        user = getUserString(iterator);
                        if (this.bank.containsKey(user)) return null;
                        this.bank.put(user,new Bank_Account(user,Long.parseLong(value)));
                        return Long.toString(this.bank.get(user).getBalance());
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
            return null;
        }
        @Override
        boolean deleteURI(String _URI) {
            String[] URI_tree = _URI.split("/");
            Iterator<String> iterator = Arrays.stream(URI_tree).iterator();
            String user = "";
            try{
                while (iterator.hasNext()){
                    String next = iterator.next();
                    if (next.equals("")) continue;
                    else if (next.equals("bank")) continue;
                    else if (next.equals("users")){
                        user = getUserString(iterator);
                        if (!this.bank.containsKey(user)) return false;
                        this.bank.remove(user);
                        return true;
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
            return false;
        }
        private String getUserString(Iterator<String> uri) throws IOException {
            if (uri.hasNext()){
                return uri.next();
            }else{
                throw new IOException();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        System.out.println("Starting BankServer");
        Bank_Server bankServer = new Bank_Server();
        bankServer.startServer();
    }
}
