<?php

$curl = curl_init();

curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
curl_setopt($curl, CURLOPT_URL, "http://localhost:8888/scheduler");
curl_setopt($curl, CURLOPT_WRITEFUNCTION, 'progress');
curl_exec($curl);
curl_close($curl);

function progress($curl, $str) {
    print "$str\n";
    return strlen($str);
}
