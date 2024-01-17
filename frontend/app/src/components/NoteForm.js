import React, { useEffect, useState } from 'react';

const NoteForm = ({ isVisible, setShowForm, onCreate, onUpdate, selectedNote}) => {
    const [formData, setFormData] = useState({
        title: '',
        content: ''
    });

    useEffect(() => {
        if (selectedNote) {
          setFormData({
            id: selectedNote.id || 0, 
            title: selectedNote.title || '',
            content: selectedNote.content || '',
          });
        }
      }, [selectedNote]);

    //When the input field changes, we update the form with new values.
    //We take the previous state, create a new object by spreading and update the input field
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
        }));
    };

    // We prevent the submit to reload the page, pass the form to the prop function and clear the form for the next input
    const handleCreate = async (e) => {
        onCreate(formData);
    };

    const handleUpdate = async (e) => {
        onUpdate(formData)
    };

    const handleClose = () => {
        setFormData({ title: '', content: '' });
        setShowForm(false);
    }

    return (
        <div style={{ display: isVisible ? 'block' : 'none' }}>
            <form onSubmit={selectedNote ? handleUpdate : handleCreate}>
                <label>
                    Title:
                    <input type="text" name="title" value={formData.title} onChange={handleInputChange}></input>
                </label>
                <label>
                    Content:
                    <textarea name="content" defaultValue={formData.content} onChange={handleInputChange}></textarea>
                </label>

                <button type="submit">{selectedNote ? 'Update note' : 'Create note'}</button>
                <button onClick={() => handleClose}>Cancel</button>
            </form>
        </div>
    );
};

export default NoteForm;