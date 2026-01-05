import './ManagePrestations.css'
import { useEffect, useState } from 'react'
import { Table } from 'react-bootstrap'
import axios from 'axios'
import PrestationRow from '../../../components/Prestations/PrestationRow'
import PrestationEditModal from '../../../components/Prestations/PrestationEditModal'
const apiUrl = import.meta.env.VITE_API_URL

export default function ManagePrestations(){
    const [prestations, setPrestations] = useState([])
    const [prestation, setPrestation] = useState(null)
    const [displayEditModal, setDisplayEditModal] = useState(false)

    async function getPrestations(){
        try{
            const resp = await axios.get(`${apiUrl}/prestations`)
            setPrestations(resp.data)
        }catch(err){
            if(err.response)console.log(err.response)
            if(err.request)console.log(err.request)
        }
    }

    useEffect(()=>{
        getPrestations()
    }, [])


    const prestationsRows= prestations?.map((prestation, i) =>
        <PrestationRow 
            key={prestation.id}
            index={i}
            prestation={prestation}
            setPrestation={setPrestation}
            setDisplayEditModal={setDisplayEditModal}
        />
    )

    return (
        <div className='manage-prestations'>
            <h1>Gérer les prestations</h1>

            <Table striped bordered hover className='align-middle'>
            <thead className='text-center align-middle'>
                <tr>
                <th>#</th>
                <th>Image</th>
                <th>Nom </th>
                <th>Type</th>
                <th>Durée</th>
                <th>Tarif</th>
                <th>Description</th>
                <th>Active?</th>
                <th>Actions</th>
                </tr>
            </thead>
            <tbody className='text-center'>
                {prestationsRows}
            </tbody>
            </Table>

            <PrestationEditModal 
                show={displayEditModal}
                onHide={()=> setDisplayEditModal(false)}
                prestation={prestation}
            />
        </div>
    )
}