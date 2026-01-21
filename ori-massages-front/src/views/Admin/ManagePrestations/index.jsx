import './ManagePrestations.css'
import { useEffect, useState } from 'react'
import { Table } from 'react-bootstrap'
import axios from 'axios'
import PrestationRow from '../../../components/Prestations/PrestationRow'
import PrestationEditModal from '../../../components/Prestations/PrestationEditModal'
import PrestationDeleteModal from '../../../components/Prestations/PrestationDeleteModal'
import add from '../../../assets/pictos/add.svg'
import sort from '../../../assets/pictos/sort.svg'
import ascendingOrder from '../../../assets/pictos/ascendingOrder.svg'
import descendingOrder from '../../../assets/pictos/descendingOrder.svg'
const apiUrl = import.meta.env.VITE_API_URL

export default function ManagePrestations(){
    const [prestations, setPrestations] = useState([])
    const [prestation, setPrestation] = useState(null)
    const [displayEditModal, setDisplayEditModal] = useState(false)
    const [hasBeenModified, setHasBeenModified] = useState(false)
    const [displayDeleteModal, setDisplayDeleteModal] = useState(false)
    const [sorted, setSorted] = useState({
        sort:'unsorted',
        column:''
    })
    const [prestationsDisplayed, setPrestationsDisplayed] = useState([])

    async function getPrestations(){
        try{
            const resp = await axios.get(`${apiUrl}/prestations`)
            setPrestations(resp.data)
            setPrestationsDisplayed(resp.data)
            console.log('prestations =', resp.data)
        }catch(err){
            if(err.response)console.log(err.response)
            if(err.request)console.log(err.request)
        }
    }

    useEffect(()=>{
        getPrestations()
    }, [hasBeenModified])


    useEffect(()=>{
        console.log('prestation=', prestation)
    }, [prestation])
    
    const prestationsRows= prestationsDisplayed?.map((prestation, i) =>
        <PrestationRow 
            key={prestation.id}
            index={i}
            prestation={prestation}
            setPrestation={setPrestation}
            setDisplayEditModal={setDisplayEditModal}
            setDisplayDeleteModal={setDisplayDeleteModal}
        />
    )

    function sortBy(attribute, column){
        if(sorted.sort === 'unsorted'){
            let sortedPrestations = Array.from(prestationsDisplayed).sort((a, b) => a[attribute].localeCompare(b[attribute]))
            setPrestationsDisplayed(sortedPrestations)
            setSorted({sort:'ascending', column:column})
            return;
        }
        if(sorted.sort === 'ascending'){
            let sortedPrestations = Array.from(prestationsDisplayed).sort((a, b) => b[attribute].localeCompare(a[attribute]))
            setPrestationsDisplayed(sortedPrestations)
            setSorted({sort:'descending', column:column})
            return;
        }
        if(sorted.sort === 'descending'){
            setPrestationsDisplayed(prestations)
            setSorted({sort:'unsorted', column:column})
        }
    }

    function handlePicto(column){
        if(sorted.sort === 'ascending' && sorted.column === column){
            return ascendingOrder
        }
        if(sorted.sort === 'descending' && sorted.column === column){
            return descendingOrder
        }
        return sort
    }

    return (
        <div className='manage-prestations'>
           <div className='section-header'>
                       <h1 className='mx-auto'>Gérer les Prestations</h1>
                       <div className='add-type'>
                           <button 
                               className='add-type-btn'
                               onClick={() => {
                                   setPrestation(null)
                                   setDisplayEditModal(true)
                               }}
                           >
                               <img src={add} alt="button to add a new Prestation" /> 
                           </button>
                       </div>
                   </div>

            <Table striped bordered hover className='align-middle'>
            <thead className='text-center align-middle'>
                <tr>
                <th>#</th>
                <th>Image</th>
                <th>
                    <div className='d-flex justify-content-center align-items-center'>
                        <span>Nom</span>
                        <button className='d-flex toSort align-items-center' onClick={()=>sortBy('name', 'Nom')}>
                            <img src={handlePicto('Nom')} alt="sort by name" id='picto-sort-name'/>
                        </button>
                    </div>
                </th>
                <th >
                    <div className='d-flex justify-content-center align-items-center'>
                        <span>Type</span>
                        <button className='d-flex toSort align-items-center' onClick={()=>sortBy('typeName', 'Type')}>
                            <img src={handlePicto('Type')} alt="sort by type" id='picto-sort-type'/>
                        </button>
                    </div>
                </th>
                <th>Ordre</th>
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
                hasBeenModified={hasBeenModified}
                setHasBeenModified={setHasBeenModified}
            />
            <PrestationDeleteModal
                show={displayDeleteModal}
                onHide={()=> setDisplayDeleteModal(false)}
                prestation={prestation}
                hasBeenModified={hasBeenModified}
                setHasBeenModified={setHasBeenModified}
            />
        </div>
    )
}