package com.avaliacao.l2code.controller;


import com.avaliacao.l2code.dto.request.PedidoRequestDTO;
import com.avaliacao.l2code.dto.response.PedidoResponseDTO;
import com.avaliacao.l2code.service.EmpacotarService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/empacotar")
@Tag(name = "Empacotar", description = "Gerenciamento de caixas.")
public class EmpacotarController {

    @Autowired
    EmpacotarService empacotarService;

    @PostMapping("/processar-pedidos")
    public ResponseEntity<PedidoResponseDTO> processar(@RequestBody PedidoRequestDTO pedidoRequest) {

        return ResponseEntity.ok().body(empacotarService.processarPedidos(pedidoRequest));
    }
}
