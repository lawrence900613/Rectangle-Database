window.addEventListener("load", function(){
    
    let colour = document.getElementById("color").innerHTML;
    document.getElementById("colorprofile").style.backgroundColor = colour;
    console.log(colour);
    
    




    let h = document.getElementById("height").innerHTML;

    let w = document.getElementById("width").innerHTML;
    document.getElementById("area").innerHTML = w*h;
    while(w>100 || h > 100){
        w = w/2;
        h=h/2;
    }
    if(w<=5 || h<=5){
        w = w*5;
        h = h*5;
    }else{
    if((w<=10 && w>5)||(h<=10 && wh5) ){
        w = w*5;
        h = h*5;
    }
}

    document.getElementById("colorprofile").style.width = w + "mm"; 
    console.log(w);

    document.getElementById("colorprofile").style.height = h + "mm";
    console.log(h);




});




