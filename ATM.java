package assiment;

import java.util.*;
class AtmDb{
    private int bal;
    private  int dep;
    private int wd;

    public AtmDb() {
    }

    public AtmDb(int bal, int dep, int withdraw) {
        this.bal = bal;
        this.dep = dep;
        this.wd = withdraw;
    }

    public int getBal() {
        return bal;
    }

    public void setBal(int bal) {
        this.bal = bal;
    }

    public int getDep() {
        return dep;
    }

    public void setDep(int dep) {
        this.dep = dep;
    }

    public int getWithDraw() {
        return wd;
    }

    public void setWd(int wd) {
        this.wd = wd;
    }
}
class Customer {
    private  String accountNumber;
    private String customerName;
    private String pinNumber;
    private int accountBalance;


    public Customer(){

    }

    public Customer(String accountNumber, String customerName, String pinNumber, int accountBalance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.pinNumber = pinNumber;
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void withdraw(String accountNumber, int amount, TreeMap<String, Customer> db){
         db.get(accountNumber).setAccountBalance(getAccountBalance()-amount);
    }

    public void deposit(String accountNumber, int amount, TreeMap<String, Customer> db){
        db.get(accountNumber).setAccountBalance(getAccountBalance()+amount);
    }
}
class MangAtm {

    public static void updateDenomination(int amount, int denomination, ManageDenomination handleDenomination){
        if(amount==2000){
                handleDenomination.setTwoThousand(handleDenomination.getTwoThousand()+denomination);
        }
        else if(amount==500){
                handleDenomination.setFiveHundred(handleDenomination.getFiveHundred()+denomination);
        }
        else if(amount==100){
                handleDenomination.setOneHundred(handleDenomination.getOneHundred()+denomination);
        }
    }

    public static int reduceDenomination(int amount, int denomination, ManageDenomination handleDenomination){
        int flag1=0, flag2=0;
        if(amount==2000){
            if(handleDenomination.getTwoThousand()>0){
                handleDenomination.setTwoThousand(handleDenomination.getTwoThousand()-denomination);
                return 1;
            }
            else if(handleDenomination.getFiveHundred()>0){
                flag1=1;
                handleDenomination.setFiveHundred(handleDenomination.getFiveHundred()-denomination);
            }
            else if(handleDenomination.getOneHundred()>0){
                flag2=1;
                handleDenomination.setOneHundred(handleDenomination.getOneHundred()-denomination);
            }
        }
        else if(amount==500){
            if(handleDenomination.getFiveHundred()>0){
                if(flag1==0){
                    handleDenomination.setFiveHundred(handleDenomination.getFiveHundred()-denomination);
                    return 1;
                }
            }
            else if(handleDenomination.getOneHundred()>0)
                if(flag2==0)
                handleDenomination.setOneHundred(handleDenomination.getOneHundred()-denomination);
        }
        else if(amount==100){
            if(handleDenomination.getOneHundred()>0){
                if(flag2==0){
                handleDenomination.setOneHundred(handleDenomination.getOneHundred()-denomination);
                return 1;}
            }
        }
        return -1;
    }

    public static void updateDepositingAmount(AtmDb atmDatabase, ManageDenomination handleDenomination){
        int depositingAmount=handleDenomination.getTwoThousand()*2000+handleDenomination.getFiveHundred()*500+handleDenomination.getOneHundred()*100;
        atmDatabase.setDep(depositingAmount);
        atmDatabase.setBal(atmDatabase.getDep());
    }

    public static void updateWithdraw(AtmDb atmDatabase, int withdrawAmount){
        atmDatabase.setWd(withdrawAmount);
        atmDatabase.setBal(atmDatabase.getBal()-withdrawAmount);
    }

    public static int[] dispensingDenomination(int[] notes, int withdrawAmount){
        int[] noteCounter=new int[notes.length];
        for(int i=0;i<notes.length;i++){
            if(withdrawAmount>=notes[i]){
                noteCounter[i]=withdrawAmount/notes[i];
                withdrawAmount=withdrawAmount-noteCounter[i]*notes[i];
            }
        }
        return noteCounter;
    }

}
class HandleCustomer {

    public boolean validAccountNumber(String accountNumber, TreeMap<String, Customer> db){
        if(accountNumber.equals(db.get(accountNumber).getAccountNumber())){
            return true;
        }
        return false;
    }
    public boolean validPinNumber(String accountNumber, String pinNumber, TreeMap<String, Customer> db){
        if(pinNumber.equals(db.get(accountNumber).getPinNumber())){
            return true;
        }
        return false;
    }


