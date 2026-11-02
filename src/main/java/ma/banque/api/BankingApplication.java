package ma.banque.api;

import ma.banque.api.entities.Account;
import ma.banque.api.entities.AccountType;
import ma.banque.api.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(AccountRepository accountRepository) {
        return args -> {
            accountRepository.save(new Account(null, Math.random() * 9000, new Date(), AccountType.EPARGNE));
            accountRepository.save(new Account(null, Math.random() * 9000, new Date(), AccountType.COURANT));
            accountRepository.save(new Account(null, Math.random() * 9000, new Date(), AccountType.EPARGNE));

            accountRepository.findAll().forEach(a -> System.out.println(a.toString()));
        };
    }
}
