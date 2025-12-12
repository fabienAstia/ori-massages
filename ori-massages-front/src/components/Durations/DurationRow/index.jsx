import './DurationRow.css'
import Pencil from '../../../assets/pictos/pencil.svg'
import Trash from '../../../assets/pictos/trash.svg'

export default function DurationRow(props){

    const handleEdit = () => {
        props.setDisplayEditModal(!props.displayEditModal)
    }
    const handleDelete = () => {
        props.setDiplayDeleteModal(true)
    }

    return (
        <tr>
        <td>{props.index + 1}</td>
        <td>{props.duration.value}</td>
        <td>{props.duration.label}</td>
        <td>{props.duration.breakTime}</td>
        <td>
            <button 
                onClick={() => {
                    handleEdit()
                    props.setDuration(props.duration)
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
                    handleDelete()
                    props.setDuration(props.duration)
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