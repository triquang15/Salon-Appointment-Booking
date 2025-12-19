import React from 'react'

const CategoryCard = ({ handleCategoryClick, selectedCategory, item }) => {
    return (
        <div onClick={handleCategoryClick}
            className={`px-3 py-2 cursor-pointer flex gap-2 items-center mb-3 rounded-md border ${selectedCategory === item ? 'bg-green-100 border-green-500' : 'border-gray-300'}`}>
            <img className='w-14 h-14 object-cover rounded-full' src="https://i.pinimg.com/736x/95/b1/9b/95b19b60ed907c7347053e3af9d12ce9.jpg" alt="" />
            <h1>Bridal Makeup</h1>
        </div>
    )
}

export default CategoryCard
