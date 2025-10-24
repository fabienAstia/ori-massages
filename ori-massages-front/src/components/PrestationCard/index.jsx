import './PrestationCard.css'
import Button from 'react-bootstrap/Button';

export default function PrestationCard({variant='home', setModalShow, prestation, setSelectedPrestation}){
    return ( 
    <>
    <div className={`card h-100 custom-style--${variant}`} onClick={() => {
                setModalShow(true); 
                setSelectedPrestation(prestation);
                }}>

        {variant==='services' &&
        <h5 className="card-title text-center">{prestation.name}</h5>}
        <img src={`/photos/${prestation.imagePath}`} className={`card-img-top custom-img--${variant}`} alt={prestation.name}/>
        <div className='card-body d-flex flex-column gap-1'>

            {variant==='home' &&
            <p className="card-title text-center">{prestation.label}</p>}
            
            {prestation.description && 
            <>
            <div className='row cols-row-2 text-center fs-3 fw-bold '>
                <div className='col text-center'>
                     {prestation.duration.label}
                </div>
                <div className='col'>
                    {prestation.price + '€'}
                </div>
            </div>
            <div className='row d-flex align-items-center justify-content-center'>
                <p className="card-text">{prestation.description}</p>
            </div>
            <Button variant="primary" onClick={() => {
                setModalShow(true); 
                setSelectedPrestation(prestation);
                }}>
                Réserver
            </Button>       
            </>
            }
        </div>
    </div>
    </>
    );
}