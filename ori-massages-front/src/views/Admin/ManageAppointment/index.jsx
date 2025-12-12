import './ManageAppointment.css'
import { useEffect, useState } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';
import AppointmentRow from '../../../components/AppointmentRow';
const apiUrl = import.meta.env.VITE_API_URL;

export default function ManageAppointment(){
    const [appointments, setAppointments] = useState(null)
    
    useEffect(()=> {   
        async function getAppointment(){
            try {
                const resp = await axios.get(`${apiUrl}/appointments`)
                setAppointments(resp.data)
            } catch(err){
                if(err.response){
                    console.log('err', err.response)
                }else if(err.request){
                    console.log(err.request)
                }
            }
        }
        getAppointment()
    }, [])

    const appointmentsRows =appointments?.map((appointment, i) =>
            <AppointmentRow 
            key={appointment.id} 
            appointment={appointment}
            index = {i}
            />
    )

    return (
        <div className='manage-appointment'>
            <div className='text-center'>
                <h1>Rendez-vous</h1>

                <Table striped bordered hover className='align-middle'>
                    <thead>
                        <tr>
                        <th>#</th>
                        <th>Client</th>
                        <th>Prestation</th>
                        <th>Date</th>
                        <th>Créneau</th>
                        <th>Adresse</th>
                        <th>Statut</th>
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