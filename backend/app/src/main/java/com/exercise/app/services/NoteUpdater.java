package com.exercise.app.services;

import com.exercise.app.domain.Note;
import com.exercise.app.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteUpdater {

    @Autowired
    private NoteRepository noteRepository;

    public Note save(Note note) {
        return noteRepository.save(note);
    }

    public void deleteOne(Note note) {
        noteRepository.delete(note);
    }

}
