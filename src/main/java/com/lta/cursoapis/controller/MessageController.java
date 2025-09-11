package com.lta.cursoapis.controller;

import com.lta.cursoapis.entity.Message;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/messages")
public class MessageController {

    private List<Message> mensajes = new ArrayList<>();

    public MessageController() {
        mensajes.add(new Message(1,"Suscríbete en La Tecnología Avanza"));
        mensajes.add(new Message(2,"Comparte el video en tus redes sociales"));
    }

    @GetMapping
    public List<Message> listarMensajes(){
        return mensajes;
    }

    @GetMapping("/{id}")
    public Message obtenerMensajePorId(@PathVariable int id){
        Optional<Message> message = mensajes.stream()
                .filter(m -> m.getId() == id)
                .findFirst();
        return message.orElse(null);
    }

    @PostMapping
    public Message crearMensaje(@RequestBody Message message){
        mensajes.add(message);
        return message;
    }

    @DeleteMapping("/{id}")
    public void eliminarMensaje(@PathVariable int id){
        mensajes.removeIf(m -> m.getId() == id);
    }
}