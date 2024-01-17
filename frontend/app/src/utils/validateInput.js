export const validateInput = (formData) => {
    const { title, content } = formData;
  
    if (!title || !content) {
      return false;
    }
  
    if (title.length > 50) {
      return false;
    }
  
    return true;
  };