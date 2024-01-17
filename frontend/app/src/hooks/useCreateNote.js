const useCreateNote = (url) => {
  const createNote = async (formData) => {
    try {
      const response = await fetch(`${url}/notes`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      const result = await response.json();
      return result;
    } catch (error) {
      console.error("Error creating note:", error);
    }
  };

  return { createNote };
};

export default useCreateNote;
