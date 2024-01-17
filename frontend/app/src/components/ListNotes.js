import React from 'react';
import Note from '../entities/Note';

const ListNotes = ({ notes, onEdit, onDelete, onArchive, onRemoveCategory, onAddCategory }) => {
    return (
        <div className='note-list'>
            {notes.length > 0 ? (
                notes.map((note) => (
                    <Note key={note.id} {...note} onEdit={onEdit} onDelete={onDelete} onArchive={onArchive} onRemoveCategory={onRemoveCategory} onAddCategory={onAddCategory} />
                ))
            ) : (
                <p>No notes available.</p>
            )}
        </div>
    );
};

export default ListNotes;