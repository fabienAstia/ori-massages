import './FacialCare.css';
import Card from '../Card'
import photo1 from '../../assets/photos/photo_massage1.jpg'
import photo2 from '../../assets/photos/photo_massage2.jpeg'
import photo3 from '../../assets/photos/photo_massage3.webp'

export default function FacialCare({showDescription=true, variant}){
    const list = facialCares.map(facialCare =>
        <div className='col' key={facialCare.id}>
            <div className='card-body d-flex flex-column gap-3 mb-4 align-items-center'>
                <Card 
                title={facialCare.title}
                image={facialCare.image}
                description={showDescription?facialCare.description:undefined}
                variant={variant}
                />
            </div>
        </div>
    );
    return(
        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 justify-content-center">
            {list}
        </div>
    );
}

const facialCares = [
    {
        title:'45 min', 
        image:photo1, 
        description:'45 €', 
        id:1
    },
    {
        title:'1 h', 
        image:photo2, 
        description:'60 €', 
        id:2
    },
    {
        title:'1 h 30', 
        image:photo3, 
        description:'80 €', 
        id:3
    }
];