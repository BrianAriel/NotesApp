const useEditNote = (url) => {
    const editNote = async (formData) => {
        try {
            const response = await fetch(`${url}/notes/${formData.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                console.error(`Error editing note: ${response.statusText}`);
                return;
            }
        } catch (error) {
            console.error('Error editing note:', error);
        }

    };

    return { editNote };
};

export default useEditNote;