import { useState } from 'react';
import './Card.css'
import Button from 'react-bootstrap/Button';

export default function Card({title, image, description, variant='home', modalShow, setModalShow}){
    return (
    <>
    <div className={`card h-100 custom-style--${variant}`}>
        {variant==='services' &&
        <h5 className="card-title text-center mb-3">{title}</h5>}
        <img src={image} className={`card-img-top custom-img--${variant}`} alt={title}/>
        <div className='card-body d-flex flex-column gap-1'>
            {variant==='home' &&
            <h5 className="card-title text-center">{title}</h5>}
            {description && 
            <>
            <p className="card-text">{description}</p>
            <Button variant="primary" onClick={() => setModalShow(true)}>
                Réserver
            </Button>       
            {/* <a href="#" className="btn btn-primary">Réserver</a> */}
            </>
            }
        </div>
    </div>
    </>
    );
}