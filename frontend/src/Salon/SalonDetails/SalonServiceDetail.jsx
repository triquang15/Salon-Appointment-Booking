import React from 'react'
import CategoryCard from './CategoryCard'
import ServiceCard from './ServiceCard'
import RemoveShoppingCart from '@mui/icons-material/RemoveShoppingCart'
import { ShoppingCart } from '@mui/icons-material'
import { Button } from '@mui/material'
import SelectedServiceList from './SelectedServiceList'

const SalonServiceDetail = () => {
    const [selectedCategory, setSelectedCategory] = React.useState(null)

    const handleCategoryClick = (category) => {
        setSelectedCategory(category)
    }

    // demo condition (sau này thay bằng cart.length > 0)
    const hasItems = true

    return (
        <div className="lg:flex gap-5 h-[90vh] mt-10">

            {/* Category */}
            <section className='space-y-5 border-r lg:w-[25%] pr-5'>
                {[1, 2, 3, 4, 5].map((item, index) => (
                    <CategoryCard
                        key={index}
                        item={index}
                        selectedCategory={selectedCategory}
                        handleCategoryClick={() => handleCategoryClick(index)}
                    />
                ))}
            </section>

            {/* Services */}
            <section className='space-y-2 lg:w-[50%] px-5 lg:px-20 overflow-y-auto'>
                {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((item, index) => (
                    <ServiceCard key={index} />
                ))}
            </section>

            {/* Cart */}
            <section className="lg:w-[25%]">
                <div className="border rounded-lg p-5 bg-white shadow-sm">

                    {hasItems ? (
                        <div>
                            {/* Header */}
                            <div className="flex items-center gap-2 pb-4 border-b">
                                <ShoppingCart sx={{ fontSize: 26, color: 'green' }} />
                                <h2 className="text-xl font-semibold text-gray-800">
                                    Your Cart
                                </h2>

                                <Button
                                    variant="contained"
                                    color="success"
                                    size="small"
                                    className="ml-auto"
                                    sx={{ textTransform: 'none' }}
                                    disabled
                                >
                                    Book now
                                </Button>
                            </div>

                            {/* Selected services */}
                            <SelectedServiceList />
                        </div>
                    ) : (
                        /* Empty State */
                        <div className="flex flex-col p-8 gap-3 items-center justify-center text-center">
                            <RemoveShoppingCart sx={{ fontSize: 48, color: '#ef4444' }} />

                            <h2 className="text-lg font-semibold text-gray-800">
                                No services added
                            </h2>

                            <p className="text-sm text-gray-500 max-w-[200px]">
                                Add services to your cart to book your appointment.
                            </p>
                        </div>
                    )}

                </div>
            </section>

        </div>
    )
}

export default SalonServiceDetail
