import './ManageLocations.css'
import add from '../../../assets/pictos/add.svg'
import axios from 'axios'
import { useEffect, useState } from 'react'
import LocationRow from '../../../components/Locations/LocationRow'
import Table from 'react-bootstrap/Table'
const apiUrl = import.meta.env.VITE_API_URL

export default function ManageLocations(){

    const [locations, setLocations] = useState([])

    async function getLocations(){
        try {
            const resp = await axios.get(`${apiUrl}/locations`)
            setLocations(resp.data)
        } catch(err) {
            if(err.response) console.log(err.response)
            if(err.request) console.log(err.request)
        }
    }

    const locationsRows = locations?.map((location, i) => 
        <LocationRow
            key={location.id}
            index={i}
            location={location}
        />
    )

    useEffect(()=> {
        getLocations()
    }, [])

    return (
        <div className='manage-locations'>
            <div className='section-header'>
                    <h1 className='mx-auto'>Gérer les Lieux</h1>
                    <div className='add-type'>
                        <button 
                            className='add-type-btn'
                            onClick={() => {
                                setDisplayEditModal(true)
                                // setWorkingHour(null)
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
                    <th>Nom du lieu</th>
                    <th>Chemin de l'image</th>
                    <th>À domicile ?</th>
                    <th>Adresse</th>
                    <th>Actions</th>
                    </tr>
                </thead>
                <tbody className='text-center align-middle'>
                    {locationsRows}
                </tbody>
            </Table>

            {/* <DurationEditModal 
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
            /> */}
        </div>
    )
}