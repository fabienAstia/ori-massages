import './BookingCalendar.css'
import 'react-day-picker/style.css'
import { useState } from 'react';
import {DayPicker} from 'react-day-picker';
import { se } from 'react-day-picker/locale';

export default function BookingCalendar({onChangeDate}){
    const [selectedDate, setSelectedDate] = useState(null);
 
    return(
        <DayPicker
            animate
            mode="single"
            // disabled={{ dayOfWeek: [0, 6] }}
            selected={selectedDate}
            onSelect={(selectedDate) => {
                setSelectedDate(selectedDate);
                onChangeDate(selectedDate)
                console.log(selectedDate, 'date on Day Picker')
            }}
            footer={
                selectedDate ? `Selected: ${selectedDate.toLocaleDateString()}` : "Choisissez un jour."
            }
        />
    );
}