import './PossibleStatuses.css'
import { useState, useEffect, useRef } from 'react'

export default function PossibleStatuses({possibleStatuses, onChangeStatus, disabled}){
    const [open, setOpen] = useState(false)
    const wrapperRef = useRef()

    const handleClickListener = (event) => {
        let clickedInside = (wrapperRef && wrapperRef.current.contains(event.target));
        if (clickedInside) return;
        else setOpen(false);
    }

    useEffect(()=> {
        document.addEventListener('pointerdown', handleClickListener)
        return () => {
            document.removeEventListener('pointerdown', handleClickListener)
        }
    }, [])

    const statusItems = possibleStatuses?.map(status =>
        <li 
            key={status.possibleStatus}
            className='row'
            style={{'--status-color': status.color}}
            onClick={()=> {
                if (disabled) return
                onChangeStatus(status.possibleStatus)
                setOpen(false)
            }}   
        >
            {status.label.toUpperCase()}
        </li>
    )
 
    return (
       <div ref={wrapperRef}
        className='possible-statuses'>
            <button 
                onClick={()=>{
                    if(disabled) return
                    setOpen(!open)
                }}
                className='button'
                disabled={disabled}
            >
                <span>
                    <b>⋮</b>
                </span> 
            </button>
            {open && 
                <div className='statuses'>
                    <ul className='list-style'>
                        {statusItems}
                    </ul>
                </div>
            }
       </div>
    )
}