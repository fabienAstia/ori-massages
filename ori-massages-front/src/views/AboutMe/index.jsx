import './AboutMe.css';
import oriana_photo from '../../assets/photos/oriana-black-and-white-removebg.png'
import { NavLink } from 'react-router-dom';

export default function AboutMe(){
    return(
        <>
         <section className='about-me'>
                <h1>À propos</h1>
                <p className='about-me-text'>Une écoute bienveillante et des soins adaptés à chacun·e</p>

                <div className='d-flex'>
                    <div className='row row-cols-1 row-cols-md-2'>
                         <div className='photo-wrapper col'>
                            <img src={oriana_photo} alt="photo de moi" className='ori-photo'/>
                        </div>
                        <div className='about-me-content col'>
                            <p>
                            Formée à l'école ...
                            </p>
                            <p>
                            Travailleuse sociale pendant plusieurs années, j’ai rencontré de nombreuses personnes, 
                            dans des contextes complexes et de forts moments de vulnérabilité.
                            </p>
                            <p> 
                            J’ai découvert combien un moment de détente, un soin attentif ou un massage bienveillant 
                            peuvent être des leviers puissants pour aider à restaurer la confiance en soi, à se reconnecter.
                            </p>
                            <p>
                            Le bien-être agit comme un catalyseur, permettant de restaurer une énergie positive, 
                            de renforcer l’estime de soi et de renouer avec une dynamique de changement personnel durable.
                            </p>
                        </div>
                       
                    </div>
                </div>
                <div className='d-flex justify-content-center mt-5'>
                    <NavLink to="/contact" className="navButton btn btn-outline-primary ">Me contacter</NavLink>
                </div> 
            </section>
        </>
    );
}