import './ServiceCard.css'
import Button from 'react-bootstrap/Button';

export default function ServiceCard({title, image, duration, price, description, variant='home', setModalShow, setSelectedTitle, setSelectedImage, setSelectedDuration, setSelectedPrice, setSelectedDescription}){
    return (
    <>
    <div className={`card h-100 custom-style--${variant}`} onClick={() => {
                setModalShow(true); 
                setSelectedTitle(title); 
                setSelectedImage(image);
                setSelectedDuration(duration);
                setSelectedPrice(price);
                setSelectedDescription(description);
                }}>

        {variant==='services' &&
        <h5 className="card-title text-center">{title}</h5>}
        <img src={image} className={`card-img-top custom-img--${variant}`} alt={title}/>
        <div className='card-body d-flex flex-column gap-1'>

            {variant==='home' &&
            <p className="card-title text-center">{description}</p>}
            
            {description && 
            <>
            <div className='row cols-row-2 text-center fs-3 fw-bold '>
                <div className='col text-center'>
                     {duration}
                </div>
                <div className='col'>
                    {price}
                </div>
            </div>
            <div className='row d-flex align-items-center justify-content-center'>
                <p className="card-text">{description}</p>
            </div>
            <Button variant="primary" onClick={() => {
                setModalShow(true); 
                setSelectedTitle(title); 
                setSelectedImage(image);
                setSelectedDuration(duration);
                setSelectedPrice(price);
                setSelectedDescription(description);
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