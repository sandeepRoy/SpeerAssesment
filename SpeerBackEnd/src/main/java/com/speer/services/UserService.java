package com.speer.services;

import com.speer.dtos.CreateNoteDto;
import com.speer.dtos.CreateUserDto;
import com.speer.dtos.LoginUserDto;
import com.speer.entities.Note;
import com.speer.entities.User;
import com.speer.repositories.NoteRepository;
import com.speer.repositories.UserRepository;
import com.speer.responses.SharedNotesResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;
    // POST - Create new User
    public User createUser(CreateUserDto createUserDto){
        User user = new User();
        user.setUser_name(createUserDto.getUser_name());
        user.setPassword(createUserDto.getPassword());

        userRepository.save(user);
        return user;
    }

    // Post - Login Already Created User
    public String loginUser(LoginUserDto loginUserDto){
        String token = ""; // need to be global, available for a createdUser throughout interaction
        List<User> all_users = userRepository.findAll();
        for(User user : all_users){
            if(user.getUser_name().equals(loginUserDto.getUser_name()) && user.getPassword().equals(loginUserDto.getPassword())){
                token = RandomStringUtils.randomAlphanumeric(20) + user.getUser_name() + "--"  + user.getPassword();
                user.setToken(token);
                userRepository.save(user);
            }
        }
        return token;
    }

    public User getAllNotesofUser(String token) throws Exception{
        User found_user = new User();
        String user_name = extractUserNameFromToken(token.substring(20));
        List<User> all = userRepository.findAll();
        for(User user : all){
            if(user.getUser_name().equals(user_name)){
                found_user.setUser_id(user.getUser_id());
                found_user.setUser_name(user.getUser_name());
                found_user.setPassword(user.getPassword());
                found_user.setToken(user.getToken());
                found_user.setNoteList(user.getNoteList());
            }
        }
        return found_user;
    }

    public User getNoteByIdOfUser(String token, Integer note_id) {
        User found_user = new User();
        String user_name = extractUserNameFromToken(token.substring(20));
        List<User> all = userRepository.findAll();
        for(User user : all){
            if(user.getUser_name().equals(user_name)) {
                Note note = noteRepository.findById(note_id).get();
                List<Note> noteList = new ArrayList<>();
                noteList.add(note);
                found_user.setNoteList(noteList);
                found_user.setUser_id(user.getUser_id());
                found_user.setUser_name(user.getUser_name());
                found_user.setPassword(user.getPassword());
                found_user.setToken(user.getToken());
            }
        }
        return found_user;
    }

    public User createNoteForUser(String token, CreateNoteDto createNoteDto){
        User user_with_note = new User();
        String user_name = extractUserNameFromToken(token.substring(20));
        List<User> all = userRepository.findAll();
        for(User user : all){
            if(user.getUser_name().equals(user_name)){
                Note note = new Note();
                note.setNote_body(createNoteDto.getNote_body());
                note.setUser(user);
                List<Note> noteList = user.getNoteList();
                noteList.add(note);

                noteRepository.save(note);
                user_with_note = userRepository.save(user);
            }
        }
        return user_with_note;
    }

    // PUT - Update Note of a user by given note_id
    public User updateNoteofUser(String token, Integer note_id, CreateNoteDto createNoteDto){
        User user_with_note = new User();
        String user_name = extractUserNameFromToken(token.substring(20));
        List<User> all = userRepository.findAll();
        for(User user : all){
            if(user.getUser_name().equals(user_name)){
                Note note = noteRepository.findById(note_id).get();
                note.setNote_body(createNoteDto.getNote_body()); // new note
                note.setUser(user); // update note for user
                noteRepository.save(note);
                user_with_note = userRepository.save(user);
            }
        }
        return user_with_note;
    }

    // DELETE - Delete note of a user by giveb note_id
    public User deleteNoteofUser(String token, Integer note_id){
        User user_with_note = new User();
        String user_name = extractUserNameFromToken(token.substring(20));
        List<User> all = userRepository.findAll();
        for(User user : all){
            if(user.getUser_name().equals(user_name)){
                Note note = noteRepository.findById(note_id).get();
                noteRepository.delete(note);
                user_with_note = userRepository.save(user);
            }
        }
        return user_with_note;
    }

    // POST - Share a note of a user by given note_id, to another user given by user_id
    public Note shareNote(String token, Integer note_id, Integer user_id){
        Note note = noteRepository.findById(note_id).orElseThrow(() -> new RuntimeException("Note not found")); // Note ID - 4
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not Found")); // User ID - 2


        List<Note> noteList = user.getNoteList();
        note.setUser(user);
        noteList.add(note);
        userRepository.save(user);
        return note;
    }

    public String extractUserNameFromToken(String token_processed){
        String userName = "";
        int index_of_separator = token_processed.indexOf("--");
        if(index_of_separator != -1){
            userName = token_processed.substring(0, index_of_separator);
        }
        System.out.println(userName);
        return userName;
    }
}
