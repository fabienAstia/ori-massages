import './StatusFilter.css'
import Dropdown from 'react-bootstrap/Dropdown'
import DropdownButton from 'react-bootstrap/DropdownButton'

export default function StatusFilter(props){
    const STATUS_CODE_ALL = 'ALL'

    const dropItems = props.statuses?.map((status) =>
        <Dropdown.Item 
            key={status.code}
            onClick={()=> {
                props.setSelectedStatus(status.code)
            }}
            style={{color: status.color}}
        >
            {status.label.toUpperCase()}
        </Dropdown.Item>
    )


    return (
        <DropdownButton 
            id="status-filter" 
            title={props.title}
            size={props.size}
            variant={props.variant}
        >
            {dropItems}
            <Dropdown.Item 
                onClick={()=> {
                    props.setSelectedStatus(STATUS_CODE_ALL)
                }}
            >
                {STATUS_CODE_ALL}
            </Dropdown.Item>
        </DropdownButton>
    )
}