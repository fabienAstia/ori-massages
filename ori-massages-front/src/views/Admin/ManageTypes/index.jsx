import './ManageTypes.css'
import { useEffect, useState } from 'react';
import Table  from 'react-bootstrap/Table';
import TypeRow from '../../../components/Types/TypeRow';
import axios from 'axios';
import TypeEditModal from '../../../components/Types/TypeEditModal';
import TypeDeleteModal from '../../../components/Types/TypeDeleteModal';
import add from '../../../assets/pictos/add.svg'
const apiUrl = import.meta.env.VITE_API_URL

export default function ManageTypes(){
    const [types, setTypes] = useState(null)
    const [type, setType] = useState(null)
    const [displayEditModal, setDisplayEditModal] = useState(false)
    const [displayDeleteModal, setDiplayDeleteModal] = useState(null)

    const [modfiedType, setModifiedType] = useState(null)
    const [deleteType, setDeleteType] = useState(null)

    async function getTypes(){
        try {
            const resp = await axios.get(`${apiUrl}/types`)
            setTypes(resp.data)
        }catch(err){
            if(err.response)console.log(err.response)
            if(err.request) console.log(err.request)
        }
    } 

    const typesRows = types?.map((type, i)=>
        <TypeRow 
            key={type.id}
            index={i} 
            type={type}
            setType={setType}
            displayEditModal={displayEditModal}
            setDisplayEditModal={setDisplayEditModal}
            setDiplayDeleteModal={setDiplayDeleteModal}
        />
    )

    useEffect(()=> {
        getTypes()
    }, [modfiedType, deleteType])

    return (
        <div className='manage-types'>
            <div className='section-header'>
                <h1 className='mx-auto'>Gérer les Types</h1>
                <div className='add-type'>
                    <button 
                        className='add-type-btn'
                        onClick={() => {
                            setDisplayEditModal(true)
                            setType(null)
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
                    <th>Nom du Type</th>
                    <th>Description</th>
                    <th>Actions</th>
                    </tr>
                </thead>
                <tbody className='text-center align-middle'>
                    {typesRows}
                </tbody>
            </Table>

            <TypeEditModal 
                show={displayEditModal}
                onHide={()=> setDisplayEditModal(false)}
                type={type}
                setModifiedType={setModifiedType}
            />
            <TypeDeleteModal 
                show={displayDeleteModal}
                onHide={() => setDiplayDeleteModal(false)}
                type= {type}
                setDeleteType={setDeleteType}
            />
        </div>
    );
}