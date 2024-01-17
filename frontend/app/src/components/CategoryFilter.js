import React, { useState } from 'react';

const CategoryFilter = ({ onApplyFilter, onCancel }) => {

    const [categoryInput, setCategoryInput] = useState('');
    const [includeArchived, setIncludeArchived] = useState(false);

    const handleApplyFilter = () => {
        onApplyFilter(includeArchived, categoryInput);
    };

    return (
        <div>
            <label>Enter category:</label>
            <input
                type="text"
                value={categoryInput}
                onChange={({ target: { value } }) => setCategoryInput(value)}
            />
            <div className='under-label'>
                <button onClick={handleApplyFilter}>Apply Filter</button>
                <button onClick={onCancel}>Cancel</button>
                <label>Filter archived notes</label>
                <input className='checkbox-label'
                    type="checkbox"
                    checked={includeArchived}
                    onChange={() => setIncludeArchived(!includeArchived)}
                />
            </div>
        </div>
    );
};

export default CategoryFilter;