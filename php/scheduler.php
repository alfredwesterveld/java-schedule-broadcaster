<?php

$url = 'http://localhost:8888/scheduler';
$ch = curl_init($url);
curl_setopt($ch, CURLOPT_POST, 1);

while (true) {
    $rnd = rand(1,10000);
    $epoch = time() * 1000 + $rnd;
    echo $rnd;
    curl_setopt($ch,CURLOPT_POSTFIELDS, "message=$rnd&epoch=$epoch");
    curl_exec($ch);
    sleep(2);
}

curl_close($ch);
