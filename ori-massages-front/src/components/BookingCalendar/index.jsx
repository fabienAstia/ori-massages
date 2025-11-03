import './BookingCalendar.css'
import 'react-day-picker/style.css'
import { useState } from 'react';
import {DayPicker} from 'react-day-picker';

export default function BookingCalendar({onChangeDate}){
    const [selectedDate, setSelectedDate] = useState(null);
 
    return(
        <div className='booking-date'>
            <DayPicker
            animate
            mode="single"
            disabled={{ before: new Date() }}
            // disabled={{ dayOfWeek: [0, 6] }}
            selected={selectedDate}
            onSelect={(selectedDate) => {
                setSelectedDate(selectedDate);
                onChangeDate(selectedDate)
                console.log(selectedDate, 'date on Day Picker')
            }}
            footer={
                selectedDate ? `${selectedDate.toLocaleDateString()}` : "Choisissez un jour."
            }
        />
        </div>
    );
}