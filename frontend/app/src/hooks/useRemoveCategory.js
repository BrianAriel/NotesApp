const useRemoveCategory = (url) => {
    const removeCategory = async (id, category) => {
        try {
            await fetch(`${url}/notes/removeCategory/${id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(category),
            });

        } catch (error) {
            console.error("Error deleting category:", error);
        }
    };

    return { removeCategory };
};

export default useRemoveCategory;