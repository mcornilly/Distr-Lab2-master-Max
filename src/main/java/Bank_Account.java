public class Bank_Account {
    String name;
    long balance;

    public Bank_Account(String name, long balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public synchronized void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount:{" +
                "name:'" + name + '\'' +
                ", balance:" + balance +
                '}';
    }

    public synchronized long putBalance(long value) {
        this.balance += value;
        return this.balance;
    }
}
