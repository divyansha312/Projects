const secondhand = document.querySelector('.second-hand')
    const minuteHand =  document.querySelector('.min-hand')
    const hourHand =  document.querySelector('.hour-hand')

    function setDate(){
        const now = new Date()
        const second = now.getSeconds()
        const minute =  now.getMinutes()
        const hour =  now.getHours()

        const secondDegress = ((second/60)*360)+90
        const mindegere = ((minute/60)*360)+90
        // const hourdegree =  (Math.abs(hour-12)/12*360)+90
        const hourdegree  = ((hour/12)*360)+90
        secondhand.style.transform = `rotate(${secondDegress}deg)`
        minuteHand.style.transform = `rotate(${mindegere}deg)`
        hourHand.style.transform = `rotate(${hourdegree}deg)`
        // console.log(second);
        // console.log(minute);
        console.log(hour);

    }
setInterval(setDate,1000);