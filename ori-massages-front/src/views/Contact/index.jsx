import { useState } from 'react';
import './Contact.css'
import axios from 'axios';
import {useForm} from 'react-hook-form'

export default function Contact({bookModalSubmit, isAtHome}){
    const apiUrl = import.meta.env.VITE_API_URL
    const [data, setData] = useState(null)
    const [charCount, setCharCount] = useState(0);
    const MAX_CHARS = 300;
    const [phoneNumber, setPhoneNumber] = useState('')
    const {register, handleSubmit, setValue, formState:{errors}} = useForm();
    
    const onChangePhoneNumber = (e) => {
        let formatted = (e.target.value).replace(/[^\d]/g, "")
        setPhoneNumber(formatted);
        return phoneNumber;
    }
    const onBlurPhoneNumber = (value) => {
        if(!value) return value
        const number = value.replace(/[\s]/g, "")
        setValue("phoneNumber", number)
        if(number.length < 3) return number.length;
        if(number.length >= 3){
            let chunk = '';
            for(let n = 0; n < (number.length/2); n++){
                chunk += `${number.slice(2*n, (2*(n+1)))} `;
            }
            return chunk;
        }
    }
    
    const onSubmit = async(data) => {
        if(bookModalSubmit){
            bookModalSubmit(data)
        }else{
            try {
                const res = await axios.post(`${apiUrl}/contact`, data);
                setData(res.data)
                console.log('data=', res.data)
            }catch(err){
                if(err.response){
                    console.error('POST FAILED with status= ' + err.response.status)
                } else {
                    console.error(err)
                    alert(err)
                }
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
                <form onSubmit={handleSubmit(onSubmit)}>

                    {isAtHome &&
                    <div className="mb-3">
                        <label htmlFor="address" className="form-label">Adresse :<span className="red"> *</span></label>
                        <input 
                            {...register("address", {required:true})}
                            type="address" 
                            className="form-control" 
                            id="address" 
                            aria-describedby="adresse"
                        />
                        {errors.address && <span className='text-danger fw-bold'>This field is required</span>}
                    </div>
                    }
                

                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email :<span className="red"> *</span></label>
                        <input 
                            {...register("email", {required:true})}
                            type="email" 
                            className="form-control" 
                            id="email" 
                            aria-describedby="email"
                        />
                        <div id="emailHelp" className="form-text">Nous ne partageons jamais aucun email avec qui que ce soit.</div>
                        {errors.email && <span className='text-danger fw-bold'>This field is required</span>}
                    </div>

                    <div className="mb-3">
                        <label htmlFor="phoneNumber" className="form-label">Téléphone : <span className="red"> *</span></label>
                        <input 
                            {...register("phoneNumber", {
                                required:true, 
                                onChange: (e) => {
                                    const formatted = onChangePhoneNumber(e)
                                    console.log('formatted=' , formatted)
                                },
                                onBlur: (e) => {e.target.value = onBlurPhoneNumber(e.target.value)}
                            })}
                            type="tel" 
                            className="form-control" 
                            id="phoneNumber"
                            placeholder='06 70 88 71 67'
                            maxLength={14}
                            aria-describedby="phone_number"
                            required
                        />
                        <div id="phoneHelp" className="form-text">Votre numéro servira pour recevoir un sms de rappel 48h avant.</div>
                        {errors.phoneNumber && <span className='text-danger fw-bold'>This field is required</span>}
                    </div>

                    <div className='row row-cols-1 row-cols-md-2'>
                        <div className='col'>
                            <div className="mb-3">
                            <label htmlFor="firstname" className="form-label">Prénom :</label>
                            <input 
                                {...register("firstname")}
                                type="text" 
                                className="form-control" 
                                id="firstname"
                                aria-describedby="firstname"
                            />
                        </div>
                        </div>
                        <div className='col'>
                            <div className="mb-3">
                            <label htmlFor="lastname" className="form-label">Nom :</label>
                            <input 
                                {...register("lastname")}
                                type="text" 
                                className="form-control" 
                                id="lastname"
                                aria-describedby="lastname"
                            />
                        </div>
                        </div>
                    </div>
                    
                    <div className="mb-3">
                        <label htmlFor="message" className="form-label">Message : </label>
                        <textarea 
                            {...register("message", {onChange:(e) => setCharCount(e.target.value.length)})}
                            id='message' 
                            className='form-control w-100' 
                            maxLength={MAX_CHARS} rows={3} placeholder='Ecrire ici...'
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