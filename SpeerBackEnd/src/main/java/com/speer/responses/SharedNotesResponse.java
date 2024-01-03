package com.speer.responses;

import com.speer.entities.Note;
import com.speer.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SharedNotesResponse {
    private Note note;
    private List<Integer> userList = new ArrayList<>();
}
