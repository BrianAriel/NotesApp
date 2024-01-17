const useDeleteNote = (url) => {
    const deleteNote = async (id) => {
        try {
            await fetch(`${url}/notes/${id}`, {
                method: 'DELETE',
            });
        } catch (error) {
            console.error("Error deleting note:", error);
        }
    };

    return { deleteNote };
};

export default useDeleteNote;
