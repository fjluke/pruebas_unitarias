package com.example.ms_carrito.controller;

import com.example.ms_carrito.dto.CarritoRequestDTO;
import com.example.ms_carrito.dto.CarritoResponseDTO;
import com.example.ms_carrito.service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Carritos",
        description = "API para la gestión de carritos de compra"
)
@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
@Slf4j

public class CarritoController {

    private final CarritoService carritoService;

    @Operation(summary = "Crear un nuevo carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<CarritoResponseDTO> crearCarrito(
            @RequestBody CarritoRequestDTO requestDTO) {

        log.info("POST /api/carritos - Crear carrito");

        CarritoResponseDTO carritoCreado =
                carritoService.crearCarrito(requestDTO);

        return new ResponseEntity<>(
                carritoCreado,
                HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un carrito por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> obtenerCarritoPorId(

            @Parameter(description = "ID del carrito")
            @PathVariable Long id) {

        log.info("GET /api/carritos/{} - Obtener carrito", id);

        CarritoResponseDTO carrito =
                carritoService.obtenerCarritoPorId(id);

        return ResponseEntity.ok(carrito);
    }

    @Operation(summary = "Obtener todos los carritos")
    @ApiResponse(responseCode = "200", description = "Lista de carritos")
    @GetMapping
    public ResponseEntity<List<CarritoResponseDTO>> obtenerTodosLosCarritos() {

        log.info("GET /api/carritos - Obtener todos los carritos");

        List<CarritoResponseDTO> carritos =
                carritoService.obtenerTodosLosCarritos();

        return ResponseEntity.ok(carritos);
    }

    @Operation(summary = "Actualizar un carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito actualizado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarritoResponseDTO> actualizarCarrito(

            @Parameter(description = "ID del carrito")
            @PathVariable Long id,

            @RequestBody CarritoRequestDTO requestDTO) {

        log.info("PUT /api/carritos/{} - Actualizar carrito", id);

        CarritoResponseDTO carritoActualizado =
                carritoService.actualizarCarrito(id, requestDTO);

        return ResponseEntity.ok(carritoActualizado);
    }

    @Operation(summary = "Eliminar un carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito eliminado"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrito(

            @Parameter(description = "ID del carrito")
            @PathVariable Long id) {

        log.info("DELETE /api/carritos/{} - Eliminar carrito", id);

        carritoService.eliminarCarrito(id);

        return ResponseEntity.noContent().build();
    }
}