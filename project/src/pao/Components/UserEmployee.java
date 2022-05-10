package pao.Components;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserEmployee extends AbstractUser {

    private long salaryPerMonth;
    private LocalDate dateOfHire;
    private PossibleJobs jobName;

    public UserEmployee(String firstName, String lastName, String emailAddress, LocalDate timeOfBirth, Address addressOfLiving, long salaryPerMonth, PossibleJobs job) {
        super(firstName, lastName, emailAddress, timeOfBirth, addressOfLiving);

        this.salaryPerMonth = salaryPerMonth;
        this.jobName = job;
        this.dateOfHire = LocalDate.now();
    }

    public long getSalaryPerMonth() {
        return salaryPerMonth;
    }

    public void increaseSalaryByXPercent(Integer x) {
        this.salaryPerMonth += (long)(salaryPerMonth * x) / 100;
    }

    public String getJobName() {
        return jobName.name();
    }

    @Override
    public String toString() {
        return "UserEmployee{" +
                super.toString() +
                ", salaryPerMonth=" + salaryPerMonth +
                ", dateOfHire=" + dateOfHire.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +
                ", jobName='" + jobName.toString() + '\'' +
                '}';
    }

    @Override
    public String convertEntityToCsvString() {
        return super.convertEntityToCsvString() + "," + salaryPerMonth + "," + dateOfHire.toString() + "," + jobName.toString();
    }

    @Override
    public void convertCsvStringToEntity(String CsvString) {
        String []tempArr = CsvString.split(CsvDelimiter);

        try {
            StringBuilder abstractConstructor = new StringBuilder(tempArr[0]);
            for (int i = 1; i <= 8; i++) {
                abstractConstructor.append(",").append(tempArr[i]);
            }
            super.convertCsvStringToEntity(abstractConstructor.toString());
            this.salaryPerMonth = Long.parseLong(tempArr[9]);
            this.dateOfHire = LocalDate.parse(tempArr[10]);
            this.jobName = PossibleJobs.valueOf(tempArr[11]);
        }
        catch (Exception exception) {
            System.out.println("Exceptie la citirea soferului din CSV : " + exception);
        }
    }
}
