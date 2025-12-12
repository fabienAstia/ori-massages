import './WorkingHoursRow.css'
import Pencil from '../../../assets/pictos/pencil.svg'
import Trash from '../../../assets/pictos/trash.svg'

export default function WorkingHoursRow({index, workingHour, setWorkingHour, setDisplayEditModal, setDiplayDeleteModal}){
    
    return (
        <tr>
            <td>{index+1}</td>
            <td>{workingHour.startTime}</td>
            <td>{workingHour.endTime}</td>
            <td>{workingHour.name}</td>
            <td>
                <button 
                    onClick={() => {
                        setDisplayEditModal(true)
                        setWorkingHour(workingHour)
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
                        setWorkingHour(workingHour)
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
    )
}