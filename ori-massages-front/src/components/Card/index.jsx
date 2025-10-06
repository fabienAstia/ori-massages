import './Card.css'

export default function Card({title, image, description, variant='home'}){
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
            <a href="#" className="btn btn-primary">Réserver</a>
            </>
            }
        </div>
    </div>
    </>
    );
}