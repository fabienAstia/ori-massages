import './PrestationRow.css'
import Pencil from '../../../assets/pictos/pencil.svg'
import Trash from '../../../assets/pictos/trash.svg'
const apiUrl = import.meta.env.VITE_API_URL

export default function PrestationRow({prestation, index, setPrestation, setDisplayEditModal, setDisplayDeleteModal}){

    return(
        <tr>
            <td>{index+1}</td>
            <td>
                <img 
                    src={`${apiUrl}/uploads/prestations/${prestation.imagePath}`} 
                    id='image-row'
                    alt="prestation image" 
                />
            </td>
            <td>{prestation.name}</td>
            <td>{prestation.typeName}</td>
            <td>{prestation.displayOrder}</td>
            <td>{prestation.durationLabel}</td>
            <td>{prestation.price}€</td>
            <td>{prestation.description}</td>
            <td>{prestation.active ? "✔️" : "❌"}</td>
            <td>
                <div>
                    <button 
                        onClick={() => {
                            setDisplayEditModal(true)
                            setPrestation(prestation)
                        }}
                        className='editButton'
                    >
                        <img 
                            src={Pencil} 
                            alt="to edit the selected type" 
                            className='p-1' 
                        />
                    </button>
                    
                    <button 
                        onClick={() => {
                            setDisplayDeleteModal(true)
                            setPrestation(prestation)
                        }} 
                        className='deleteButton'
                    >
                        <img 
                            src={Trash} 
                            alt="to delete the selected type" 
                            className='p-1'
                        />
                    </button>
                </div>
            </td>
        </tr>
    )
}