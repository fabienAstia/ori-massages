import './FacialCare.css';
import ServiceCard from '../ServiceCard'
import BookModal from '../BookModal'
import HomeCard from '../HomeCard';
import { useState } from 'react';
import photo1 from '../../assets/photos/photo_massage1.jpg'
import photo2 from '../../assets/photos/photo_massage2.jpeg'
import photo3 from '../../assets/photos/photo_massage3.webp'

export default function FacialCare({showDescription=true, variant}){
        const [modalShow, setModalShow] = useState(false);
        const [selectedTitle, setSelectedTitle] = useState('');
        const [selectedImage, setSelectedImage] = useState('');
        const [selectedPrice, setSelectedPrice] = useState('');
        const [selectedDuration, setSelectedDuration] = useState(0);
        const [selectedDescription, setSelectedDescription] = useState('');

    const listFacialCare = facialCares.map(facialCare =>
        showDescription ?
        <div className='col' key={facialCare.id}>
            <div className='card-body d-flex flex-column gap-3 mb-4 align-items-center'>
                <ServiceCard 
                title={facialCare.title}
                image={facialCare.image}
                duration={facialCare.duration}
                price={facialCare.price}
                description={showDescription? facialCare.description : undefined}
                variant={variant}
                setModalShow={setModalShow}
                setSelectedTitle={setSelectedTitle}
                setSelectedImage={setSelectedImage}
                setSelectedPrice={setSelectedPrice}
                setSelectedDuration={setSelectedDuration}
                setSelectedDescription={setSelectedDescription}
                />
            </div>
        </div>
        :
        <div className='col' key={facialCare.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <HomeCard
                title={facialCare.title}
                image={facialCare.image}
                />
            </div>
        </div>
    );
    return  <section className='CustomMassageView'>
                <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 justify-content-center">
                    {listFacialCare}
                    <BookModal show={modalShow} onHide={() => setModalShow(false)} 
                    title={selectedTitle} 
                    image={selectedImage}
                    duration={selectedDuration}
                    price={selectedPrice}
                    description={selectedDescription}/>
                </div>
            </section>;
}

const facialCares = [
    {
        title: 'Soin visage éclat naturel',
        image: photo1,
        duration: '45 min',
        price: '55 €',
        description: 'Nettoyage, gommage et masque hydratant pour réveiller l’éclat du teint et redonner douceur et luminosité à la peau.',
        id: 1
    },
    {
        title: 'Soin anti-âge revitalisant',
        image: photo2,
        duration: '1 h',
        price: '75 €',
        description: 'Soin complet anti-âge alliant massage liftant et masque régénérant pour lisser les traits et redonner fermeté et éclat à la peau.',
        id: 2
    },
    {
        title: 'Soin visage apaisant peaux sensibles',
        image: photo3,
        duration: '1 h',
        price: '70 €',
        description: 'Soin doux à base d’actifs naturels apaisants, idéal pour calmer les rougeurs, hydrater en profondeur et restaurer le confort des peaux réactives.',
        id: 3
    },
    {
        title: 'Soin purifiant peau nette',
        image: photo1,
        duration: '1 h',
        price: '65 €',
        description: 'Soin détoxifiant alliant vapeur, extraction et masque purifiant pour assainir, resserrer les pores et retrouver un grain de peau uniforme.',
        id: 4
    },
    {
        title: 'Rituel visage premium',
        image: photo2,
        duration: '1 h 30',
        price: '95 €',
        description: 'Rituel complet combinant nettoyage profond, sérum concentré, massage liftant et masque expert pour une peau éclatante et revitalisée.',
        id: 5
    }
];