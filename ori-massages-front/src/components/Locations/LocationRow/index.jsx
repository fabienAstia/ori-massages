import './LocationRow.css'
import Trash from '../../../assets/pictos/trash.svg'
import Pencil from '../../../assets/pictos/pencil.svg'
import addressToString from '../../../utils/addressUtils'
const apiUrl = import.meta.env.VITE_API_URL


export default function LocationRow({location, index, setDisplayEditModal, setDisplayDeleteModal, setLocation}){
    return(
    <tr>
        <td>{index+1}</td>
        <td >
            <div className='image-format'>
                <img src={`${apiUrl}/uploads/locations/${location.imagePath}`} alt="location's image"/>
            </div>
        </td>
        <td>{location.name}</td>
        <td>{location.atHome ? "🏠" : "—"}</td>
        <td>{addressToString(location.address)}</td>
        <td>
            <div>
                <button 
                    onClick={() => {
                        setDisplayEditModal(true)
                        setLocation(location)
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
                        setLocation(location)
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
    );
}