import React from 'react';

const Note = ({ id, title, content, archived, categories, onEdit, onDelete, onArchive, onRemoveCategory, onAddCategory }) => {
    return (
        <div className='note'>
            <h3>{title}</h3>
            <p>{content}</p>
            <div className='note-actions'>
                <button onClick={() => onEdit(id, title, content)}>Edit</button>
                <button onClick={() => onDelete(id)}>Delete</button>
                <button onClick={() => onArchive(id, archived)}>{archived ? "Unarchive" : "Archive"}</button>
            </div>
            <div>
                <strong>Categories:</strong>
                <div className='category-container'>
                    {categories.map((category, index) => (
                        <div key={index} className='category-chip'>
                            {category} <button className='note-remove' onClick={() => onRemoveCategory(id, category)}>Remove</button>
                        </div>
                    ))}
                    <div>
                        <input
                            type='text'
                            placeholder='Enter a new category'
                            onKeyDown={(e) => e.key === 'Enter' && onAddCategory(id, e.target.value)}
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Note;