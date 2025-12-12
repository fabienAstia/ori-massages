import './ManageWorkingHours.css'
import { useEffect, useState } from 'react'
import add from '../../../assets/pictos/add.svg'
import Table from 'react-bootstrap/Table'
import WorkingHoursRow from '../../../components/WorkingHours/WorkingHoursRow'
import WorkingHoursEditModal from '../../../components/WorkingHours/WorkingHoursEditModal'
import WorkingHoursDeleteModal from '../../../components/WorkingHours/WorkingHoursDeleteModal'
import axios from 'axios'
const apiUrl = import.meta.env.VITE_API_URL

export default function ManageWorkingHours(){
    const [workingHours, setWorkingHours] = useState(null)
    const [workingHour, setWorkingHour] = useState(null)
    const [displayEditModal, setDisplayEditModal] = useState(false)
    const [displayDeleteModal, setDiplayDeleteModal] = useState(false)
    const [modfiedWorkingHour, setModifiedWorkingHour] = useState(null)
    const [deleteWorkingHour, setDeleteWorkingHour] = useState(null)

    async function getWorkingHours(){
        try{
            const resp = await axios.get(`${apiUrl}/workingHours`)
            setWorkingHours(resp.data)
        }catch(err){
            if(err.response)console.log(err.response)
            if(err.request)console.log(err.request)
        }
    }

    const workingHoursRows = workingHours?.map((workingHour, i) => 
        <WorkingHoursRow 
            key={workingHour.id}
            index={i}
            workingHour={workingHour}
            setWorkingHour={setWorkingHour}
            setDisplayEditModal={setDisplayEditModal}
            setDiplayDeleteModal={setDiplayDeleteModal}
        />
    )

    useEffect(()=> {
        getWorkingHours()
    }, [modfiedWorkingHour, deleteWorkingHour])

    return (
        <div className='manage-workingHours'>
            <div className='section-header'>
                <h1 className='mx-auto'>Gérer les Horaires</h1>
                <div className='add-type'>
                    <button 
                        className='add-type-btn'
                        onClick={() => {
                            setDisplayEditModal(true)
                            setWorkingHour(null)
                        }}
                    >
                        <img src={add} alt="button to add a new Type" /> 
                    </button>
                </div>
            </div>

            <Table striped bordered hover>
                <thead className='text-center align-middle'>
                    <tr>
                    <th>#</th>
                    <th>Heure du début</th>
                    <th>Heure de fin</th>
                    <th>Nom</th>
                    <th>Actions</th>
                    </tr>
                </thead>
                <tbody className='text-center align-middle'>
                    {workingHoursRows}
                </tbody>
            </Table>

            <WorkingHoursEditModal 
                show={displayEditModal}
                onHide={() => setDisplayEditModal(false)}
                workingHour={workingHour}
                setModifiedWorkingHour={setModifiedWorkingHour}
            />

            <WorkingHoursDeleteModal 
                show={displayDeleteModal}
                onHide={() => setDiplayDeleteModal(false)}
                workingHour={workingHour}
                setDeleteWorkingHour={setDeleteWorkingHour}
            />
        </div>
    )
}