import React from 'react'
import SalonDetail from './SalonDetail'
import { Button, Divider } from '@mui/material'
import SalonServiceDetail from './SalonServiceDetail'
import Review from '../../Customer/Review/Review'
import AddReview from '../../Customer/Review/AddReview'

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
            <Review />
          </div>
        )}
        {activeTab === "Add Review" && (
          <div>
            <AddReview />
          </div>
        )}
      </div>
    </div>
  )
}

export default SalonDetails
