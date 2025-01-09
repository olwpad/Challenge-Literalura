package com.example.challengeliteralura;

import com.example.challengeliteralura.Principal.Principal;
import com.example.challengeliteralura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication  implements CommandLineRunner {

    @Autowired
    LibroRepository libroRepository;

    public static void main(String[] args) {
        SpringApplication.run(ChallengeLiteraluraApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        var principal= new Principal(libroRepository);
        principal.mostrarMenuLibros();
    }
}
