document.addEventListener('DOMContentLoaded',function (){
    let modal = document.getElementById('loginModal');
    let closeModalBtn = document.getElementById('closeModal')

    closeModalBtn.addEventListener('click',function (){
        modal.style.display='none';
    });

    window.addEventListener('click',function (event){
        if(event.target === modal){
            modal.style.display='none';
        }
    })
})