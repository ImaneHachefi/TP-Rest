package ma.banque.api.controllers;

import ma.banque.api.entities.Account;
import ma.banque.api.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/banque")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    // READ : tous les comptes (JSON et XML)
    @GetMapping(value = "/comptes", produces = {"application/json", "application/xml"})
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // READ : un compte par identifiant (JSON et XML)
    @GetMapping(value = "/comptes/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Account> findById(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(account -> ResponseEntity.ok().body(account))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE : nouveau compte (JSON et XML) -> 201 + en-tete Location
    @PostMapping(value = "/comptes",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account saved = accountRepository.save(account);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    // UPDATE : mise a jour d'un compte (JSON et XML)
    @PutMapping(value = "/comptes/{id}",
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody Account details) {
        return accountRepository.findById(id)
                .map(account -> {
                    account.setSolde(details.getSolde());
                    account.setDateCreation(details.getDateCreation());
                    account.setType(details.getType());
                    Account updated = accountRepository.save(account);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE : suppression d'un compte
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(account -> {
                    accountRepository.delete(account);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
