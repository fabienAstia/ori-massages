import './HomeCard.css'

export default function HomeCard({title, image, address, onClick, className=''}){
    return (
    <>
    <section className={`homeCard `} onClick={onClick}>
        <div className={`card h-100 custom-style--home ${className}`}>

            <img src={image} className={`card-img-top custom-img`} alt={title}/>
            <h5 className={`card-title text-center `}>{title}</h5>
            <p className='text-center'>{address}</p>

        </div>
    </section>

    </>
    );
}