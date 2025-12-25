import React, { useState } from 'react'

const CategoryForm = ({ onAdd }) => {
  const [name, setName] = useState('')
  const [image, setImage] = useState(null)

  const handleSubmit = (e) => {
    e.preventDefault()

    if (!name || !image) return

    const newCategory = {
      id: Date.now(),
      name,
      image: URL.createObjectURL(image),
    }

    onAdd(newCategory)
    setName('')
    setImage(null)
  }

  return (
    <form
      onSubmit={handleSubmit}
      className="bg-white p-5 rounded-lg shadow mb-6"
    >
      <h2 className="text-lg font-semibold mb-4">Add Category</h2>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Category Name */}
        <input
          type="text"
          placeholder="Category name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          className="border p-2 rounded"
        />

        {/* Image Upload */}
        <input
          type="file"
          accept="image/*"
          onChange={(e) => setImage(e.target.files[0])}
          className="border p-2 rounded"
        />
      </div>

      <button
        type="submit"
        className="mt-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
      >
        Add Category
      </button>
    </form>
  )
}

export default CategoryForm
