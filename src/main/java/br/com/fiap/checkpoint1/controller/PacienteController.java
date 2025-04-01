package br.com.fiap.checkpoint1.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.checkpoint1.dto.PacienteRequestCreate;
import br.com.fiap.checkpoint1.dto.PacienteRequestUpdate;
import br.com.fiap.checkpoint1.dto.PacienteResponse;
import br.com.fiap.checkpoint1.service.PacienteService;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponse> create(@RequestBody PacienteRequestCreate dto){
        return ResponseEntity.ok().body(new PacienteResponse().toDto(pacienteService.createPaciente(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> update(@PathVariable Long id, @RequestBody PacienteRequestUpdate dto){
        return pacienteService.updatePaciente(id, dto)
        .map(p->new PacienteResponse().toDto(p))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean result = pacienteService.deletePaciente(id);

        if(result){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> findById(@PathVariable Long id){
        return pacienteService.getPacienteById(id)
        .map(p->new PacienteResponse().toDto(p))
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> findAll(){
       List<PacienteResponse> response = 
         pacienteService.getAll().stream()
          .map(p->new PacienteResponse().toDto(p))
          .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
