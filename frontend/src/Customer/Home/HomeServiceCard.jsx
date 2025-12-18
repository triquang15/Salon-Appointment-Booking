import React from 'react'

const HomeServiceCard = ({ item }) => {
  const { title, description, icon: Icon } = item

  return (
    <div className="border p-6 rounded-xl shadow text-center hover:shadow-lg transition bg-white">
      
      {Icon && (
        <div className="flex justify-center mb-4">
          <Icon className="w-12 h-12 text-pink-500" />
        </div>
      )}

      <h3 className="font-semibold text-lg mb-2">{title}</h3>
      <p className="text-gray-600 text-sm">{description}</p>
    </div>
  )
}

export default HomeServiceCard
