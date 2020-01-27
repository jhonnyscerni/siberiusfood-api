package br.com.siberius.siberiusfood.api.controller;

import br.com.siberius.siberiusfood.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teste")
@RestController
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

//    @GetMapping("/cozinhas/por-nome")
//    public ResponseEntity<?> cozinhasPorNome(@RequestParam("nome") String nome) {
//        return ResponseEntity.ok(cozinhaRepository.consultarPorNome(nome));
//    }
}
