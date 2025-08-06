package com.lta.cursoapis.controller;

import com.lta.cursoapis.entity.Message;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private List<Message> mensajes = new ArrayList<>();

    public MessageController() {
        mensajes.add(new Message(1, "Testeo de mensaje 1"));
        mensajes.add(new Message(2, "Testeo de mensaje 2"));
        mensajes.add(new Message(3, "Testeo de mensaje 3"));
    }

    @GetMapping
    public List<Message> getAllMessages() {
        return mensajes;
    }

    @GetMapping("/{id}")
    public Message getMessageById(@PathVariable int id) {
        Optional<Message> mensaje = mensajes.stream().
                filter(m -> m.getId() == id)
                .findFirst();
        return mensaje.orElse(null);
    }

    @PostMapping
    public Message crearMensaje(@RequestBody Message mensaje) {
        mensajes.add(mensaje);
        return mensaje;
    }

    @DeleteMapping("/{id}")
    public void eliminarMensaje(@PathVariable int id) {
        mensajes.removeIf(m -> m.getId() == id);
    }
}
