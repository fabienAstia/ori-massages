import './AppointmentRow.css'
import { frenchDate } from '../../utils/dateUtils'
import {Link} from 'react-router-dom'

export default function AppointmentRow({appointment, index}){

    const addressOrLocationName = (appointment)=>{
        if(appointment.isAtHome){
            return appointment.address
        }else{
            return appointment.locationName
        }
    }
    return (
        <tr>
            <td>{index+1}</td>
            <td ><Link to='/users'>{appointment.userFullName}</Link></td>
            <td>{appointment.prestationName}</td>
            <td>{frenchDate(appointment.date)}</td>
            <td>{`${appointment.beginAt}/${appointment.endReal}`}</td>
            <td>{addressOrLocationName(appointment)}</td> 
            <td>{appointment.status}</td>
            
        </tr>
    )
}