    public void transferAmount(String fromAccountNumber, String toAccountNumber, int amount, TreeMap<String, Customer> db){
        db.get(fromAccountNumber).withdraw(fromAccountNumber, amount, db);
        db.get(toAccountNumber).deposit(toAccountNumber, amount, db);
    }
}
class ManageDenomination {
    private int twoThousand;
    private int fiveHundred;
    private int oneHundred;

    public ManageDenomination() {
    }

    public ManageDenomination(int twoThousand, int fiveHundred, int oneHundred) {
        this.twoThousand = twoThousand;
        this.fiveHundred = fiveHundred;
        this.oneHundred = oneHundred;
    }

    public int getTwoThousand() {
        return twoThousand;
    }

    public void setTwoThousand(int twoThousand) {
        this.twoThousand = twoThousand;
    }

    public int getFiveHundred() {
        return fiveHundred;
    }

    public void setFiveHundred(int fiveHundred) {
        this.fiveHundred = fiveHundred;
    }

    public int getOneHundred() {
        return oneHundred;
    }

    public void setOneHundred(int oneHundred) {
        this.oneHundred = oneHundred;
    }
}

public class Atm {
    public static void main(String[] args) {
        try(Scanner scanner=new Scanner(System.in);) {
            AtmDb atmDatabase=new AtmDb();
            MangAtm handleAtm=new MangAtm();
            HandleCustomer handleCustomer=new HandleCustomer();

            TreeMap<String, Customer> db=new TreeMap<>();
            int[] notes=new int[]{2000, 500, 100};
            ManageDenomination handleDenomination=new ManageDenomination();
            while (true){
                int option=0;
                System.out.println("Choose any Option \n1. Load ATM\n2. Withdraw From ATM\n3. Check ATM Balance\n4. Create Account\n5. Transfer\n6. Check Account Balance\n7. Display All Customer Details\n8. Deposit\n9. End");
                option=scanner.nextInt();
                System.out.println();
                scanner.nextLine();
                switch (option){
                    case 1:
                    {
                        System.out.println("         Load ATM         ");
                        System.out.println("Enter the Denomination to deposit(2000:10, 500:5) : ");
                        String[] denominations=scanner.nextLine().split(",");
                        int flag=1;
                        for(String seperate:denominations){
                            String[] values=seperate.split(":");
                            int amount=Integer.valueOf(values[0].trim());
                            int denomination=Integer.valueOf(values[1]);
                            if(amount<0||denomination<0){
                                System.out.println("Incorrect Deposit amount");
                            }
                            else if (amount==0||denomination==0){
                                System.out.println("Deposit amount cannot be Zero");
                            }
                            else{
                                handleAtm.updateDenomination(amount, denomination, handleDenomination);
                            }
                        }

                            handleAtm.updateDepositingAmount(atmDatabase, handleDenomination);


                        System.out.println("Denomination            Number  Value       ");
                        System.out.println("2000                    "+handleDenomination.getTwoThousand()+"       "+2000*handleDenomination.getTwoThousand());
                        System.out.println("500                     "+handleDenomination.getFiveHundred()+"       "+500*handleDenomination.getFiveHundred());
                        System.out.println("100                     "+handleDenomination.getOneHundred()+"       "+100*handleDenomination.getOneHundred());
                        break;
                    }
                    case 2:
                    {
                        System.out.println("--------    Withdraw    --------");

                        System.out.println("Enter the Account Number : ");
                        String accountNumber=scanner.next();
                        System.out.println("Enter the Pin Number : ");
                        String pinNumber=scanner.next();
                        if(handleCustomer.validAccountNumber(accountNumber, db)&&handleCustomer.validPinNumber(accountNumber, pinNumber, db)){
                            System.out.println("Enter the amount to Withdraw : ");
                            int withdrawAmount=scanner.nextInt();
                            if(withdrawAmount<=0||withdrawAmount>atmDatabase.getBal()){
                                System.out.println("Incorrect or Insufficient Funds");break;
                            }
                            else if(db.get(accountNumber).getAccountBalance()>10000||db.get(accountNumber).getAccountBalance()<100){
                                System.out.println("Withdraw Amount should maximum 10000 and minimum 100");break;
                            }
                            db.get(accountNumber).withdraw(accountNumber, withdrawAmount, db);
                            int flag=1;
                            int[] dispensingDenominations=handleAtm.dispensingDenomination(notes, withdrawAmount);
                            for(int i=0;i< notes.length;i++){
                                if(dispensingDenominations[i]>0){
                                    flag=handleAtm.reduceDenomination(notes[i], dispensingDenominations[i], handleDenomination);
                                }
                            }
                            System.out.println();
                            if(flag==1)
                                handleAtm.updateWithdraw(atmDatabase, withdrawAmount);
                            else {
                                System.out.println("No Available Denomination");
                                break;
                            }
                        }
                        else{
                            System.out.println("Invalid Acoount Number or Pin Number");break;
                        }
                        break;
                    }
                    case 3:
                    {
                        int currentAtmBalance=atmDatabase.getBal();
                        if(currentAtmBalance<=0){
                            System.out.println("No Fund");
                            continue;
                        }
                        else{
                            System.out.println("      Current ATM Balance      ");
                            System.out.println("Denomination            Number  Value       ");
                            System.out.println("2000                    "+handleDenomination.getTwoThousand()+"       "+2000*handleDenomination.getTwoThousand());
                            System.out.println("500                     "+handleDenomination.getFiveHundred()+"       "+500*handleDenomination.getFiveHundred());
                            System.out.println("100                     "+handleDenomination.getOneHundred()+"       "+100*handleDenomination.getOneHundred());
                            System.out.println("Total Amount available in ATM is = Rs. "+atmDatabase.getBal());
                        }
                        break;
                    }
                    case 4:
                    {
                        System.out.println("       Create Savings Account        ");
                        System.out.println("Enter the New Account Number : ");
                        String accountNumber=scanner.nextLine();
                        System.out.println("Enter the Customer Name : ");
                        String customerName=scanner.nextLine();
                        System.out.println("Enter the New Pin Number : ");
                        String pinNumber=scanner.nextLine();
                        System.out.println("Enter the Amount to Deposit : ");
                        int acoountBalance=scanner.nextInt();
                        if(acoountBalance>=500){
                            Customer customerDatabase=new Customer(accountNumber,customerName, pinNumber, acoountBalance);
                            db.put(accountNumber, customerDatabase);
                        }
                        else{
                            System.out.println("Minimum Balance Must be 500 or above");
                            break;
                        }
                        break;
                    }
                    case 5:
                    {
                        System.out.println("         Money Transfer         ");

                        System.out.println("Enter the Account Number : ");
                        String fromAccountNumber=scanner.next();
                        System.out.println("Enter the Pin Number : ");
                        String fromPinNumber=scanner.next();
                        if(handleCustomer.validAccountNumber(fromAccountNumber, db)&&handleCustomer.validPinNumber(fromAccountNumber, fromPinNumber, db)){
                            System.out.println("Enter the Account Number to make Transfer : ");
                            String toAccountNumber=scanner.next();
                            System.out.println("Enter the Amount to Transfer : ");
                            int transferAmount=scanner.nextInt();
                            handleCustomer.transferAmount(fromAccountNumber, toAccountNumber, transferAmount, db);
                        }
                        else{
                            System.out.println("Invalid Account Number or Pin Number");
                            break;
                        }
                        break;
                    }
                    case 6:
                    {
                        System.out.println("    Check Account Balance     ");

                        System.out.println("Enter the Account Number : ");
                        String accountNumber=scanner.next();
                        System.out.println("Enter the Pin Number : ");
                        String pinNumber=scanner.next();
                        if(handleCustomer.validAccountNumber(accountNumber, db)&&handleCustomer.validPinNumber(accountNumber, pinNumber, db)){
                            System.out.println("AccNo  AccountHolder    PinNumber AccountBalance");
                            System.out.println(accountNumber+"        "+db.get(accountNumber).getCustomerName()+"        "+pinNumber+"        "+db.get(accountNumber).getAccountBalance());
                        }
                        else{
                            System.out.println("Invalid Account Number or Pin Number");
                            break;
                        }
                        break;
                    }
                    case 7:
                    {
                        System.out.println(" Customer Details ");
                        System.out.println("AccNo    Account Holder    Pin Number    Account Balance");
                        for(Map.Entry<String, Customer> entry: db.entrySet()){
                            System.out.println(entry.getValue().getAccountNumber()+"       "+entry.getValue().getCustomerName()+"        "+entry.getValue().getPinNumber()+"        "+entry.getValue().getAccountBalance());
                        }
                    }
                    case 8:
                    {
                        System.out.println(" Deposit ");
                        System.out.println("Enter the Account Number : ");
                        String accountNumber=scanner.next();
                        if(handleCustomer.validAccountNumber(accountNumber, db)){
                            System.out.println("Enter the Amount to Deposit : ");
                            int depositAmount=scanner.nextInt();
                            db.get(accountNumber).deposit(accountNumber, depositAmount, db);
                            System.out.println("Your Current Account Balance is Rs. "+db.get(accountNumber).getAccountBalance());
                        }
                        else{
                            System.out.println("Please Enter valid Account Number...");break;
                        }break;
                    }
                    case 9:
                    {
                        System.out.println("Thank You!");
                        System.exit(0);
                    }
                    default:
                    {
                        System.out.println(" Enter valid option...");
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
