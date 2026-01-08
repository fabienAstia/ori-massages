import { useState, useEffect } from "react";
import axios from "axios";
const apiUrl = import.meta.env.VITE_API_URL

export default function usePrestations(){
    const [prestations, setPrestations] = useState([]);
        
    useEffect(() => {
        async function getPrestations(){
            try {
                const result = await axios.get(`${apiUrl}/prestations/active`)
                setPrestations(result.data);
            } catch(err) {
                if(err.response) return console.log(err.response.data)
                if(err.request) return console.log(err.request)
                return console.log(err.message)
            }
        }
        getPrestations();
    }, [])

    const massagesList = prestations?.filter(prestation=>
        prestation.typeName === 'Massages'
    )
    const facialCaresList = prestations?.filter(prestation=>
        prestation.typeName === 'Soins visage'
    )
    return {prestations, massagesList, facialCaresList}
}
