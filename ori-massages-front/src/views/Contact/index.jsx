import { useState } from 'react';
import './Contact.css'
import axios from 'axios';
import { fi } from 'react-day-picker/locale';

export default function Contact({onSubmit}){
    const [data, setData] = useState(null)
    const [username, setUsername] = useState('')
    const [firstname, setFirstName] = useState('')
    const [lastname, setLastName] = useState('')
    const [phoneNumber, setPhoneNumber] = useState('')
    const [message, setMessage] = useState('')
    const [charCount, setCharCount] = useState(0);
    const MAX_CHARS = 300;

    async function submit(e){
        e.preventDefault();
        try {
            const res = await axios.post('http://localhost:8080/contact', {
                username,
                firstname,
                lastname,
                phoneNumber,
                message
            });
            setData(res.data)
            console.log('res.data', res.data)
        }catch(err){
            if(err.response){
                console.error('POST FAILED with status= ' + err.response.status)
            } else {
                console.error(err)
                alert(err)
            }
        }
    }

    return(
        <section className="ContactView">
            <div className='text-center'>
                {!onSubmit &&
                <>
                    <h2 className="mb-3">Contact</h2>
                    <p className="contact-text">
                        Une question, une demande de rendez-vous ou simplement l’envie d’échanger ?  
                        Je vous répondrai avec plaisir.
                    </p>
                </>
                }  
                {onSubmit &&
                    <p className="contact-text">
                        Merci de renseigner vos informations pour confirmer votre réservation.
                    </p>
                }
               
            </div>
          
            <div className='d-flex mt-1 justify-content-center'>
                <form onSubmit={(e) => {
                    e.preventDefault();
                    if(onSubmit) {
                        onSubmit({username, firstname, lastname, phoneNumber, message})
                    } else {
                        submit(e)
                    }
                    }}
                >
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email :<span className="red"> *</span></label>
                        <input 
                            name="email" 
                            type="email" 
                            className="form-control" 
                            id="email" 
                            onChange={(e) => setUsername(e.target.value)}
                            aria-describedby="email"
                        />
                        <div id="emailHelp" className="form-text">Nous ne partageons jamais aucun email avec qui que ce soit.</div>
                    </div>
                    <div className='row row-cols-1 row-cols-md-2'>
                        <div className='col'>
                            <div className="mb-3">
                            <label htmlFor="firstname" className="form-label">Prénom :</label>
                            <input 
                                name="firstname" 
                                type="text" 
                                className="form-control" 
                                id="firstname"
                                onChange={(e)=> setFirstName(e.target.value)}
                                aria-describedby="firstname"
                            />
                        </div>
                        </div>
                        <div className='col'>
                            <div className="mb-3">
                            <label htmlFor="lastname" className="form-label">Nom :</label>
                            <input 
                                name="lastname" 
                                type="text" 
                                className="form-control" 
                                id="lastname"
                                onChange={(e)=> setLastName(e.target.value)}
                                aria-describedby="lastname"
                            />
                        </div>
                        </div>
                    </div>
                    
                    <div className="mb-3">
                        <label htmlFor="phoneNumber" className="form-label">Téléphone : <span className="red"> *</span></label>
                        <input 
                            name="phoneNumber" 
                            type="tel" 
                            className="form-control" 
                            id="phoneNumber"
                            placeholder='06 70 88 71 67'
                            maxLength={14}
                            onChange={(e)=> setPhoneNumber(e.target.value)}
                            aria-describedby="phone_number"
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="message" className="form-label">Message : </label>
                        <textarea 
                            name="message" 
                            id='message' 
                            className='form-control w-100' 
                            maxLength={MAX_CHARS} rows={3} placeholder='Ecrire ici...'
                            onChange={
                                (event) => {
                                    setCharCount(event.target.value.length)
                                    setMessage(event.target.value)
                                }
                            }
                        />
                        <div id='remaining-char'>{MAX_CHARS-charCount} caractères restants</div>
                    </div>
                    <div className='justify-content-center d-flex'>
                        <button type="submit" className="btn btn-primary custom-btn">Submit</button>
                    </div>
                </form>
            </div>
            
        </section>
    );
}