<?php
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all products from products table
$result = mysql_query("SELECT *FROM tb7") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["tb7"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $Part = array();
        $Part["ID"] = $row["ID"];
        $Part["Name"] = $row["Name"];
        $Part["part_nr"] = $row["part_nr"];
 
        // push single product into final response array
        array_push($response["tb7"], $Part);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No parts inserted";
 
    // echo no users JSON
    echo json_encode($response);
}
?>
