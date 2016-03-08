<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <title>Loff1 ungdomsklubb</title>
         <META content=0 http-equiv=Expires>
        <META name=author content="Sindre Osmundsen Rasmussen">
        <META name=description content="Nettsiden til ungdomsklubben Loff1. Loff1 er en ungdomsklubb i Lura Kirke for ungdom fra 8. trinn og oppover. Loff1 er cirka annenhver fredag fra kl. 20:00 og inneholder alt fra underholdning og andakt til spill og kiosk!">
        <META name=keywords content="Loff1, ungdom, klubb, ungdomsklubb, Lura, Sandnes, kirke, kristen, kristendom, loffen, filmnatt, sjokonÃ¦r">
        <META name=Language content=Norwegian>

                <script type="text/javascript">
                    var bredde = window.innerWidth;
                    var hoyde=window.innerHeight;
                    //document.write(bredde);      
                    if (navigator.userAgent.match(/Android/i)
                    || navigator.userAgent.match(/webOS/i)
                    || navigator.userAgent.match(/iPhone/i)
                    || navigator.userAgent.match(/iPad/i)
                    || navigator.userAgent.match(/iPod/i)
                    || navigator.userAgent.match(/BlackBerry/i)
                    || navigator.userAgent.match(/Windows Phone/i)

                    ) {
                        document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"mobil.css\">")
                    }
                    else if (bredde < 1350 || hoyde<770) {
                        document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"smal.css\">")
                    }
                    else {
                        document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">")

                    }
                    function oppdater() {
                        location.reload();
                    }

        </script>


    </head>
    
    <body onresize="oppdater()">
        <!--Facebook script-->
        <div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/nb_NO/sdk.js#xfbml=1&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>
        <!--Facebook script slutt-->


        <div id="side">
        
        
        <div id="right">
        <a href="index.php">
        <div id="mobillogo"></div>
            </a>
            <div id="meny">
                <div id="menyikon">
                </div>
                <a href="om.php">
                <div id="menyikonOm">
                    
                </div>
                    </a>
                <div id="menyikon"></div>
                <a href="kontakt.php">
                <div id="menyikonKontakt"></div>
                    </a>
                <div id="menyikon"></div>
                <a href="konfirmant.php">
                <div id="menyikonKonfirmant"></div>
                    </a>
            </div>
            
            
            <div id="tekstfelt">
                <?php
                $overskrifter=array();
                $avsnitt=array();
                $bilder=array();
                $omfil = fopen("rediger/RedigerKonfirmant.txt", "r") or die("Unable to open file!");
                $nr=0;
                while(!feof($omfil)){
                    array_push($overskrifter,fgets($omfil));
                    array_push($bilder,fgets($omfil));
                    $neste=fgets($omfil);
                    while(strlen(trim($neste))!=0){
                        $avsnitt[$nr]=$avsnitt[$nr].$neste;
                        $neste=fgets($omfil);
                    }
                    $nr=$nr+1;
                }
                fclose($omfil);
                echo "<h1>".$overskrifter[0]."</h1>";
                    if(strlen(trim($bilder[0]))!=0){
                        echo "<img src=\"bilder/".$bilder[0]."\" alt=\"Mangler bilder\">";
                    }
                   echo "<p>".$avsnitt[0]."</p>";
                for($x=1; $x<count($overskrifter);$x++){
                   echo "<h2>".$overskrifter[$x]."</h2>";
                   if(strlen(trim($bilder[$x]))!=0){
                        echo "<img src=\"bilder/".$bilder[$x]."\" alt=\"Mangler bilder\">";
                    }
                   echo "<p>".$avsnitt[$x]."</p>";
                   
                }
                
               ?>
                 <br><br><br> <br><br><br>    
            </div>
            
        </div>
        <div id="left"> 
        </div>
            <a href="index.php">
            <div id="logo">
            </div>
                </a>
        <div id="tekster">
            
        <div id="sisteNyttTekst">
                <?php
                $overskrifter=array();
                $avsnitt=array();
                $bilder=array();
                $omfil = fopen("rediger/RedigerSisteNytt.txt", "r") or die("Unable to open file!");
                $nr=0;
                while(!feof($omfil)){
                    array_push($overskrifter,fgets($omfil));
                    array_push($bilder,fgets($omfil));
                    $neste=fgets($omfil);
                    while(strlen(trim($neste))!=0){
                        $avsnitt[$nr]=$avsnitt[$nr].$neste;
                        $neste=fgets($omfil);
                    }
                    $nr=$nr+1;
                }
                fclose($omfil);
                
                for($x=0; $x<count($overskrifter);$x++){
                   echo "<h1>".$overskrifter[$x]."</h1>";
                   if(strlen(trim($bilder[$x]))!=0){
                        echo "<img src=\"bilder/".$bilder[$x]."\" alt=\"Mangler bilder\">";
                    }
                   echo "<p>".$avsnitt[$x]."</p>";
                   
                }
                
               ?>
            </div>

            <div id="programTekst">
                <h1>Kommende arrangement:</h1>
                <?php
                $overskrifter=array();
                $avsnitt=array();
                $bilder=array();
                $dato=array();
                $maned=array("januar", "februar","mars","april","mai","juni","juli","august","september","oktober","november","desember");
                $omfil = fopen("rediger/RedigerArrangementer.txt", "r") or die("Unable to open file!");
                $nr=0;
                while(!feof($omfil)){
                    array_push($overskrifter,fgets($omfil));
                    array_push($bilder,fgets($omfil));
                    array_push($dato,fgets($omfil));
                    $neste=fgets($omfil);
                    while(strlen(trim($neste))!=0){
                        $avsnitt[$nr]=$avsnitt[$nr].$neste;
                        $neste=fgets($omfil);
                    }
                    $nr=$nr+1;
                }
                fclose($omfil);
                
                for($x=0; $x<count($overskrifter);$x++){
                    
                    if(!(strtotime($dato[$x]) < time()))
                    {
                        echo "<div class=\"arrangement\">";
                   echo "<h1>".$overskrifter[$x]."</h1>";
                   if(strlen(trim($bilder[$x]))!=0){
                        echo "<img src=\"bilder/".$bilder[$x]."\" alt=\"Mangler bilder\">";
                    }
                    else{
                        echo "<img src=\"arrangementer/loff1logo.jpg\" alt=\"Mangler bilder\">";
                    }
                   echo "<p>".$avsnitt[$x]."</p>";
                   $dag=substr($dato[$x], 8, 2);
                   $manedStreng=substr($dato[$x], 5, 2);
                   $manedTall=intval($manedStreng); 
                   echo "<div class=\"dato\"><p><b>".$dag.". <br>".$maned[$manedTall-1]."</b></p></div>";
                   echo "</div>";
                    }
                }
                
               ?>
           
        </div>
         <div id="facebook">
            <h1>Finn oss p&#229; facebook:</h1>
             <div class="fb-like-box" data-href="https://www.facebook.com/loff1lura?ref=bookmarks" data-width="500" data-height="200" data-colorscheme="dark" data-show-faces="true" data-header="false" data-stream="false" data-show-border="false"></div>
            </div>
        </div>
            
            
            </div>
    </body>
</html>
