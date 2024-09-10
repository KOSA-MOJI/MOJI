document.addEventListener('DOMContentLoaded',function (){
    let modal=document.getElementById('loginModal');
    let openModalBtn = document.getElementById('openModalBtn');

    modal.style.display='none';
    openModalBtn.addEventListener('click',function (){
        modal.style.display='block';
    })
})