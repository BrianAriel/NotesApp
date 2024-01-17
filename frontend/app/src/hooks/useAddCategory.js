const useAddCategory = (url) => {
    const addCategory = async (id, category) => {
        try {
            const response = await fetch(`${url}/notes/addCategory/${id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(category),
            });

            const result = await response.json();
            return result;
        } catch (error) {
            console.error("Error creating category:", error);
        }
    };

    return { addCategory };
};

export default useAddCategory;
