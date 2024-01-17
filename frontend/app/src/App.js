import React, { useState } from "react";
import { validateInput } from "./utils/validateInput";
import './styles/App.css';
import './styles/ListNotes.css';
import './styles/NoteForm.css';
import './styles/Note.css';
import './styles/CategoryFilter.css'
import ListNotes from "./components/ListNotes";
import NoteForm from "./components/NoteForm";
import CategoryFilter from "./components/CategoryFilter";
import useCreateNote from "./hooks/useCreateNote";
import useDeleteNote from "./hooks/useDeleteNote";
import useEditNote from './hooks/useEditNote';
import useToggleArchiveNote from "./hooks/useToggleArchiveNote";
import useGetNotes from "./hooks/useGetNotes";
import useAddCategory from "./hooks/useAddCategory";
import useRemoveCategory from "./hooks/useRemoveCategory";
import { validateCategory } from "./utils/validateCategory";

const App = () => {
  const API_URL = 'http://localhost:8080'

  // We want to show the unarchived notes first
  const { data: notes, getNotes } = useGetNotes(API_URL);
  const { createNote } = useCreateNote(API_URL);
  const { deleteNote } = useDeleteNote(API_URL);
  const { editNote } = useEditNote(API_URL);
  const { toggleArchiveNote } = useToggleArchiveNote(API_URL);
  const { addCategory } = useAddCategory(API_URL);
  const { removeCategory } = useRemoveCategory(API_URL);

  const [showForm, setShowForm] = useState(false);
  const [showCategoryFilter, setShowCategoryFilter] = useState(false);
  const [selectedNote, setSelectedNote] = useState(null);

  //Once we create the note, we hide the form and retrieve the active notes
  const handleCreate = async (formData) => {
    if (validateInput(formData)) {
      await createNote(formData);
      setShowForm(false);
      getNotes();
    } else {
      console.error("Input validation failed. Title and/or content might be missing. Title might be too long")
    }
  };

  const handleDelete = async (id) => {
    const confirmed = window.confirm('Are you sure you want to delete this note?');
    if (confirmed) {
      await deleteNote(id);
      getNotes(false);
    }
  };

  //Once we edit the note, we hide the form, clean up the selectedNote and retrieve the active notes
  const handleEditNote = async (formData) => {
    if (validateInput(formData)) {
      await editNote(formData)
      setShowForm(false);
      setSelectedNote(null);
      getNotes();
    } else {
      console.error("Input validation failed. Title and/or content might be missing. Title might be too long")
    }
  }

  const handleGetNotes = (archived) => {
    getNotes(archived);
  }

  const handleArchive = async (id, archived) => {
    await toggleArchiveNote(id);
    getNotes(archived);
  }

  const handleEditNoteForm = (id, title, content) => {
    setSelectedNote({ id, title, content });
    setShowForm(true);
  };

  const handleAddCategory = async (id, category) => {
    if (validateCategory) {
      await addCategory(id, category);
      getNotes();
    } else {
      console.error("Category can't be empty")
    }
  }

  const handleRemoveCategory = async (id, category) => {
    await removeCategory(id, category);
    getNotes();
  }

  const handleFilterByCategory = async (archived, category) => {
    await getNotes(archived, category)
  }

  return (
    <div className="app">
      <h1>Notes App</h1>
      <button onClick={() => { setShowForm(true); setShowCategoryFilter(false) }}>Create note</button>
      <button onClick={() => handleGetNotes(true)}>List archived notes</button>
      <button onClick={() => handleGetNotes(false)}>List active notes</button>

      {showCategoryFilter ? (
        <CategoryFilter
          onApplyFilter={(includeArchived, category) => handleFilterByCategory(includeArchived, category)}
          onCancel={() => setShowCategoryFilter(false)}
        />
      ) : (
        <button onClick={() => setShowCategoryFilter(true)}>Filter notes by category</button>
      )}

      <NoteForm
        isVisible={showForm}
        setShowForm={setShowForm}
        onCreate={handleCreate}
        onUpdate={handleEditNote}
        selectedNote={selectedNote} />
      <ListNotes notes={notes} onEdit={handleEditNoteForm} onDelete={handleDelete} onArchive={handleArchive} onRemoveCategory={handleRemoveCategory} onAddCategory={handleAddCategory} />
    </div>
  );
};

export default App;