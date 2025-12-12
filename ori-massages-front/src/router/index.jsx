import {lazy, Suspense} from 'react';
import {Routes, Route} from 'react-router-dom';
import Layout from '../components/Layout'
import LayoutAdmin from '../components/LayoutAdmin';

const Home = lazy(()=> import ('../views/Home'))
const Services = lazy(()=> import ('../views/Services'))
const SolidarityFund = lazy(()=> import ('../views/SolidarityFund'))
const AboutMe = lazy(()=> import('../views/AboutMe'))
const Contact = lazy(()=> import ('../views/Contact'))
const LegalNotices = lazy(() => import('../views/LegalNotices'))
const PrivacyPolicy = lazy(() => import('../views/PrivacyPolicy'))
const Admin = lazy(() => import ('../views/Admin'))
const ManageDates = lazy(() => import('../views/Admin/ManageDates'))
const ManageUsers = lazy(() => import ('../views/Admin/ManageUsers'))
const ManageAppointment = lazy(()=> import('../views/Admin/ManageAppointment'))
const ManagePrestations = lazy(()=> import('../views/Admin/ManagePrestations'))
const ManageTypes = lazy(()=> import('../views/Admin/ManageTypes'))
const ManageDurations = lazy(()=> import('../views/Admin/ManageDurations'))
const ManageWorkingHours = lazy(()=>import('../views/Admin/ManageWorkingHours'))
const ManageLocations = lazy(()=>import('../views/Admin/ManageLocations'))

export default function Router(){
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Routes>
                <Route path='/' element = {<Home/>}/>
                <Route element = {<Layout/>}>
                    <Route path='/services' element={<Services/>}/>
                    <Route path='/solidarity-fund' element={<SolidarityFund/>}/>
                    <Route path='/about-me' element={<AboutMe/>}/>
                    <Route path='/contact' element={<Contact/>}/>
                    <Route path='/legalNotices' element={<LegalNotices/>}/>
                    <Route path='/privacyPolicy' element={<PrivacyPolicy/>}/>
                </Route>
                <Route element= {<LayoutAdmin/>}>
                    {/* <Route path='/admin' element={<Admin/>}/> */}
                    <Route path='/dates' element={<ManageDates/>}/>
                    <Route path='/users' element={<ManageUsers/>}/>
                    <Route path='/appointments' element={<ManageAppointment/>}/>
                    <Route path='/prestations' element={<ManagePrestations/>}/>
                    <Route path='/types' element={<ManageTypes/>}/>
                    <Route path='/durations' element={<ManageDurations/>}/>
                    <Route path='/workingHours' element={<ManageWorkingHours/>}/>
                    <Route path='/locations' element={<ManageLocations/>}/>
                </Route>
            </Routes>
        </Suspense>
    );
}