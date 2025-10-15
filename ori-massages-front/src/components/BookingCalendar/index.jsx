import './BookingCalendar.css'
import 'react-day-picker/style.css'
import { useState } from 'react';
import {DayPicker} from 'react-day-picker';

export default function BookingCalendar(){
    const [selectedDate, setSelectedDate] = useState(null);
    return(
        <DayPicker
            animate
            mode="single"
            // disabled={{ dayOfWeek: [0, 6] }}
            selected={selectedDate}
            onSelect={setSelectedDate}
            footer={
                selectedDate ? `Selected: ${selectedDate.toLocaleDateString()}` : "Choisissez un jour."
            }
        />
    );
}