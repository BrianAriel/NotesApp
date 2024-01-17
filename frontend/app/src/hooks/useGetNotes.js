import { useState, useEffect, useCallback } from 'react';

const useGetNotes = (url) => {
    const [notes, setNotes] = useState([]);

    const getNotes = useCallback(async (archived = null, category = null) => {
        try {
            const queryParams = [];
            
            if (archived !== null) {
                queryParams.push(`archived=${archived}`);
            }

            if (category !== null) {
                queryParams.push(`category=${category}`);
            }

            const queryString = queryParams.length > 0 ? queryParams.join('&') : '';

            const response = await fetch(`${url}/notes${queryString ? `?${queryString}` : ''}`);
            const data = await response.json();
            setNotes(data);
        } catch (error) {
            console.error('Error getting notes:', error);
        }
    }, [url]);

    useEffect(() => {
        getNotes();
    }, [getNotes])

    return { 
        data: notes, 
        getNotes
    };
};

export default useGetNotes;