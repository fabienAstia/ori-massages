import './AppointmentRow.css'
import { frenchDate } from '../../utils/dateUtils'
import {Link} from 'react-router-dom'
import PossibleStatuses from '../PossibleStatuses'

export default function AppointmentRow({appointment, index, onChangeStatus}){

    const addressOrLocationName = (appointment)=>{
        if(appointment.atHome){
            return appointment.address
        }else{
            return appointment.locationName
        }
    }

    function handleChangeStatus(targetStatus){
        onChangeStatus(targetStatus, appointment.id)
    }

    const disabled = appointment.possibleStatuses.length === 0

    return (
        <tr>
            <td>{index+1}</td>
            <td ><Link to='/users'>{appointment.userFullName}</Link></td>
            <td>{appointment.prestationName}</td>
            <td>{`${appointment.beginAt}/${appointment.endReal}`}</td>
            <td>{frenchDate(appointment.dateMeeting)}</td>
            <td>{frenchDate(appointment.dateCreation)}</td>
            <td>{addressOrLocationName(appointment)}</td> 
            <td>
                <span style={{color: appointment.status.color}}>
                    <b>
                        {appointment.status.label.toUpperCase()}
                    </b>
                </span>
            </td>
            <td>
                <PossibleStatuses
                    possibleStatuses = {appointment.possibleStatuses}
                    onChangeStatus = {handleChangeStatus}
                    disabled = {disabled}
                />
            </td>
        </tr>
    )
}