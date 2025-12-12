import './TypeRow.css'
import Pencil from '../../../assets/pictos/pencil.svg'
import Trash from '../../../assets/pictos/trash.svg'

export default function TypeRow({index, type, setType, displayEditModal, setDisplayEditModal, setDiplayDeleteModal}){
    const handleEdit = () => {
        setDisplayEditModal(!displayEditModal)
    }
    const handleDelete = () => {
        setDiplayDeleteModal(true)
    }

    return (
    <tr>
        <td>{index+1}</td>
        <td>{type.name}</td>
        <td>{type.description}</td>
        <td>
            <button 
                onClick={() => {
                    handleEdit()
                    setType(type)
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
                    setType(type)
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