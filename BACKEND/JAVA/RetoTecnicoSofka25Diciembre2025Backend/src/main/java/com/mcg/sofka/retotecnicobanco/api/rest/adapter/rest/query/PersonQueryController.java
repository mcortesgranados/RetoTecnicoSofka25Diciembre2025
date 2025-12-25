package com.mcg.sofka.retotecnicobanco.api.rest.adapter.rest.query;

import com.mcg.sofka.retotecnicobanco.api.rest.application.port.input.QueryUseCase;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.GetPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.application.query.dto.ListPersonQuery;
import com.mcg.sofka.retotecnicobanco.api.rest.domain.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person/query")
public class PersonQueryController {

    private final QueryUseCase<GetPersonQuery, Optional<Person>> getUseCase;
    private final QueryUseCase<ListPersonQuery, List<Person>> listUseCase;

    public PersonQueryController(QueryUseCase<GetPersonQuery, Optional<Person>> getUseCase,
                                 QueryUseCase<ListPersonQuery, List<Person>> listUseCase) {
        this.getUseCase = getUseCase;
        this.listUseCase = listUseCase;
    }

    @GetMapping("/single")
    public ResponseEntity<Optional<Person>> get(@RequestParam Long id) {
        return ResponseEntity.ok(getUseCase.execute(new GetPersonQuery(id)));
    }

    @GetMapping
    public ResponseEntity<List<Person>> list(@RequestParam String identification) {
        return ResponseEntity.ok(listUseCase.execute(new ListPersonQuery(identification)));
    }
}
