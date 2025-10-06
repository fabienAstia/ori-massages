import CustomMassage from '../../components/CustomMassage';
import FacialCare from '../../components/FacialCare';
import './Services.css'
import photo_visage from '../../assets/photos/photo_massage4.webp'
import photo_massage from '../../assets/photos/photo_massage3.webp'

export default function Services(){
    return (
        <section className='ServicesView'>
            <div>
                <h1>Nos prestations</h1>
                <p className='services-text'>Du mieux-être pour toustes</p>

                <div id='massages' className='spacing'>
                    <h2>Massages sur mesure</h2>
                    <p>Offrez-vous un moment de détente grâce à nos massages adaptés à vos besoins.</p>
                    <CustomMassage variant='services'/>
                </div>

                <div id='facialCares' className='spacing'>
                    <h2>Soins visages</h2>
                    <p>Retrouvez éclat et vitalité grâce à des soins du visage personnalisés, pensés pour chaque type de peau.</p>
                    <FacialCare variant='services'/>
                    <p className='note-facial-care'>Tous les soins comportent un nettoyage de peau, un gommage, une pose de masque et un massage.</p>
                </div>
        
                <p className='conclusion'>
                    Les soins sont sur mesure, avec une attention particulière portée à l’écoute, à la bienveillance 
                    et au respect de chacun.e d’entre vous. 
                    Chaque geste est guidé par le souci d’apporter, détente sérénité, réconfort et reconnexion à soi. 
                </p>
            </div>
        </section>
    );
    
}

    const services = [
        {
            title: 'Massages',
            image:photo_massage,
            description:'Massage relax pour le bien-être du corps...',
            id:1
        },
        {
            title:'Soins visage',
            image:photo_visage,
            description:'Soins visage rajeunissant...',
            id:2
        }
    ]
