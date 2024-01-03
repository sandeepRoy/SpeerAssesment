package com.speer.controllers;

import com.speer.dtos.CreateNoteDto;
import com.speer.entities.Note;
import com.speer.entities.User;
import com.speer.responses.SharedNotesResponse;
import com.speer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    @Autowired
    private UserService userService;

    @GetMapping("/notes")
    public ResponseEntity<User> getAllNotesOfUser(@RequestParam String token) throws Exception {
        User user = userService.getAllNotesofUser(token);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/notes/id")
    public ResponseEntity<User> getNoteByIdOfUser(@RequestParam String token, @RequestParam Integer id) {
        User user = userService.getNoteByIdOfUser(token, id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/notes")
    public ResponseEntity<User> createNoteForUser(@RequestParam String token, @RequestBody CreateNoteDto createNoteDto){
        User user = userService.createNoteForUser(token, createNoteDto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping("/notes/id")
    public ResponseEntity<User> updateNoteofUser(@RequestParam String token, @RequestParam Integer id, @RequestBody CreateNoteDto createNoteDto){
        User user = userService.updateNoteofUser(token, id, createNoteDto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/notes/id")
    public ResponseEntity<User> deleteNoteofUser(@RequestParam String token, @RequestParam Integer id){
        User user = userService.deleteNoteofUser(token, id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("notes/id/share")
    public ResponseEntity<Note> shareNote(@RequestParam String token, @RequestParam Integer note_id, @RequestParam Integer user_id){
        Note note = userService.shareNote(token, note_id, user_id);
        return new ResponseEntity<Note>(note, HttpStatus.OK);
    }
}
