import './LocationRow.css'
import Trash from '../../../assets/pictos/trash.svg'
import Pencil from '../../../assets/pictos/pencil.svg'


export default function LocationRow({location, index}){
    return(
    <tr>
        <td>{index+1}</td>
        <td>{location.name}</td>
        <td>{location.imagePath}</td>
        <td>{location.atHome ? "🏠" : "—"}</td>
        <td>{location.address}</td>
        <td>
            <button 
                onClick={() => {
                    setDisplayEditModal(true)
                    // setWorkingHour(workingHour)
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
                    setDiplayDeleteModal(true)
                    // setWorkingHour(workingHour)
                }} 
                className='deleteButton'
            >
                <img 
                    src={Trash} 
                    alt="to delete the selected type" 
                    className='p-1'
                />
            </button>
            
        </td>
    </tr>
    );
}