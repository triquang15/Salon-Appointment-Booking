import React from 'react'
import SalonDetail from './SalonDetail'
import { Button, Divider } from '@mui/material'
import SalonServiceDetail from './SalonServiceDetail'

const tabs = [{name: "All Services"}, {name: "Reviews"}, {name: "Add Review"}]

const SalonDetails = () => {
  const [activeTab, setActiveTab] = React.useState(tabs[0].name);
  const handleActiveTab = (tabName) => {
    setActiveTab(tabName);
  }

  return (
    <div className='px-5 lg:px-20'>
      <SalonDetail />
      <div className='space-y-4'>
        <div className='flex gap-2'>
          {tabs.map((tab) => (
            <Button onClick={() => handleActiveTab(tab.name)} variant={activeTab === tab.name ? "contained" : "outlined"}>{tab.name}</Button>
          ))}
        </div>
        <Divider />
      </div>
      <div>
        {activeTab === "All Services" && (
          <div>
            <SalonServiceDetail />
          </div>
        )}
        {activeTab === "Reviews" && (
          <div>
            <h2 className='text-2xl font-semibold mb-5'>Reviews</h2>
          </div>
        )}
        {activeTab === "Add Review" && (
          <div>
            <h2 className='text-2xl font-semibold mb-5'>Add Review</h2>
          </div>
        )}
      </div>
    </div>
  )
}

export default SalonDetails
