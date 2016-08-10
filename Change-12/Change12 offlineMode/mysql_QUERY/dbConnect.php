<?php


	define("DB_SERVER", "localhost");
	define("DB_USER", "root");
	define("DB_PASS", "");
	define("DB_NAME", "change12_database_ver6");
	
	$connection =mysqli_connect(DB_SERVER,DB_USER,DB_PASS,DB_NAME) or die('Unable to Connect');
	
	
	/*JSON PARSER
	$sql = "Select * from tbl_volley_img ";
	$res = mysqli_query($connection,$sql);
	$items = array();
	while($row = mysqli_fetch_array($res)){
	
	array_push($items,
		array('id'=>$row[0],
		'photo'=>$row[1],
		'name'=>$row[2]
		));

	}
	
	 echo json_encode(array("results"=>$items));
	 mysqli_close($connection);  */ 
?>