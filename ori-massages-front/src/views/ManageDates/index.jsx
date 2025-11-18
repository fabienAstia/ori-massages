import './ManageDates.css'
import 'react-day-picker/style.css'
import { useEffect, useState } from 'react';
import {DayPicker} from 'react-day-picker';
import { Row, Col } from 'react-bootstrap';
import axios from 'axios';
import {toLocalDate, getDateWithoutOffset, formatDate} from '../../utils/dateUtils'
const apiUrl = import.meta.env.VITE_API_URL;

export default function ManageDates(){
    const [selectedDate, setSelectedDate] = useState(null)
    const [bookedDays, setBookedDays] = useState(null)
    const [unavailableDays, setUnavailableDays] = useState(null)
    console.log('selectedDate', selectedDate)

    async function persistDate(date){
        if(!date) return "there is no date"
        const firstDateOffset = toLocalDate(date.from);
        const lastDateOffset = toLocalDate(date.to);
        const firstDate = getDateWithoutOffset(firstDateOffset)
        const lastDate = getDateWithoutOffset(lastDateOffset);
        try {
            const resp = await axios.post(`${apiUrl}/dates/unavailable`, 
                {
                    from:firstDate,
                    to:lastDate
                })
            if(resp){
                const bookedDateArray = resp.data.bookedDays;
                const bookedArray = bookedDateArray.map(date => new Date(date))
                setBookedDays(bookedArray)

                const unavailableDateArray = resp.data.unavailableDays;
                const unavailableArray = unavailableDateArray.map(date => new Date(date))
                setUnavailableDays(unavailableArray)
            }
            alert('PERSISTED !')
        } catch(err){
            if(err.response){
                console.log('error', err.response.data)
            } else if (err.request) {
                console.log(err.request)
            }
        }  
    }

    useEffect(() => {
        async function getAllDates(){
            try {
                const resp = await axios.get(`${apiUrl}/dates`)
                if(resp) {
                    const bookedDateArray = resp.data.bookedDays;
                    const bookedArray = bookedDateArray.map(date => new Date(date))
                    const unavailableDateArray = resp.data.unavailableDays;
                    const unavailableArray = unavailableDateArray.map(date => new Date(date))
                    setBookedDays(bookedArray);
                    setUnavailableDays(unavailableArray);
                    console.log('bookedArray=', bookedArray)
                }
            } catch(err) {
                if(err.response){
                    console.log('err', err.response.data)
                }
            }
        }getAllDates();
    }, [])

    // useEffect(()=> {
    //     console.log(`bookedDays= ${bookedDays?bookedDays:null}`)
    // }, [bookedDays])

    return (
        <div className='manage-date'>
            <h1>Choisir les dates d'indisponibilité</h1>
            <div className='d-flex justify-space-between'>
                <Row>
                    <Col sm={12} md={6}>
                        <DayPicker 
                            animate
                            mode="range"
                            onSelect={setSelectedDate}
                            selected={selectedDate}
                            footer={
                                selectedDate 
                                ? `${formatDate(selectedDate?.from)} - ${formatDate(selectedDate?.to)}` 
                                : "Choisissez un ou plusieurs jour(s)."
                            }
                            modifiers={{
                                booked: bookedDays? bookedDays : null,
                                unavailable: unavailableDays ? unavailableDays : null
                            }}
                            modifiersClassNames={{
                                booked: "booked",
                                unavailable: "unavailable" 
                            }}
                        />
                    </Col>
                    <Col sm={12} md={6}>
                        <div className='selected-dates d-flex text-center'>
                            <h2>Dates indisponibles</h2>
                            <div className='m-2 w-100'>
                                <Row className='w-100 '>
                                    <Col xs={8}>
                                        <span className='text-center w-100'>{formatDate(selectedDate?.from)}-{formatDate(selectedDate?.to)}</span>    
                                    </Col>
                                    <Col xs={2}>
                                        <button 
                                            onClick={() => persistDate(selectedDate)}
                                            className='btn btn-success'
                                        >V</button>
                                    </Col>
                                    <Col xs={2}>
                                        <button className='btn btn-danger'>X</button>
                                    </Col>
                                </Row>
                            </div>
                        </div>
                    </Col>
                </Row>
            </div>
        </div>
    );
}