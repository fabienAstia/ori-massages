import './ManageDurations.css'
import { useEffect, useState } from 'react'
import axios from 'axios'
import Table from 'react-bootstrap/Table'
import DurationRow from '../../../components/Durations/DurationRow'
import DurationEditModal from '../../../components/Durations/DurationEditModal'
import DurationDeleteModal from '../../../components/Durations/DurationDeleteModal'
import add from '../../../assets/pictos/add.svg'
const apiUrl = import.meta.env.VITE_API_URL

export default function ManageDurations(){

    const [durations, setDurations] = useState([])
    const [duration, setDuration] = useState(null)
    const [displayEditModal, setDisplayEditModal] = useState(false)
    const [displayDeleteModal, setDiplayDeleteModal] = useState(false)

    const [modifiedDuration, setModifiedDuration] = useState(null)
    const [deleteDuration, setDeleteDuration] = useState(null)

    async function getDurations(){
        try {
            const resp = await axios.get(`${apiUrl}/durations`)
            setDurations(resp.data)
        }catch(err){
            if(err.response) console.log(err.response)
            if(err.request) console.log(err.request)
        }
    }

    const durationsRows = durations?.map((duration, i) =>
        <DurationRow 
            key={duration.id}
            index={i}
            duration={duration}
            setDuration={setDuration}
            displayEditModal={displayEditModal}
            setDisplayEditModal={setDisplayEditModal}
            setDiplayDeleteModal={setDiplayDeleteModal}
        />
    )

    useEffect(()=> {
        getDurations()
    }, [modifiedDuration, deleteDuration])

    return (
        <div className='manage-durations'>
           <div className='section-header'>
                <h1 className='mx-auto'>Gérer les Durées</h1>
                <div className='add-type'>
                    <button 
                        className='add-type-btn'
                        onClick={() => {
                            setDisplayEditModal(true)
                            setDuration(null)
                        }}
                    >
                        <img src={add} alt="button to add a new Duration" /> 
                    </button>
                </div>
            </div>

            <Table striped bordered hover>
                <thead className='text-center align-middle'>
                    <tr>
                    <th>#</th>
                    <th>Durée de la prestation</th>
                    <th>Libellé</th>
                    <th>Temps de pause</th>
                    <th>Actions</th>
                    </tr>
                </thead>
                <tbody className='text-center align-middle'>
                    {durationsRows}
                </tbody>
            </Table>

            <DurationEditModal 
                show={displayEditModal}
                onHide={() => setDisplayEditModal(false)}
                duration={duration}
                setModifiedDuration={setModifiedDuration}
            />
            <DurationDeleteModal 
                show={displayDeleteModal}
                onHide={() => setDiplayDeleteModal(false)}
                duration={duration}
                setDeleteDuration={setDeleteDuration}
            />
        </div>
    )
}