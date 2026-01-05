export default function addressToString(address){
    if (!address) return '';
    const {
        streetNumber,
        streetName,
        complement,
        zipCode,
        city
    } = address;

    const complementPart = complement ? ` ${complement}` : '';
    return `${streetNumber} ${streetName}${complementPart}, ${zipCode} ${city}`
}