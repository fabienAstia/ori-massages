import './ManageAppointment.css'
import { useEffect, useState } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import AppointmentRow from '../../../components/AppointmentRow';
import StatusFilter from '../../../components/StatusFilter';
const apiUrl = import.meta.env.VITE_API_URL;

export default function ManageAppointment(){
    const [appointments, setAppointments] = useState([])
    const [selectedStatus, setSelectedStatus] = useState('')
    const STATUS_CODE_ALL = 'ALL'

    useEffect(()=> {   
        getAppointments()
    }, [])

    async function getAppointments(){
        try {
            const resp = await axios.get(`${apiUrl}/appointments`)
            setAppointments(resp.data)
         } catch (err){
            if(err.response) return console.log(err.response.data)
            if(err.request) return console.log(err.request)
            return console.log(err.message)
        }
    }

    let displayedAppointments = getDisplayedAppointments(selectedStatus)
    let presentStatuses = getPresentStatuses(appointments)
    let title = getTitle(selectedStatus, presentStatuses)

    function getPresentStatuses(appointments) {
        const response = []
        const statusesCode = []
        if(appointments){
             appointments.forEach(app => {
                if(!statusesCode.includes(app.status.code)){
                    statusesCode.push(app.status.code)
                    response.push(app.status)
                }
            });
        }
        return response
    }

    function getTitle(selectedStatus, presentStatuses){
        if(!selectedStatus || selectedStatus === STATUS_CODE_ALL) return STATUS_CODE_ALL
        if(selectedStatus && presentStatuses) {
            const correspondingStatus = presentStatuses.find(status => status.code === selectedStatus)
            if(!correspondingStatus) return STATUS_CODE_ALL
            return correspondingStatus.label.toUpperCase()
        }
        return STATUS_CODE_ALL
    }

    function getDisplayedAppointments(selectedStatus){
        if(!selectedStatus || selectedStatus === STATUS_CODE_ALL) {
            return appointments
        } else {
            return appointments.filter(app => app.status.code === selectedStatus)
        }
    }

    async function onChangeStatus(targetStatus, appointmentId){
        if(!targetStatus) return null
        try {
            const resp = await axios.post(`${apiUrl}/appointments/${appointmentId}`, 
                {targetStatus})
            getAppointments()
        }catch (err){
            if(err.response) return console.log(err.response.data)
            if(err.request) return console.log(err.request)
            return console.log(err.message)
        }
    }

    const appointmentsRows = displayedAppointments?.map((appointment, i) =>
            <AppointmentRow 
                key={appointment.id} 
                appointment={appointment}
                index = {i}
                onChangeStatus={onChangeStatus}
            />
    )

    return (
        <div className='manage-appointment'>
            <div className='text-center'>
                <h1>Rendez-vous</h1>

                <Table striped bordered hover className='align-middle'>
                    <thead className='align-middle'> 
                        <tr>
                        <th>#</th>
                        <th>Client</th>
                        <th>Prestation</th>
                        <th>Créneau</th>
                        <th>Date du RDV</th>
                        <th>Date de création</th>
                        <th>Adresse</th>
                        <th>
                            <div className='d-flex justify-content-center align-items-center'>
                                <span>Statut:</span>
                                <StatusFilter 
                                    size='sm'
                                    variant='Success'
                                    statuses={presentStatuses}
                                    setSelectedStatus={setSelectedStatus}
                                    title={title}
                                />
                            </div>
                        </th>
                        <th>Statuts possibles</th>
                        </tr>
                    </thead>
                    <tbody>
                       {appointmentsRows}
                    </tbody>
                </Table>
            </div>
        </div>
    );
}