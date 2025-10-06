import {lazy, Suspense} from 'react';
import {Routes, Route} from 'react-router-dom';
import Layout from '../components/Layout'

const Home = lazy(()=> import ('../views/Home'))
const Services = lazy(()=> import ('../views/Services'))
const SolidarityFund = lazy(()=> import ('../views/SolidarityFund'))
const AboutMe = lazy(()=> import('../views/AboutMe'))
const Contact = lazy(()=> import ('../views/Contact'))


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
                </Route>
            </Routes>
        </Suspense>
    );
}