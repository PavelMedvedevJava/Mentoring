package company.repo.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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

public class AccountRepositoryImpl implements AccountRepository {

    private long idCounter = 1;

    private final String fileName = "dz\\src\\main\\resources\\account.json";

    private Path filePath = Paths.get("dz\\src\\main\\resources\\account.json");

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


            try (Writer writer = new FileWriter(String.valueOf(Paths.get(fileName)))) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(accountList, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return account;

    }


    private Long getNewId() {
        return getAll().stream().max(Comparator.comparing(i -> i.getId())).get().getId() + 1;
    }


    public Account update(Account account) {

        delete(account.getId());
        create(account);


        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(accountList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }




    public void delete(Long id) {
        accountList.removeIf(e -> e.getId() == id);

        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(accountList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }


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
            Reader reader = new FileReader(fileName);

            Gson gson1 = new GsonBuilder().create();
            accountList = gson1.fromJson(reader, new TypeToken<ArrayList<Account>>() {
            }.getType());
            return accountList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
@Override
    public Optional<Account> read (Long id) {

        return getAll().stream().filter(x -> x.getDevId() == id).findFirst();
    }

}
