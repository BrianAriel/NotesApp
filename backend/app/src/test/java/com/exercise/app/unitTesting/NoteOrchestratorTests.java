package com.exercise.app.unitTesting;

import com.exercise.app.domain.Note;
import com.exercise.app.dtos.NoteDTO;
import com.exercise.app.orchestrators.NoteOrchestrator;
import com.exercise.app.services.NoteSearcher;
import com.exercise.app.services.NoteUpdater;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NoteOrchestratorTests {

    @InjectMocks
    private NoteOrchestrator noteOrchestrator;

    @Mock
    private NoteSearcher noteSearcher;
    @Mock
    private NoteUpdater noteUpdater;

    @Test
    public void createNewNote_whenANoteIsCreated_thenItIsCreatedCorrectly() {
        NoteDTO noteDTO = NoteDTO.builder()
                .id(1L)
                .title("title")
                .content("this is content")
                .archived(false)
                .categories(new ArrayList<>())
                .build();

        Note note = Note.builder()
                .id(1L)
                .title("title")
                .content("this is content")
                .archived(false)
                .categories(new ArrayList<>())
                .build();
        when(noteUpdater.save(any(Note.class))).thenReturn(note);

        NoteDTO noteDTOReturned = noteOrchestrator.createNewNote(noteDTO);

        assertEquals(noteDTO, noteDTOReturned);
    }

    @Test
    public void deleteNote_whenNoteDoesNotExists_thenExceptionIsThrown() {
        when(noteSearcher.findById(1L)).thenThrow(new EntityNotFoundException(("Note entity not found with id " + 1L)));
        assertThrows(EntityNotFoundException.class, () -> noteOrchestrator.deleteNote(1L));
    }

    @Test
    public void updateNote_whenNoteIsUpdated_thenNoteIsUpdatedCorrectly() {
        Note note = Note.builder()
                .id(1L)
                .title("title")
                .content("this is content")
                .archived(false)
                .categories(new ArrayList<>())
                .build();
        when(noteSearcher.findById(1L)).thenReturn(note);

        Note updatedNote = Note.builder()
                .id(1L)
                .title("this is another title")
                .content("this is another content")
                .archived(false)
                .categories(new ArrayList<>())
                .build();
        when(noteUpdater.save(any(Note.class))).thenReturn(updatedNote);

        NoteDTO noteDTO = NoteDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .archived(note.getArchived())
                .categories(note.getCategories())
                .build();
        NoteDTO recievedNoteDTO = noteOrchestrator.updateNote(noteDTO);

        NoteDTO expectedNoteDTO = NoteDTO.builder()
                .id(updatedNote.getId())
                .title(updatedNote.getTitle())
                .content(updatedNote.getContent())
                .archived(updatedNote.getArchived())
                .categories(updatedNote.getCategories())
                .build();

        assertEquals(expectedNoteDTO, recievedNoteDTO);
    }
}
