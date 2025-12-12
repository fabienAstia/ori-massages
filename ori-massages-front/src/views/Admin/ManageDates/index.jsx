import './ManageDates.css'
import 'react-day-picker/style.css'
import { useEffect, useState } from 'react';
import {DayPicker} from 'react-day-picker';
import { Row, Col } from 'react-bootstrap';
import axios from 'axios';
import {toLocalDate, getDateWithoutOffset, formatDate} from '../../../utils/dateUtils'
const apiUrl = import.meta.env.VITE_API_URL;

export default function ManageDates(){
    const [selectedDate, setSelectedDate] = useState(null)
    const [availableDays, setAvailableDays] = useState(null)
    const [unavailableDays, setUnavailableDays] = useState(null)
    const [bookedDays, setBookedDays] = useState(null)
    const [availability, setAvailability] = useState(null);

    async function persistDate(date){
        if(!date) return "there is no date"
        const firstDateOffset = toLocalDate(date.from);
        const lastDateOffset = toLocalDate(date.to);
        const firstDate = getDateWithoutOffset(firstDateOffset)
        const lastDate = getDateWithoutOffset(lastDateOffset);

        try {
            const resp = await axios.post(`${apiUrl}/dates/availability`,  {
                        from:firstDate,
                        to:lastDate,
                        availability : availability
            })

            if(resp){
                const availableDateArray = resp.data.availableDays;
                const availableArray = availableDateArray.map(date => new Date(date))
                setAvailableDays(availableArray)

                const unavailableDateArray = resp.data.unavailableDays;
                const unavailableArray = unavailableDateArray.map(date => new Date(date))
                setUnavailableDays(unavailableArray)

                const bookedDateArray = resp.data.bookedDays;
                const bookedArray = bookedDateArray.map(date => new Date(date))
                setBookedDays(bookedArray)

                setAvailability(null)
                setSelectedDate(null)
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
                    const availableDateArray = resp.data.availableDays;
                    const availableArray = availableDateArray.map(date=> new Date(date))
                    const unavailableDateArray = resp.data.unavailableDays;
                    const unavailableArray = unavailableDateArray.map(date => new Date(date))
                    setAvailableDays(availableArray);
                    setUnavailableDays(unavailableArray);
                    setBookedDays(bookedArray);
                    console.log('bookedArray=', bookedArray)
                }
            } catch(err) {
                if(err.response){
                    console.log('err', err.response.data)
                }
            }
        }getAllDates();
    }, [])

    return (
        <div className='manage-date'>
            <h1>Gérer les dates</h1>
            <Row>
                <Col sm={12} lg={6}>
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
                            available: availableDays ? availableDays : null,
                            unavailable: unavailableDays ? unavailableDays : null,
                            booked: bookedDays? bookedDays : null
                        }}
                        modifiersClassNames={{
                            available: "available",
                            unavailable: "unavailable",
                            booked: "booked"
                        }}
                    />
                </Col>

                <Col sm={12} lg={6} >
                    <div className='col-wrapper'>
                        <div className='legend'>
                            <div className='d-flex align-items-center gap-2 mb-2'>
                                <div className='legendColor green'></div>
                                <span className='fw-bold'> disponibles </span>
                            </div>
                         
                            <div className='d-flex align-items-center gap-2 mb-2'>
                                <div className='legendColor red'></div>
                                <span className='fw-bold'> indisponibles </span>
                            </div>
                            <div className='d-flex align-items-center gap-2 mb-2'>
                                <div className='legendColor purple'></div>
                                <span className='fw-bold'> réservées </span>
                            </div>
                        </div>

                        <div className='selected-dates d-flex text-center'>
                            <h2>Dates sélectionnées</h2>
                            <Row className='w-100 my-2'>
                                <Col xs={12} className='selectedDates'>
                                    <span className='text-center w-100'>
                                        {selectedDate 
                                            ? `${formatDate(selectedDate?.from)} - ${formatDate(selectedDate?.to)}`
                                            : "Aucune date sélectionnée"
                                        }
                                    </span>    
                                </Col>
                            </Row>
                            <Row className='w-100 my-2'>
                                <Col xs={12} className='availability-row'>
                                    <div className='radio-option'>
                                        <input 
                                            type="radio"
                                            name="set-availability"
                                            value="MAKE_AVAILABLE" 
                                            id="yes" 
                                            onChange={(e) => setAvailability(e.target.value)}
                                            checked={availability === 'MAKE_AVAILABLE'}
                                        /> 
                                        <label htmlFor="yes">disponibles</label>
                                    </div>
                                    <div className='radio-option'>
                                        <input 
                                            type="radio"
                                            name="set-availability"
                                            value="MAKE_UNAVAILABLE" 
                                            id="no" 
                                            onChange={(e) => setAvailability(e.target.value)}
                                            checked={availability === 'MAKE_UNAVAILABLE'}
                                        />
                                        <label htmlFor="no">indisponibles</label> 
                                    </div>
                                </Col>
                            </Row>
                            <Row className='w-100 my-2'>
                                <Col xs={6}>
                                    <button 
                                        onClick={() => persistDate(selectedDate)}
                                        className='btn btn-primary w-100'
                                        disabled = {!selectedDate || !availability}
                                    >
                                    Enregistrer
                                    </button>
                                </Col>
                                <Col xs={6}>
                                    <button 
                                        className='btn btn-secondary w-100'
                                        type='reset'
                                        onClick={() => {setAvailability(null); setSelectedDate(null)}}
                                        disabled={!selectedDate && !availability}
                                    >
                                    Reset
                                    </button>
                                </Col>
                            </Row>
                        </div>
                    </div>
                </Col>
            </Row>
        </div>
    );
}