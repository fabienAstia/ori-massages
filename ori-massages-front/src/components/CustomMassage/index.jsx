import './CustomMassage.css'
import Card from '../Card'
import photo1 from '../../assets/photos/photo_massage1.jpg'
import photo2 from '../../assets/photos/photo_massage2.jpeg'
import photo3 from '../../assets/photos/photo_massage3.webp'

export default function CustomMassage({showDescription = true, variant}){
     const listMassage = massages.map(massage =>
        <div className='col' key={massage.id}>
            <div className="card-body d-flex flex-column mb-4 align-items-center"> 
                <Card
                title={massage.title}
                image={massage.image}
                description={showDescription? massage.description : undefined}
                variant={variant}
                />
            </div>
        </div>
        );
    return <section className='CustomMassageView'>
            <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 justify-content-center">
                {listMassage}
            </div>
        </section>;
}

const massages = [
    {
        title:'45 min', 
        image:photo1, 
        description:'50 €', 
        id:1
    },
    {
        title:'1 h', 
        image:photo2, 
        description:'70 €', 
        id:2
    },
    {
        title:'1 h 30', 
        image:photo3, 
        description:'90 €', 
        id:3
    }
];