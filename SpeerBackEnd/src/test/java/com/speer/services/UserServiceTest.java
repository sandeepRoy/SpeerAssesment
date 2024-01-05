package com.speer.services;

import com.speer.dtos.CreateNoteDto;
import com.speer.dtos.CreateUserDto;
import com.speer.dtos.LoginUserDto;
import com.speer.entities.Note;
import com.speer.entities.User;
import com.speer.repositories.NoteRepository;
import com.speer.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserService userService;

    @Test
    void createUser() {

        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setUser_name("user_name");
        createUserDto.setPassword("1234");

        User user = userService.createUser(createUserDto);
        System.out.println(user.toString());
    }

    @Test
    void loginUser() {
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setUser_name("user_name");
        loginUserDto.setPassword("1234");

        String token = userService.loginUser(loginUserDto);
        System.out.println(token);
    }
    
    @Test
    void getTokenOfUser() {
        String user_name = "user_name";
        User user = userService.findUserByUserName(user_name);
        String token = user.getToken();
        System.out.println(token);
        assertEquals(token.contains("user_name"), true);
    }

    @Test
    void getAllNotesofUser() {
        String token = "MyTr5mbaVn9y6VbHtcn1user_name";
        String userNameFromToken = userService.extractUserNameFromToken(token.substring(20));
        User user = userService.findUserByUserName(userNameFromToken);
        List<Note> noteList = user.getNoteList();
        for(Note note : noteList){
            System.out.println(note.toString());
        }
    }


    @Test
    void getNoteByIdOfUser() {
        String token = "MyTr5mbaVn9y6VbHtcn1user_name";
        Integer note_id = 21;
        String userNameFromToken = userService.extractUserNameFromToken(token.substring(20));
        User user = userService.findUserByUserName(userNameFromToken);

        List<Note> noteList = user.getNoteList();
        for(Note note : noteList){
            if(note.getNote_id().equals(note_id)){
                System.out.println(note.toString());
                assertEquals(note.getNote_id(), note_id);
            }
        }
    }

    @Test
    void createNoteForUser() {
        String token = "MyTr5mbaVn9y6VbHtcn1user_name";
        String user_name = userService.extractUserNameFromToken(token.substring(20));
        User user = userService.findUserByUserName(user_name);

        CreateNoteDto createNoteDto = new CreateNoteDto();
        createNoteDto.setNote_body("Note 3");

        Note note = new Note();
        note.setNote_body(createNoteDto.getNote_body());
        note.setUser(user);
        noteRepository.save(note);

        System.out.println(note.toString());
    }

    @Test
    void updateNoteofUser() {
        CreateNoteDto createNoteDto = new CreateNoteDto();
        createNoteDto.setNote_body("Update Note 3");

        String token = "MyTr5mbaVn9y6VbHtcn1user_name";
        User user = userService.findUserByUserName(token.substring(20));

        List<Note> noteList = user.getNoteList();
        for(Note note : noteList){
            if(note.getNote_id().equals(21)){
                note.setNote_body(createNoteDto.getNote_body());
                System.out.println(note.toString());
            }
        }
        user.setNoteList(noteList);
        userRepository.save(user);
    }

    @Test
    void shareNote() {
        String token = "MyTr5mbaVn9y6VbHtcn1user_name";
        User reciever = userService.findUserByUserName("sandeep");
        Note original_note = noteRepository.findById(21).get();
        Note shared_note = new Note();

        shared_note.setNote_body(original_note.getNote_body());
        shared_note.setUser(reciever);
        noteRepository.save(shared_note);
        System.out.println(shared_note.toString());
    }

    @Test
    void findNotesWithKeyWords() {
        List<Note> found_notes = new ArrayList<>();
        String token = "Lrj8T8WZjyEnxEQwG1nUsandeep";
        User user = userService.findUserByUserName(token.substring(20));
        String keywords = "spike tom jerry";

        String[] split = keywords.split("");
        List<String> keyword_list = new ArrayList<String>(Arrays.asList(split));

        Integer keyword_list_size = keyword_list.size();
        Integer counter = 0;
        List<Note> noteList = user.getNoteList();
        for(Note note : noteList){
            if(counter < keyword_list_size){
                if(note.getNote_body().contains(keyword_list.get(counter))){
                    found_notes.add(note);
                }
                counter++;
            }
        }

        for(Note note : found_notes){
            System.out.println(note.toString());
        }
    }

    @Test
    void extractUserNameFromToken() {
        String token = "MyTr5mbaVn9y6VbHtcn1user_name";
        String userName = token.substring(20);
        System.out.println(userName);
    }

    @Test
    void findUserByUserName() {
        String token = "MyTr5mbaVn9y6VbHtcn1user_name";
        String user_name = userService.extractUserNameFromToken(token.substring(20));
        User user = userService.findUserByUserName(user_name);
    }

    @Test
    void deleteNoteofUser() {
        noteRepository.deleteById(25);
    }
}