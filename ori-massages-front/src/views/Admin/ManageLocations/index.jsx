import './ManageLocations.css'
import add from '../../../assets/pictos/add.svg'
import axios from 'axios'
import { useEffect, useState } from 'react'
import LocationRow from '../../../components/Locations/LocationRow'
import Table from 'react-bootstrap/Table'
import LocationEditModal from '../../../components/Locations/LocationEditModal'
import LocationDeleteModal from '../../../components/Locations/LocationDeleteModal'
const apiUrl = import.meta.env.VITE_API_URL

export default function ManageLocations(){

    const [locations, setLocations] = useState([])
    const [location, setLocation] = useState(null)
    const [displayEditModal, setDisplayEditModal] = useState(false)
    const [modifiedLocation, setModifiedLocation] = useState(null)
    const [displayDeleteModal, setDisplayDeleteModal] = useState(false)
    const [deleteLocation, setDeleteLocation] = useState(null)


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
            setDisplayEditModal={setDisplayEditModal}
            setDisplayDeleteModal={setDisplayDeleteModal}
            setLocation={setLocation}
        />
    )

    useEffect(()=> {
        getLocations()
    }, [modifiedLocation, deleteLocation])

    return (
        <div className='manage-locations'>
            <div className='section-header'>
                    <h1 className='mx-auto'>Gérer les Lieux</h1>
                    <div className='add-type'>
                        <button 
                            className='add-type-btn'
                            onClick={() => {
                                setDisplayEditModal(true)
                                setLocation(null)
                            }}
                        >
                            <img src={add} alt="button to add a new Location" /> 
                        </button>
                    </div>
                </div>

            <Table striped bordered hover>
                <thead className='text-center align-middle'>
                    <tr>
                    <th>#</th>
                    <th>Image</th>
                    <th>Nom du lieu</th>
                    <th>À domicile ?</th>
                    <th>Adresse</th>
                    <th>Actions</th>
                    </tr>
                </thead>
                <tbody className='text-center align-middle'>
                    {locationsRows}
                </tbody>
            </Table>

            <LocationEditModal 
                show={displayEditModal}  
                onHide={() => setDisplayEditModal(false)}
                location={location}
                setModifiedLocation={setModifiedLocation}   
            />
            
            <LocationDeleteModal 
                show={displayDeleteModal}
                onHide={() => setDisplayDeleteModal(false)}
                location={location}
                setDeleteLocation={setDeleteLocation}
            />
        </div>
    )
}