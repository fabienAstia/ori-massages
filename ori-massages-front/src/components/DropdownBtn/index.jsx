import './DropdownBtn.css'
import { useEffect, useState } from 'react'
import Dropdown from 'react-bootstrap/Dropdown'
import DropdownButton from 'react-bootstrap/DropdownButton'

export default function DropdownBtn(props){
    const [variant, setVariant] = useState('')
    const [size, setSize] = useState('')

    useEffect(()=> {
        if(props){
            setVariant(props.variant)
            setSize(props.size)
        }
    }, [])

    return (
        <DropdownButton 
            id="dropdown-button" 
            size={size}
            variant={variant}
        >
            <Dropdown.Item href="#/action-1">Action</Dropdown.Item>
            <Dropdown.Item href="#/action-2">Another action</Dropdown.Item>
            <Dropdown.Item href="#/action-3">Something else</Dropdown.Item>
        </DropdownButton>
    )
}