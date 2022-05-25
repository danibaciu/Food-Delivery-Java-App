package pao.components;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class AbstractUser extends Entity<Long>{

    static long serialOfUserNumber = 0;

    protected String firstName, lastName, emailAddress;
    protected LocalDate dateOfBirth;
    protected Address addressOfLiving;

    public AbstractUser(){
        super(serialOfUserNumber);
        serialOfUserNumber += 1;
    }

    public AbstractUser(String firstName, String lastName, String emailAddress, LocalDate timestampOfBirth, Address addressOfLiving) {
        super(serialOfUserNumber);
        serialOfUserNumber += 1;

        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = timestampOfBirth;
        this.addressOfLiving = addressOfLiving;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() { return firstName + " " + lastName; }

    public String getEmailAddress() {
        return emailAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Address getAddressOfLiving() {
        return addressOfLiving;
    }

    public void setAddressOfLiving(Address addressOfLiving) {
        this.addressOfLiving = addressOfLiving;
    }

    public static long getSerialOfUserNumber() {
        return serialOfUserNumber;
    }

    @Override
    public String toString() {
        return "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", dateOfBirth=" + dateOfBirth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                ", addressOfLiving=" + addressOfLiving +
                ", id=" + getId();
    }

    public String convertEntityToCsvString(){
        return id + "," + firstName + "," + lastName + "," + emailAddress + "," + dateOfBirth.toString() + "," + addressOfLiving.convertEntityToCsvString();
    }

    public void convertCsvStringToEntity(String CsvString){
        String []tempArr = CsvString.split(CsvDelimiter);

        try {
            this.id = Long.valueOf(tempArr[0]);
            this.firstName = tempArr[1];
            this.lastName = tempArr[2];
            this.emailAddress = tempArr[3];
            this.dateOfBirth = LocalDate.parse(tempArr[4]);
            (this.addressOfLiving = new Address()).convertCsvStringToEntity(tempArr[5] + "," + tempArr[6] + "," + tempArr[7] + "," + tempArr[8]);
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea userului abstract din CSV : " + exception);
        }
    }
}
