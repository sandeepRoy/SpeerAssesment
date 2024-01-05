package com.speer.services;

import com.speer.dtos.CreateNoteDto;
import com.speer.dtos.CreateUserDto;
import com.speer.dtos.LoginUserDto;
import com.speer.entities.Note;
import com.speer.entities.User;
import com.speer.repositories.NoteRepository;
import com.speer.repositories.UserRepository;
import com.speer.responses.SharedNotesResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        String token = "", encrypted_user_name = "";
        List<User> all_users = userRepository.findAll();
        for(User user : all_users){
            if(user.getUser_name().equals(loginUserDto.getUser_name()) && user.getPassword().equals(loginUserDto.getPassword())){
                token = RandomStringUtils.randomAlphanumeric(20) + user.getUser_name();
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
        noteRepository.deleteById(note_id);
        return findUserByUserName(token.substring(20));
    }

    // POST - Share a note of a user by given note_id, to another user given by user_id
    public Note shareNote(String token, Integer note_id, Integer user_id){
        User receiver = userRepository.findById(user_id).get();
        Note original_note = noteRepository.findById(note_id).get();
        Note shared_note = new Note();

        shared_note.setNote_body(original_note.getNote_body());
        shared_note.setUser(receiver);

        noteRepository.save(shared_note);
        return shared_note;
    }

    // GET - List<Notes> return notes with given keywords of a user
    public List<Note> findNotesWithKeyWords(String token, String keyWords){
        List<Note> found_notes = new ArrayList<>();

        String user_name = extractUserNameFromToken(token.substring(20));
        User user = findUserByUserName(user_name);

        List<Note> noteList = user.getNoteList();
        for(Note note : noteList){
            if(note.getNote_body().toLowerCase().contains(keyWords.toLowerCase())){
                found_notes.add(note);
            }
        }
        return found_notes;
    }

    public String extractUserNameFromToken(String token_processed){
        String userName = "";
        userName = token_processed.substring(0, token_processed.length());
        return userName;
    }

    public User findUserByUserName(String user_name){
        List<User> all_users = userRepository.findAll();
        for(User user : all_users){
            if(user.getUser_name().equals(user_name)){
                return user;
            }
        }
        return null;
    }
}
