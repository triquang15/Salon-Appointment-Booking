import React, { useState } from 'react'
import CategoryForm from './CategoryForm'
import CategoryTable from './CategoryTable'

const CategoryPage = () => {
  const [categories, setCategories] = useState([
    {
      id: 1,
      name: 'Hair',
      image: 'https://images.pexels.com/photos/4625616/pexels-photo-4625616.jpeg',
    },
    {
      id: 2,
      name: 'Nail',
      image: 'https://images.pexels.com/photos/4625616/pexels-photo-4625616.jpeg',
    },
  ])

  const addCategory = (category) => {
    setCategories([...categories, category])
  }

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Categories</h1>

      <CategoryForm onAdd={addCategory} />
      <CategoryTable categories={categories} />
    </div>
  )
}

export default CategoryPage
