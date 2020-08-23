package company.repo.io.CSV;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import company.model.Account;
import company.repo.AccountRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImplCSV implements AccountRepository {
    private long idCounter = 1;

    private final String fileName = "\\git\\mentoring\\src\\main\\resources\\account.csv";

    private Path filePath = Paths.get("\\git\\mentoring\\src\\main\\resources\\account.csv");

    private List<Account> accountList = new ArrayList<>();


    public Account create(Account account) {
        if (!Files.exists(filePath)) {

            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            account.setId(idCounter);
            idCounter++;
            accountList.add(account);


        } else {

            if (account.getId() == 0) {


                try {

                    if (Files.size(filePath) > 5) {
                        idCounter = getNewId();
                        account.setId(idCounter);
                        accountList.add(account);
                    } else {
                        account.setId(idCounter);
                        idCounter++;
                        accountList.add(account);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                accountList.add(account);
            }


            writeAccountList(accountList);
        }
        return account;

    }

    private void writeAccountList(List accountList) {
        try {
            Writer writer = Files.newBufferedWriter(filePath);
            StatefulBeanToCsv<Account> csvWriter = new StatefulBeanToCsvBuilder<Account>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(false)
                    .build();
            csvWriter.write(accountList);
            writer.close();

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            e.printStackTrace();
        }

    }

    private Long getNewId() {
        return getAll().stream().max(Comparator.comparing(i -> i.getId())).get().getId() + 1;
    }


    public Account update(Account account) {

        delete(account.getId());
        create(account);

        writeAccountList(accountList);
        return account;
    }


    public void delete(Long id) {
        accountList.removeIf(e -> e.getId() == id);

        writeAccountList(accountList);
    }

    @Override
    public List<Account> getAll() {


        if (!Files.exists(Paths.get(fileName))) {
            try {
                Files.createFile(Paths.get(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            try {
                Reader reader = Files.newBufferedReader(filePath);

                accountList = new CsvToBeanBuilder(reader).withType(Account.class).build().parse();
                reader.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return accountList;
    }

    @Override
    public Optional<Account> read(Long id) {

        return getAll().stream().filter(x -> x.getDevId() == id).findFirst();
    }

}
