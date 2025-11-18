export function toLocalDate (date) {
    return new Date(date.getFullYear(), date.getMonth(), date.getDate());
}

export function getDateWithoutOffset(date){
    const dateWithoutOffset = new Date(date.getTime() - (date.getTimezoneOffset()*60000));
    const toLocalDate = dateWithoutOffset.toISOString().split('T')[0];
    return toLocalDate
}

export function formatDate(date){
    if(!date) return "no date"
    const day = date.getDate();
    const month = date.getMonth() + 1;
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;
}
