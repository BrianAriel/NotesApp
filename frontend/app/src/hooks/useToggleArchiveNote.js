const useToggleArchiveNote = (url) => {
    const toggleArchiveNote = async (id) => {
        try {
            await fetch(`${url}/notes/toggleArchive/${id}`, {
                method: 'PUT',
            });
        } catch (error) {
            console.error("Error archiving note:", error);
        }

    };

    return { toggleArchiveNote };
};

export default useToggleArchiveNote;
